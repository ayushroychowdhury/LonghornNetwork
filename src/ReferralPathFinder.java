import java.util.*;

/**
 * Finds referral paths in a student graph about companies.
 */
public class ReferralPathFinder {

    private StudentGraph graph;

    /**
     * Constructs a ReferralPathFinder using the given student graph.
     *
     * @param graph the graph representing relationships between students
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds a referral path from a starting student to a target company.
     *
     * @param start         the starting student
     * @param targetCompany the company for which to find the referral path
     * @return a list of students representing the referral path, or an empty list if no path exists
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Priority queue to prioritize paths with stronger connections
        PriorityQueue<Pathcost> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost));
        Map<UniversityStudent, UniversityStudent> parentMap = new HashMap<>();
        Map<UniversityStudent, Integer> costMap = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();

        // Initialize the starting student
        priorityQueue.add(new Pathcost(start, 0)); // Starting student with cost 0
        costMap.put(start, Integer.valueOf(0));

        while (!priorityQueue.isEmpty()) {
            Pathcost current = priorityQueue.poll();
            UniversityStudent currentStudent = current.student;

            // Skip if the student has already been processed
            if (visited.contains(currentStudent)) continue;
            visited.add(currentStudent);

            // If the target company is found, build the path
            if (currentStudent.previousInternships.contains(targetCompany)) {
                return buildPath(parentMap, start, currentStudent);
            }

            // Explore all neighbors
            for (UniversityStudent neighbor : graph.getConnections(currentStudent)) {
                if (visited.contains(neighbor)) continue;

                // Calculate the "cost" for this neighbor (inverted connection strength)
                int connectionStrength = currentStudent.calculateConnectionStrength(neighbor);
                int newCost = costMap.getOrDefault(currentStudent, Integer.valueOf(Integer.MAX_VALUE)) - connectionStrength;

                // Update the path if a stronger connection or shorter path is found
                if (newCost < costMap.getOrDefault(neighbor, Integer.valueOf(Integer.MAX_VALUE))) {
                    costMap.put(neighbor, Integer.valueOf(newCost));
                    parentMap.put(neighbor, currentStudent);
                    priorityQueue.add(new Pathcost(neighbor, newCost));
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    /**
     * Builds the referral path from the start student to the target student.
     *
     * @param parentMap map containing the parent of each visited student
     * @param start     the starting student
     * @param end       the target
     * @return list of students representing the referral path
     */
    private List<UniversityStudent> buildPath(Map<UniversityStudent, UniversityStudent> parentMap,
                                              UniversityStudent start, UniversityStudent end) {
        List<UniversityStudent> path = new ArrayList<>();
        for (UniversityStudent at = end; at != null; at = parentMap.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Helper class to store students and costs in Dijkstra's algorithm.
     */
    private static class Pathcost {
        UniversityStudent student;
        int cost;

        Pathcost(UniversityStudent student, int cost) {
            this.student = student;
            this.cost = cost;
        }
    }
}
