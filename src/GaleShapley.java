import java.util.*;

/**
 * Represents the Gale-Shapley method
 * @author Yahir Lopez
 */
public class GaleShapley {

    /**
     * Static Map to store Roommate Pairs
     */
    private static Map<UniversityStudent, UniversityStudent> roommatePairs = new HashMap<>();

    /**
     * Assign roommates for students using the Gale-Shapley method
     * @param students List of students to be paired
     */
    public static void assignRoommates(List<UniversityStudent> students) {

        // Initialize a Map from StudentName to it's Reference
        Map<String, UniversityStudent> studentRefFromName = new HashMap<>();
        for (UniversityStudent student : students) {
            studentRefFromName.put(student.getName(), student);
        }

        // Find the potential Roommates to Run the Algo on
        List<String> potentialRoommates = new LinkedList<>();
        for (UniversityStudent student : students) {
            for (String student2 : student.getRoommatePreferences()) {

                //If the Student Doesn't Exist, don't add them
                UniversityStudent student2Ref = studentRefFromName.get(student2);
                if (student2Ref == null) {continue;}

                potentialRoommates.add(student2);
            }
        }

        // Initialize all Potential Roommates to be Free
        Map<String, Boolean> freeStudents = new HashMap<>();
        for (String student : potentialRoommates) {
            freeStudents.put(student, true);
        }

        // Create a Queue to iterator through
        Queue<String> q = new LinkedList<>();
        for (String student : potentialRoommates) {
            q.add(student);
        }

        // Gale Shapley Algo
        while (!q.isEmpty()) {
            String student = q.poll();
            if (freeStudents.get(student).equals(true)) {
                UniversityStudent studentRef = studentRefFromName.get(student);

                List<String> pr = studentRef.getRoommatePreferences();
                for (String roommate : pr) {
                    UniversityStudent roommateRef = studentRefFromName.get(roommate);

                    //If the roommate doesn't exist, skip them
                    if (roommateRef == null) {continue;}

                    // If they are both free, add them to the map and set them to not free
                    if (freeStudents.get(roommate).equals(true)) {
                        roommatePairs.put(studentRef, roommateRef);
                        roommatePairs.put(roommateRef, studentRef);
                        freeStudents.put(roommate, false);
                        freeStudents.put(student, false);
                        break;
                    }
                    else {
                        UniversityStudent otherStudentRef = roommatePairs.get(roommateRef);

                        List<String> preferences = roommateRef.getRoommatePreferences();

                        int otherStudentPreference = preferences.indexOf(otherStudentRef.getName());
                        int thisStudentPreference = preferences.indexOf(student);

                        if (thisStudentPreference < otherStudentPreference && thisStudentPreference != -1) {
                            roommatePairs.remove(otherStudentRef, roommateRef);
                            roommatePairs.put(studentRef, roommateRef);
                            roommatePairs.put(roommateRef, studentRef);
                            freeStudents.put(roommate, false);
                            freeStudents.put(student, false);
                            freeStudents.put(otherStudentRef.getName(), true);
                            q.add(otherStudentRef.getName());
                            break;
                        }
                    }
                }
            }
        }

        // Print Out Calculated Roommates
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
     * @param a Student
     * @param b Student
     * @return boolean
     */
    public static boolean areRoommates(Student a, Student b) {
        try {
            return roommatePairs.get(a).equals(b);
        } catch (Exception e) {
            // Errors will occur will a student is not pared with any student
            // So, they do not exist in roommatePairs
            return false;
        }
    }

    /**
     * Get a Student Object's Roommate if any
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
