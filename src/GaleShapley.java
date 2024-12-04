import java.util.*;

public class GaleShapley {
    /**
     * Assigns roommates to students based on their preferences using the Gale-Shapley algorithm.
     * @param students List of students
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
                continue;
            }

            UniversityStudent preferredRoommateCurrentRoommate = getStudent(preferredRoommate.getRoommate(), students);

            if(preferredRoommateCurrentRoommate == null) {
                student.setRoommate(preferredRoommate.getName());
                preferredRoommate.setRoommate(student.getName());
                freeStudents.remove(preferredRoommate);
            } else {
                if(preferredRoommate.preferenceRank(student.getName()) < preferredRoommate.preferenceRank(preferredRoommateCurrentRoommate.getName())) {
                    student.setRoommate(preferredRoommate.getName());
                    preferredRoommate.setRoommate(student.getName());
                    freeStudents.remove(preferredRoommate);
                    freeStudents.add(preferredRoommateCurrentRoommate);
                } else {
                    studentPreferences.get(student).remove(preferredRoommate);
                    freeStudents.add(student);
                }
            }
        }
    }

    /**
     * Returns the student with the given name from the list of students.
     * @param name Name of the student
     * @param students List of students
     * @return Student with the given name
     */
    private static UniversityStudent getStudent(String name, List<UniversityStudent> students) {
        for(UniversityStudent student : students) {
            if(student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }
}
