import java.util.*;

/**
 * Implements the Gale-Shapley algorithm for stable roommate matching.
 */
public class GaleShapley {
    /**
     * Assigns roommates to a list of students using the Gale-Shapley algorithm.
     *
     * @param students The list of students to assign roommates.
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        Map<String, UniversityStudent> studentMap = new HashMap<>();
        for (UniversityStudent student : students) {
            studentMap.put(student.getName(), student);
        }

        Set<UniversityStudent> unmatchedStudents = new HashSet<>(students);

        while (!unmatchedStudents.isEmpty()) {
            Iterator<UniversityStudent> iterator = unmatchedStudents.iterator();
            UniversityStudent student = iterator.next();
            iterator.remove();

            List<String> preferences = student.getRoommatePreferences();
            for (String preferredName : preferences) {
                UniversityStudent preferredStudent = studentMap.get(preferredName);
                if (preferredStudent != null && unmatchedStudents.contains(preferredStudent)) {
                    if (preferredStudent.getRoommatePreferences().contains(student.getName())) {
                        // Mutual preference
                        student.setRoommate(preferredStudent);
                        preferredStudent.setRoommate(student);
                        unmatchedStudents.remove(preferredStudent);

                        System.out.println(student.getName() + " is roommates with " + preferredStudent.getName());
                        break;
                    }
                }
            }
        }
    }
}