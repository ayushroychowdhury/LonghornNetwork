import java.util.*;

/**
 * Finds referral paths for students based on their connections in a student graph.
 */
public class ReferralPathFinder {
    private StudentGraph graph;

    /**
     * Constructs a ReferralPathFinder with the given student graph.
     *
     * @param graph The graph representing students and their connections.
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds a referral path from the start student to the target company.
     *
     * @param start         The student to start the referral path from.
     * @param targetCompany The target company for the referral path.
     * @return A list of students representing the referral path.
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        Map<UniversityStudent, Integer> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        PriorityQueue<UniversityStudent> queue = new PriorityQueue<>((a, b) -> distances.get(b) - distances.get(a));
        Set<UniversityStudent> visited = new HashSet<>();

        // Check if the starting student has already interned at the target company
        if (start.getPreviousInternships().stream().anyMatch(internship -> internship.trim().equalsIgnoreCase(targetCompany.trim()))) {
            System.out.println("Found referral path for student " + start.getName() + " with weight: 0");
            return Collections.singletonList(start);
        }

        // Initialize distances and previous maps
        for (UniversityStudent student : graph.getAllNodes()) {
            distances.put(student, Integer.MIN_VALUE); // We are maximizing weights
            previous.put(student, null);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            UniversityStudent current = queue.poll();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            // Debug statement
            System.out.println("Visiting student: " + current.getName() + " with total weight: " + distances.get(current));

            // Check if the current student has interned at the target company
            if (current.getPreviousInternships().stream()
                    .anyMatch(internship -> internship.trim().equalsIgnoreCase(targetCompany.trim()))) {
                // Reconstruct the path
                List<UniversityStudent> path = new ArrayList<>();
                for (UniversityStudent at = current; at != null; at = previous.get(at)) {
                    path.add(at);
                }
                Collections.reverse(path);
                System.out.println("Found referral path for student " + start.getName() + " with weight: " + distances.get(current));
                return path;
            }

            // Process neighbors
            for (StudentGraph.Edge edge : graph.getNeighbors(current)) {
                UniversityStudent neighbor = edge.getTarget();
                if (visited.contains(neighbor)) {
                    continue;
                }

                int altDistance = distances.get(current) + edge.getWeight();

                // Debug statements
                System.out.println("Evaluating edge from " + current.getName() + " to " + neighbor.getName() + " with weight: " + edge.getWeight());
                System.out.println("Alternative distance for " + neighbor.getName() + ": " + altDistance);

                if (altDistance > distances.get(neighbor)) {
                    distances.put(neighbor, altDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);

                    // Debug statement
                    System.out.println("Updated distance for " + neighbor.getName() + " to " + altDistance);
                }
            }
        }

        // No path found
        System.out.println("No referral path found from " + start.getName() + " to " + targetCompany);
        return null;
    }
}