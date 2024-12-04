import java.util.*;

/**
 * the Gale-Shapley algorithm main logic for assigning roommates to students based on their preferences.
 */
public class GaleShapley {
    private static Map<UniversityStudent, UniversityStudent> roommateMatches = new HashMap<>();

    /**
     * Assigns roommates to students using this algorithm.
     *
     * @param students the list of students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        roommateMatches.clear();
        Queue<UniversityStudent> unmatched = new LinkedList<>(students);

        Map<UniversityStudent, Set<UniversityStudent>> proposals = new HashMap<>();
        for (UniversityStudent student : students) {
            proposals.put(student, new HashSet<>());
        }

        while (!unmatched.isEmpty()) {
            UniversityStudent proposer = unmatched.poll();
            List<String> preferences = proposer.roommatePreferences;

            boolean matched = false;

            for (String preferredName : preferences) {
                UniversityStudent preferred = findStudentByName(preferredName, students);

                // Skip if the preferred student is null or already proposed to
                if (preferred == null || proposals.get(proposer).contains(preferred)) {
                    continue;
                }

                proposals.get(proposer).add(preferred);

                if (!roommateMatches.containsKey(preferred)) {
                    // Preferred student is unmatched; assign them as roommates
                    roommateMatches.put(proposer, preferred);
                    roommateMatches.put(preferred, proposer);
                    matched = true;
                    break;
                } else {
                    // Preferred student is matched; check if they prefer this proposer
                    UniversityStudent currentMatch = roommateMatches.get(preferred);
                    if (prefers(preferred, proposer, currentMatch)) {
                        roommateMatches.remove(currentMatch);
                        unmatched.add(currentMatch); // Requeue the unmatched student

                        roommateMatches.put(proposer, preferred);
                        roommateMatches.put(preferred, proposer);
                        matched = true;
                        break;
                    }
                }
            }

            // Re-add proposer only if they didn't find a match
            if (!matched && !roommateMatches.containsKey(proposer)) {
                unmatched.add(proposer);
            }
        }

        // Assign null for unmatched students
        for (UniversityStudent student : students) {
            roommateMatches.putIfAbsent(student, null);
        }
    }


    /**
     * Checks if a student prefers one proposer over another.
     *
     * @param student   the student whose preference is being checked
     * @param proposer  the new proposer
     * @param current   the current match
     * @return true if the student prefers the new proposer, false otherwise
     */
    private static boolean prefers(UniversityStudent student, UniversityStudent proposer, UniversityStudent current) {
        List<String> preferences = student.roommatePreferences;
        int proposerIndex = preferences.indexOf(proposer.name);
        int currentIndex = preferences.indexOf(current.name);

        return proposerIndex != -1 && (currentIndex == -1 || proposerIndex < currentIndex);
    }

    /**
     * Finds a student by name from the list of students.
     *
     * @param name     the name of the student to find
     * @param students the list of students
     * @return the UniversityStudent object if found, null otherwise
     */
    private static UniversityStudent findStudentByName(String name, List<UniversityStudent> students) {
        for (UniversityStudent student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Retrieves the roommate matches.
     *
     * @return a map of students and their roommates
     */
    public static Map<UniversityStudent, UniversityStudent> getRoommateMatches() {
        return roommateMatches;
    }
}
