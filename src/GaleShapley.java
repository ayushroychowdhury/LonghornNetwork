import java.util.*;

public class GaleShapley {
    /**
     * Assigns roommates to students based on their preferences using the Gale-Shapley algorithm.
     * @param students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Create a map of student preferences (disposable data)
        Map<String, LinkedList<UniversityStudent>> studentPreferences = new HashMap<>();
        for(UniversityStudent student : students) {
            LinkedList<UniversityStudent> preferences = new LinkedList<>();
            for(String preference : student.getRoommatePreferences()) {
                UniversityStudent preferredRoommate = getStudent(preference, students);
                preferences.add(preferredRoommate);
            }
            studentPreferences.put(student.getName(), preferences);
        }

        // Implement the Gale-Shapley algorithm
        // Initially all students are free
        LinkedList<UniversityStudent> freeStudents = new LinkedList<>();
        freeStudents.addAll(students);

        // While there are free students
        while(!freeStudents.isEmpty()) {
            
            // Get the first free student
            UniversityStudent student = freeStudents.poll();

            // Get its most preferred roommate that hasn't been proposed to yet
            UniversityStudent preferredRoommate = studentPreferences.get(student.getName()).poll();

            // If there is a roommate that hasn't been proposed to yet
            if(preferredRoommate != null) {

                // If the preferred roommate is free they become roommates
                if(preferredRoommate.getRoommate() == null) {
                    student.setRoommate(preferredRoommate.getName());
                    preferredRoommate.setRoommate(student.getName());
                } 

                // If the preferred roommate already has a roommate
                else {

                    // If the preferred roommate prefers the current student over their current roommate
                    UniversityStudent currentRoommate = getStudent(preferredRoommate.getRoommate(), students);
                    if(preferredRoommate.preferenceRank(student.getName()) < preferredRoommate.preferenceRank(currentRoommate.getName())) {
                        // The current roommate of the student becomes free
                        if(student.getRoommate() != null) {
                            UniversityStudent oldRoommate = getStudent(student.getRoommate(), students);
                            oldRoommate.setRoommate(null);
                            freeStudents.add(oldRoommate);
                        }

                        // The preferred roommate becomes the student's roommate
                        student.setRoommate(preferredRoommate.getName());
                        preferredRoommate.setRoommate(student.getName());
                        // The preferred roommate's current roommate becomes free
                        currentRoommate.setRoommate(null);
                        freeStudents.add(currentRoommate);
                    } else {

                        // The student remains free and must propose to the next preferred roommate
                        if(student.getRoommate() == null) {
                            freeStudents.add(student);
                        }
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
