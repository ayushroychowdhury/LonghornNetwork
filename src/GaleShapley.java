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
                continue;
            }

            if(preferredRoommate.getRoommate() == null) {
                if(student.getRoommate() != null) {
                    UniversityStudent currentRoommate = getStudent(student.getRoommate(), students);
                    if(student.preferenceRank(preferredRoommate.getName()) < student.preferenceRank(student.getRoommate())) {
                        freeStudents.add(currentRoommate);
                    } else {
                        freeStudents.add(student);
                        continue;
                    }
                    currentRoommate.setRoommate(null);
                    freeStudents.add(currentRoommate);
                }
                student.setRoommate(preferredRoommate.getName());
                preferredRoommate.setRoommate(student.getName());
            } else {
                // If the preferred roommate is not free, check if they prefer the current student
                if(preferredRoommate.preferenceRank(student.getName()) < preferredRoommate.preferenceRank(preferredRoommate.getRoommate())) {
                    // If the preferred roommate prefers the current student, they become roommates
                    UniversityStudent currentRoommate = getStudent(preferredRoommate.getRoommate(), students);
                    currentRoommate.setRoommate(null);
                    student.setRoommate(preferredRoommate.getName());
                    preferredRoommate.setRoommate(student.getName());
                    freeStudents.add(currentRoommate);
                } else {
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
