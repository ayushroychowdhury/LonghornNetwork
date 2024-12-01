import java.util.*;

public class GaleShapley {
    /**
     * Assigns roommates to students based on their preferences using the Gale-Shapley algorithm.
     * @param students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Create a map of student preferences (disposable data)
        Map<UniversityStudent, LinkedList<UniversityStudent>> studentPreferences = new HashMap<>();
        for(UniversityStudent student : students) {
            LinkedList<UniversityStudent> preferences = new LinkedList<>();
            for(String preference : student.getRoommatePreferences()) {
                UniversityStudent preferredRoommate = getStudent(preference, students);
                preferences.add(preferredRoommate);
            }
            studentPreferences.put(student, preferences);
        }

        // Implement the Gale-Shapley algorithm
        // Initially all students are free
        LinkedList<UniversityStudent> freeStudents = new LinkedList<>();
        freeStudents.addAll(students);

        // While there are free students
        while(!freeStudents.isEmpty()) {
            
            // Get the first free student
            UniversityStudent student = freeStudents.poll();
            UniversityStudent preferredRoommate = studentPreferences.get(student).poll();
            if(preferredRoommate == null) {
                // If the student has no more preferences, they remain free and are not reconsidered
                System.out.println(student.getName() + " has no more preferences.");
                continue;
            }

            System.out.println(student.getName() + " is proposing to " + preferredRoommate.getName());

            if(preferredRoommate.getRoommate() == null) {
                System.out.println(preferredRoommate.getName() + " is free.");
                if(student.getRoommate() != null) {
                    UniversityStudent currentRoommate = getStudent(student.getRoommate(), students);
                    if(student.preferenceRank(preferredRoommate.getName()) < student.preferenceRank(student.getRoommate())) {
                        System.out.println(student.getName() + " prefers " + preferredRoommate.getName() + " over " + student.getRoommate());
                        freeStudents.add(currentRoommate);
                        System.out.println(currentRoommate.getName() + " is now free.");
                    } else {
                        System.out.println(student.getName() + " prefers " + student.getRoommate() + " over " + preferredRoommate.getName());
                        freeStudents.add(student);
                        System.out.println(student.getName() + " remains with " + student.getRoommate());
                        continue;
                    }
                    currentRoommate.setRoommate(null);
                    freeStudents.add(currentRoommate);
                }
                student.setRoommate(preferredRoommate.getName());
                preferredRoommate.setRoommate(student.getName());
                System.out.println(student.getName() + " and " + preferredRoommate.getName() + " are now roommates.\n");
            } else {
                System.out.println(preferredRoommate.getName() + " is not free.");
                if(student.getRoommate() == preferredRoommate.getName()) {
                    System.out.println(student.getName() + " is already roommates with " + preferredRoommate.getName() + "\n");
                    continue;
                }
                // If the preferred roommate is not free, check if they prefer the current student
                if(preferredRoommate.preferenceRank(student.getName()) < preferredRoommate.preferenceRank(preferredRoommate.getRoommate())) {
                    System.out.println(preferredRoommate.getName() + " prefers " + student.getName() + " over " + preferredRoommate.getRoommate());
                    // If the preferred roommate prefers the current student, they become roommates
                    UniversityStudent currentRoommate = getStudent(preferredRoommate.getRoommate(), students);
                    currentRoommate.setRoommate(null);
                    student.setRoommate(preferredRoommate.getName());
                    preferredRoommate.setRoommate(student.getName());
                    System.out.println(student.getName() + " and " + preferredRoommate.getName() + " are now roommates.");
                    freeStudents.add(currentRoommate);
                    System.out.println(currentRoommate.getName() + " is now free.\n");
                } else {
                    System.out.println(preferredRoommate.getName() + " prefers " + preferredRoommate.getRoommate() + " over " + student.getName());
                    // If the preferred roommate does not prefer the current student, they remain free
                    freeStudents.add(student);
                }
            }
        }
    }

    private static UniversityStudent getStudent(String name, List<UniversityStudent> students) {
        for(UniversityStudent student : students) {
            if(student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }
}
