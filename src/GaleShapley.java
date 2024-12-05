import java.util.*;

/**
 * Represents the Gale-Shapley method
 *
 * @author Cooper Nelson
 */
public class GaleShapley {

    /**
     * Static Map to store Roommate Pairs
     */
    private static Map<UniversityStudent, UniversityStudent> roommatePairs = new HashMap<>();

    /**
     * Assign roommates for students using the Gale-Shapley method
     *
     * @param students List of students to be paired
     */
    public static void assignRoommates(List<UniversityStudent> students) {

        Map<String, UniversityStudent> studentMap = new HashMap<>();
        for (UniversityStudent student : students) {
            studentMap.put(student.getName(), student);
        }

        Set<String> prefferred = new HashSet<>();
        for (UniversityStudent student : students) {
            for (String s : student.getRoommatePreferences()) {
                UniversityStudent sRef = studentMap.get(s);
                if (sRef == null) {
                    continue;
                }
                prefferred.add(s);
            }
        }

        Map<String, Boolean> freeStudents = new HashMap<>();
        for (String student : prefferred) {
            freeStudents.put(student, true);
        }

        Queue<String> preferredQueue = new LinkedList<>();
        for (String student : prefferred) {
            preferredQueue.add(student);
        }

        while (!preferredQueue.isEmpty()) {
            String student = preferredQueue.poll();
            if (freeStudents.get(student).equals(true)) {
                UniversityStudent uStudent = studentMap.get(student);

                List<String> studentPrefs = uStudent.getRoommatePreferences();
                for (String roommate : studentPrefs) {
                    UniversityStudent uRoommate = studentMap.get(roommate);
                    if (uRoommate == null) {
                        continue;
                    }
                    if (freeStudents.get(roommate).equals(true)) {
                        roommatePairs.put(uStudent, uRoommate);
                        roommatePairs.put(uRoommate, uStudent);
                        freeStudents.put(roommate, false);
                        freeStudents.put(student, false);
                        break;
                    } else {
                        UniversityStudent otherStudentRef = roommatePairs.get(uRoommate);

                        List<String> preferences = uRoommate.getRoommatePreferences();

                        int otherStudentPreference = preferences.indexOf(otherStudentRef.getName());
                        int thisStudentPreference = preferences.indexOf(student);

                        if (thisStudentPreference < otherStudentPreference && thisStudentPreference != -1) {
                            roommatePairs.remove(otherStudentRef, uRoommate);
                            roommatePairs.put(uStudent, uRoommate);
                            roommatePairs.put(uRoommate, uStudent);
                            freeStudents.put(roommate, false);
                            freeStudents.put(student, false);
                            freeStudents.put(otherStudentRef.getName(), true);
                            preferredQueue.add(otherStudentRef.getName());
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("Roommate Assignment:");
        for (UniversityStudent student : roommatePairs.keySet()) {
            String student1Name = student.getName();
            String student2Name = roommatePairs.get(student).getName();

            System.out.println(student1Name + " is roommates with " + student2Name);
        }
        System.out.println("");
    }

    /**
     * Find out if Student Objects are roommates
     *
     * @param a Student
     * @param b Student
     * @return boolean
     */
    public static boolean areRoommates(Student a, Student b) {
        try {
            return roommatePairs.get(a).equals(b);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get a Student Object'student Roommate if any
     *
     * @param a Student
     * @return UniversityStudent or null
     */
    public static UniversityStudent getRoommate(Student a) {
        if (roommatePairs.containsKey(a)) {
            return roommatePairs.get(a);
        } else {
            return null;
        }
    }
}
