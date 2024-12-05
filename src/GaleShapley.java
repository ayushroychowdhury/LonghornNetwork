import java.util.*;

public class GaleShapley {

    public static void assignRoommates(List<UniversityStudent> students) {
        Map<String, String> matches = new HashMap<>();
        Queue<UniversityStudent> unmatched = new LinkedList<>(students);

        while (!unmatched.isEmpty()) {
            UniversityStudent proposer = unmatched.poll();
            for (String preference : proposer.roommatePreferences) {
                if (!matches.containsKey(preference)) {
                    matches.put(proposer.name, preference);
                    matches.put(preference, proposer.name); // Bidirectional relationship
                    break;
                }
            }
        }

        // Print formatted roommate assignments
        System.out.println("Roommate Assignments:");
        for (Map.Entry<String, String> entry : matches.entrySet()) {
            if (students.stream().anyMatch(s -> s.name.equals(entry.getKey()))) {
                System.out.println(entry.getKey() + " is roommates with " + entry.getValue());
            }
        }
    }
}
