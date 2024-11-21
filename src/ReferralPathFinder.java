import java.util.*;

/**
 * Uses Dijkstra's algorithm to find the shortest weighted path for a student trying to
 * get a referral at a given company
 */
public class ReferralPathFinder {

    private HashMap<String, List<List<Object>>> graph;
    /**
     * The referral pathfinder constructor.
     * @param graph The graph to find referrals from.
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor. Inverts connectionStrength to be distance in a new graph.
        this.graph = graph.getAdjList();
    }

    /**
     * Finds the shortest weighted path between a starting student
     * and any student who has interned at a given company
     * @param start The student to begin the search from
     * @param targetCompany The company to search for
     * @return A path from the starting student to a student with an internship at the company
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Method signature only
        // Priority queue for Dijkstra's algorithm: stores (current distance, student, path)
        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> 10 - a.distance));
        Map<String, Integer> distances = new HashMap<>(); // Map to track shortest distances
        Map<String, UniversityStudent> previousStudent = new HashMap<>(); // For reconstructing the path
        Set<String> visited = new HashSet<>(); // To track visited nodes

        // Initialize the priority queue with the starting student
        pq.add(new DijkstraNode(start, 0, null));
        distances.put(start.name, 0);

        while (!pq.isEmpty()) {
            // Get the student with the smallest distance
            DijkstraNode currentNode = pq.poll();
            UniversityStudent currentStudent = currentNode.student;

            if (visited.contains(currentStudent.name)) {
                continue;
            }
            visited.add(currentStudent.name);

            // Check if the current student has interned at the target company
            if (currentStudent.previousInternships.contains(targetCompany)) {
                // Reconstruct and return the path
                return reconstructPath(previousStudent, currentStudent);
            }

            // Explore neighbors
            List<List<Object>> neighbors = graph.getOrDefault(currentStudent.name, new ArrayList<>());
            for (List<Object> neighborInfo : neighbors) {
                UniversityStudent neighbor = (UniversityStudent) Main.nameMap.get(neighborInfo.get(0));
                int weight = (int) neighborInfo.get(1);

                int newDist = distances.getOrDefault(currentStudent.name, Integer.MAX_VALUE) + weight;
                if (newDist < distances.getOrDefault(neighbor.name, Integer.MAX_VALUE)) {
                    distances.put(neighbor.name, newDist);
                    pq.add(new DijkstraNode(neighbor, newDist, currentStudent));
                    previousStudent.put(neighbor.name, currentStudent);
                }
            }
        }

        // If no path found, return an empty list
        return new ArrayList<>();
    }


    /**
     * Reconstructs the shortest path from the previousStudent map.
     *
     * @param previousStudent The map tracking the previous student in the shortest path.
     * @param endStudent      The target student with the internship at the company.
     * @return The reconstructed path as a list of UniversityStudents.
     */
    private List<UniversityStudent> reconstructPath(Map<String, UniversityStudent> previousStudent, UniversityStudent endStudent) {
        LinkedList<UniversityStudent> path = new LinkedList<>();
        UniversityStudent current = endStudent;

        while (current != null) {
            path.addFirst(current);
            current = previousStudent.get(current.name);
        }

        return path;
    }


    // Helper class for Dijkstra's algorithm nodes
    private static class DijkstraNode {
        UniversityStudent student;
        int distance;
        UniversityStudent previous;

        public DijkstraNode(UniversityStudent student, int distance, UniversityStudent previous) {
            this.student = student;
            this.distance = distance;
            this.previous = previous;
        }
    }
}
