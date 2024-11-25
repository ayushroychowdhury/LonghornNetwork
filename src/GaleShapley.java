import java.util.*;

/**
 * the Gale-Shapley algorithm main logic for assigning roommates to students based on their preferences.
 */
public class GaleShapley {
    /**
     * Assigns roommates to students using this algorithm.
     *
     * @param students the list of students 
     */
    public static void assignRoommates(List<UniversityStudent> students) { //where to store the matches?
        //Initialise the matching and queues
        Map<UniversityStudent, UniversityStudent> matches = new HashMap<>();
        Queue<UniversityStudent> unmatched = new LinkedList<>(students);

        //Initialise proposal map to keep track of proposals made by each student
        Map<UniversityStudent, Set<UniversityStudent>> proposals = new HashMap<>();
        for (UniversityStudent student: students) {
            proposals.put(student, new HashSet<>());
        }

        //main algorithm
        while (!unmatched.isEmpty()) {
            UniversityStudent proposer = unmatched.poll();
            List<String> preferences = proposer.roommatePreferences;

            //start proposing
            for (String preferredName : preferences) {
                UniversityStudent preferred = findStudentByName(preferredName, students);

                //skip if preferred roommate doesn't exist at all or has already been proposed to
                if (preferred == null || proposals.get(proposer).contains(preferred)) {
                    continue;
                }

                //mark as proposed to
                proposals.get(preferred).add(proposer);

                //if unmatched, match them
                if (!matches.containsKey(preferred)) {
                    matches.put(proposer, preferred);
                    matches.put(preferred, proposer);
                    break;
                } else {
                    //check if the preferred roommate prefers the new proposer or the existing matched student
                    UniversityStudent currentMatch = matches.get(preferred);
                    if (prefers(preferred, proposer, currentMatch)) {
                        matches.remove(currentMatch);
                        unmatched.add(currentMatch);

                        matches.put(proposer, preferred);
                        matches.put(preferred, proposer);
                        break;
                    }
                }
            }

            //if no match is found, the proposer remains unmatched
            if (!matches.containsKey(proposer)) {
                unmatched.add(proposer);
            }
        }

        //ensure unmatched students are left out of the matches
        for (UniversityStudent student : students) {
            if (!matches.containsKey(student)) {
                matches.put(student, null);
            }
        }

        //return matches;
        //where to store the matches???
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

        // If the proposer is listed before the current match, return true
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
}
