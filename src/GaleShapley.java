import java.util.*;

public class GaleShapley {
    /**
     * Assigns roommates to students based on their preferences using the Gale-Shapley algorithm.
     * @param students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        Map<String, LinkedList<UniversityStudent>> studentPreferences = new HashMap<>();
        for(UniversityStudent student : students) {
            studentPreferences.put(student.getName(), new LinkedList<>(students));
            for(String preference : student.getRoommatePreferences()) {
                studentPreferences.get(student.getName()).removeIf(s -> !s.getName().equals(preference));
            }
        }
        // Implement the Gale-Shapley algorithm here
        LinkedList<UniversityStudent> freeStudents = new LinkedList<>(students);
        while(!freeStudents.isEmpty()) {
            UniversityStudent student = freeStudents.poll();
            UniversityStudent preferredRoommate = studentPreferences.get(student.getName()).poll();
            if(preferredRoommate != null) {
                if(preferredRoommate.getRoommate() == null) {
                    student.setRoommate(preferredRoommate.getName());
                    preferredRoommate.setRoommate(student.getName());
                } else {
                    UniversityStudent currentRoommate = getStudent(preferredRoommate.getRoommate(), students);
                    if(currentRoommate.preferenceRank(student.getName()) < currentRoommate.preferenceRank(preferredRoommate.getName())) {
                        student.setRoommate(preferredRoommate.getName());
                        preferredRoommate.setRoommate(student.getName());
                        freeStudents.add(currentRoommate);
                    } else {
                        freeStudents.add(student);
                    }
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
