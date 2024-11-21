import java.util.*;

/**
 * Assigns roommates to students using the GaleShapley algorithm
 */
public class GaleShapley {
    /**
     * Assigns roommates to students
     * @param students A list of students
     */
    // public static Map<String, String> matches;
    public static void assignRoommates(List<UniversityStudent> students) {
//        matches = new HashMap<>(); // Holds the final pairings
//        Queue<String> unpairedStudents = new LinkedList<>(); // Queue for students without matches
//
//        // Initialize the queue with all students
//        unpairedStudents.addAll(preferences.keySet());
//
//        // Maps to keep track of who has proposed to whom
//        Map<String, Integer> proposalIndex = new HashMap<>();
//        preferences.keySet().forEach(student -> proposalIndex.put(student, 0));
//
//        while (!unpairedStudents.isEmpty()) {
//            String proposer = unpairedStudents.poll();
//            List<String> proposerPreferences = preferences.getOrDefault(proposer, Collections.emptyList());
//
//            // If the student has no preferences, skip them
//            if (proposerPreferences.isEmpty() || proposalIndex.get(proposer) >= proposerPreferences.size()) {
//                continue;
//            }
//
//            // Get the next student on the proposer's preference list
//            String preferred = proposerPreferences.get(proposalIndex.get(proposer));
//            proposalIndex.put(proposer, proposalIndex.get(proposer) + 1);
//
//            // Check if the preferred student is already matched
//            if (!matches.containsKey(preferred)) {
//                // Accept the proposal as the preferred student is not matched
//                matches.put(proposer, preferred);
//                matches.put(preferred, proposer);
//            } else {
//                // The preferred student is already matched, check if they prefer the new proposer
//                String currentPartner = matches.get(preferred);
//                List<String> preferredList = preferences.getOrDefault(preferred, Collections.emptyList());
//
//                if (preferredList.indexOf(proposer) < preferredList.indexOf(currentPartner)) {
//                    // The preferred student prefers the new proposer over the current partner
//                    matches.put(proposer, preferred);
//                    matches.put(preferred, proposer);
//
//                    // Unpair the current partner and re-add them to the queue
//                    matches.remove(currentPartner);
//                    unpairedStudents.add(currentPartner);
//                } else {
//                    // The preferred student prefers their current partner, re-add the proposer to the queue
//                    unpairedStudents.add(proposer);
//                }
//            }
//        }
//
//        // Ensure unmatched students are not included
//        for (String student : preferences.keySet()) {
//            if (!matches.containsKey(student)) {
//                matches.remove(student);
//            }
//        }

        // Map for easy access by name
        Map<String, Student> studentMap = new HashMap<>();
        for (Student student : students) {
            studentMap.put(student.name, student);
        }

        // Queue for unpaired students
        Queue<Student> unpaired = new LinkedList<>(students);

        // Keeps track of the proposal index for each student
        Map<String, Integer> proposalIndex = new HashMap<>();
        for (Student student : students) {
            proposalIndex.put(student.name, 0); // Initially no proposals made
        }

        // Begin proposals
        while (!unpaired.isEmpty()) {
            Student proposer = unpaired.poll();
            if (proposalIndex.get(proposer.name) < proposer.roommatePreferences.size()) {
                // Get the next preference of the proposer
                String nextRoommate = proposer.roommatePreferences.get(proposalIndex.get(proposer.name));
                proposalIndex.put(proposer.name, proposalIndex.get(proposer.name) + 1);
                Student proposedStudent = studentMap.get(nextRoommate);

                // If proposed student is unpaired, accept the proposal
                if (proposedStudent.roommate == null) {
                    proposedStudent.roommate = proposer.name;
                    proposer.roommate = proposedStudent.name;
                } else {
                    // If proposed student is already paired, check preferences
                    String currentRoommate = proposedStudent.roommate;
                    int currentPreference = proposedStudent.roommatePreferences.indexOf(currentRoommate);
                    int newPreference = proposedStudent.roommatePreferences.indexOf(proposer.name);

                    // If the proposer is preferred over the current roommate, swap roommates
                    if (newPreference < currentPreference) {
                        unpaired.offer(studentMap.get(currentRoommate)); // The current roommate becomes unpaired
                        proposedStudent.roommate = proposer.name;
                        proposer.roommate = proposedStudent.name;
                    } else {
                        // Keep the current pairing and proposer is still unpaired
                        unpaired.offer(proposer);
                    }
                }
            }
        }

        // Print out final pairings
//        for (Student student : students) {
//            if (student.roommate != null) {
//                System.out.println(student.name + " is paired with " + student.roommate);
//            } else {
//                System.out.println(student.name + " has no roommate.");
//            }
//        }

    }
}
