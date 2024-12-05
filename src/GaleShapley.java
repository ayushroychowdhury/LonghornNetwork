

import java.util.*;

/**
 * Uses Gale-Shapley method to match students based on roommate preferences
 */
public class GaleShapley {

    /**
     * Takes input of list of UniversityStudent objects and matches based on Gale-Shapley method
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        int N = students.size();
        Map<String, UniversityStudent> nameToStudent = new HashMap<>();
        String[] studentNames = new String[N];
        String[][] preferences = new String[N][];
        String[] roommates = new String[N];
        boolean[] avail = new boolean[N];

        // make map with student names and store roommate preferences as an array
        for (int i = 0; i < N; i++) {
            UniversityStudent student = students.get(i);
            nameToStudent.put(student.getName(), student);
            studentNames[i] = student.getName();
            List<String> prefs = student.getRoommatePreferences();
            preferences[i] = prefs.toArray(new String[0]);
        }

        // Start gale-shapley algorithm
        int availCount = 0;
        while (availCount < N / 2) { // while half the students are not matched
            int freeIndex = -1;
            for (int i = 0; i < N; i++) { // find first unpaired student
                if (!avail[i]) {
                    freeIndex = i;
                    break;
                }
            }

            if (freeIndex == -1) break; // all students are paired up

            for (String preferredName : preferences[freeIndex]) {
                int preferredIndex = studentIndexOf(preferredName, studentNames);
                if (preferredIndex == -1) continue;

                if (roommates[preferredIndex] == null) { // if preferred roommate has no roommate, pair them
                    roommates[freeIndex] = studentNames[preferredIndex];
                    roommates[preferredIndex] = studentNames[freeIndex];
                    avail[freeIndex] = true;
                    avail[preferredIndex] = true;
                    availCount++;

                    nameToStudent.get(studentNames[freeIndex]).setRoommate(nameToStudent.get(studentNames[preferredIndex]));
                    nameToStudent.get(studentNames[preferredIndex]).setRoommate(nameToStudent.get(studentNames[freeIndex]));

                    break;
                } else {
                    // if they alr have a roommate, check if they preference prefer new proposer
                    String currentRoomie = roommates[preferredIndex];
                    if (morePreferred(studentNames[freeIndex], currentRoomie, preferences[preferredIndex])) {
                        //reassign roommamtes and unmatch previous roommate
                        int currentIndex = studentIndexOf(currentRoomie, studentNames);
                        roommates[preferredIndex] = studentNames[freeIndex];
                        roommates[freeIndex] = studentNames[preferredIndex];
                        avail[freeIndex] = true;
                        avail[currentIndex] = false;

                        nameToStudent.get(studentNames[preferredIndex]).setRoommate(nameToStudent.get(studentNames[freeIndex]));
                        nameToStudent.get(studentNames[freeIndex]).setRoommate(nameToStudent.get(studentNames[preferredIndex]));
                        nameToStudent.get(studentNames[currentIndex]).setRoommate(null); // Remove old roommate
                        break;

                    }
                }
            }
        }

        // Final roommate list
        System.out.println("Roommate Assignment:");
        for (UniversityStudent student : students) {
            UniversityStudent roommate = student.getRoommate();
            if (roommate != null) {
                System.out.println(student.getName() + " is roommates with " + roommate.getName());
            }
        }

    }

    /**
     * Helper method to find the index of student using their name
     **/
    private static int studentIndexOf(String name, String[] studentNames) {
        for (int i = 0; i < studentNames.length; i++) {
            if (studentNames[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper method to check if student is preferred
     **/
    private static boolean morePreferred(String newProposer, String currentRoomie, String[] preferences) {
        // iterates over preference list until they find either proposer ot current roomie
        for (String preference : preferences) {
            if (preference.equals(newProposer)) {
                return true;
            }
            if (preference.equals(currentRoomie)) {
                return false;
            }
        }
        return false;
    }

}
