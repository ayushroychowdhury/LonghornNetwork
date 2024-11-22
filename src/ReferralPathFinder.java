import java.util.*;

public class ReferralPathFinder {

    private StudentGraph graph;

    /**
     * Constructor for ReferralPathFinder
     * @param graph graph representing the students and their connections
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds the shortest referral path from the start student to a student who works at the target company.
     * @param start student to start the search from
     * @param targetCompany company to find a referral path to
     * @return list of students representing the referral path, or an empty list if no path is found
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Priority queue to hold students to explore, with the lowest connection strength (path length) first
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        // Map to track the shortest path to each student
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        // Map to track the shortest path distance to each student
        Map<UniversityStudent, Double> distances = new HashMap<>();

        // Initialize distances
        for (UniversityStudent student : graph.getAllNodes()) {
            distances.put(student, Double.MAX_VALUE);
        }
        distances.put(start, 0.0);

        pq.add(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            UniversityStudent currentStudent = current.student;
            double currentDistance = current.distance;

            // Check if the current student works at the target company
            if (currentStudent.getPreviousInternships().contains(targetCompany)) {
                return buildPath(previous, currentStudent);
            }

            // Explore neighbors
            for (StudentGraph.Edge edge : graph.getNeighbors(currentStudent)) {
                UniversityStudent neighbor = edge.neighbor;
                // Invert the weight using 15 - weight
                double invertedWeight = 15 - edge.weight;
                double newDistance = currentDistance + invertedWeight;

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentStudent);
                    pq.add(new Node(neighbor, newDistance));
                }
            }
        }

        // No path found, return an empty list
        return new ArrayList<>();
    }

    /**
     * Builds the path from the start student to the target student
     * @param previous map of previous nodes for path reconstruction
     * @param target the target student
     * @return list of students representing the path
     */
    private List<UniversityStudent> buildPath(Map<UniversityStudent, UniversityStudent> previous, UniversityStudent target) {
        List<UniversityStudent> path = new LinkedList<>();
        for (UniversityStudent at = target; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Helper class to represent a node in the priority queue for Dijkstra's algorithm
     */
    private static class Node {
        UniversityStudent student;
        double distance;

        Node(UniversityStudent student, double distance) {
            this.student = student;
            this.distance = distance;
        }
    }

    /**
     * Prints the referral path from the start student to the target company
     * @param path list of UniversityStudents representing the referral path
     */
    public void printReferralPath(List<UniversityStudent> path, String targetCompany) {
        if (path.isEmpty()) {
            System.out.println("No referral path found.");
        } else {
            System.out.print("Path to " + targetCompany + ": ");
            for (int i = 0; i < path.size(); i++) {
                UniversityStudent student = path.get(i);
                System.out.print(student.getName());
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }
}
