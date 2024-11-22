import java.util.*;

/**
 * Class used to match students to their roommate based on the Gale Shapley algorithm
 */
public class GaleShapley {
    /**
     * Given a list of University Students, assigns roommate to each student
     * based on their preference list via Gale Shapley algorithm
     * @param students list of University Students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Queue to track unpaired students
        Queue<UniversityStudent> unpairedStudents = new LinkedList<>(students);
        // Map of student names to their preferences for easy lookup
        Map<String, List<String>> preferenceMap = new HashMap<>();

        // Initialize preference map
        for (UniversityStudent student : students) {
            preferenceMap.put(student.getName(), new ArrayList<>(student.getRoommatePreferences()));
        }

        // Main Gale-Shapley algorithm
        while (!unpairedStudents.isEmpty()) {
            // Get the next unpaired student
            UniversityStudent proposer = unpairedStudents.poll();
            String proposerName = proposer.getName();
            List<String> preferences = preferenceMap.get(proposerName);

            if (preferences == null || preferences.isEmpty()) {
                // No preferences left, skip this student
                continue;
            }

            // Propose to the top preference
            String preferredRoommateName = preferences.removeFirst(); // Remove the top preference

            // Find the preferred roommate by name
            UniversityStudent preferredRoommate = students.stream()
                    .filter(s -> s.getName().equals(preferredRoommateName))
                    .findFirst()
                    .orElse(null);

            if (preferredRoommate == null) {
                continue; // Skip if the preferred roommate is not found
            }

            if (preferredRoommate.getRoommate() == null) {
                // Preferred roommate is unpaired, form a pairing
                proposer.setRoommate(preferredRoommate.getName());
                preferredRoommate.setRoommate(proposer.getName());
            } else {
                // Preferred roommate is already paired
                UniversityStudent currentRoommate = students.stream()
                        .filter(s -> s.getName().equals(preferredRoommate.getRoommate()))
                        .findFirst()
                        .orElse(null);

                if (currentRoommate == null) {
                    continue; // Skip if the current roommate is not found
                }

                // Check if preferredRoommate prefers proposer over currentRoommate
                List<String> preferredRoommatePrefs = preferenceMap.get(preferredRoommate.getName());
                if (preferredRoommatePrefs.indexOf(proposerName) <
                        preferredRoommatePrefs.indexOf(currentRoommate.getName())) {
                    // Preferred roommate prefers proposer, update pairing
                    proposer.setRoommate(preferredRoommate.getName());
                    preferredRoommate.setRoommate(proposer.getName());

                    // Unpair the current roommate
                    currentRoommate.setRoommate(null);
                    unpairedStudents.add(currentRoommate); // Add unpaired student back to the queue
                } else {
                    // Proposer remains unpaired
                    unpairedStudents.add(proposer);
                }
            }
        }

        // Print the roommate assignments in the specified format
        System.out.println("\nRoommate Assignments:");
        for (UniversityStudent student : students) {
            if (student.getRoommate() != null) {
                System.out.println(student.getName() + " is roommates with " + student.getRoommate());
            } else {
                System.out.println(student.getName() + " has no roommate");
            }
        }
    }
}
