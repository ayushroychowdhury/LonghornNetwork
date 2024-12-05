import java.util.*;

/**
 * ReferralPathFinder class uses Dijkstra's algorithm to find the shortest path
 * (strongest connection path) from a starting student to any student who has
 * interned at a specified company.
 */
public class ReferralPathFinder {

    private StudentGraph graph;

    /**
     * Constructor for ReferralPathFinder with input of StudentGraph.
     * It determines the maximum connection weight in the graph for weight inversion.
     *
     * @param graph The StudentGraph representing student connections.
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }


    /**
     * Finds and prints referral paths from the start student to all students who have
     * interned at the target company. For each path found, it prints:
     * "Found referral path for student [Name] with weight: [Weight]".
     *
     * @param start         The starting UniversityStudent.
     * @param targetCompany The target company to find students who interned there.
     * @return A list of UniversityStudent objects representing the paths.
     * Returns an empty list if no such paths exist.
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        if (start == null || targetCompany == null || targetCompany.isEmpty()) {
            throw new IllegalArgumentException("Start student and target company must be non-null and non-empty.");
        }

        // identify  target students who have interned at the target company
        Set<UniversityStudent> targetStudents = findStudents(targetCompany);

        if (targetStudents.isEmpty()) {
            System.out.println("No referral path found");
            return new ArrayList<>();
        }

        if (start.getInternships().contains(targetCompany)) {
            System.out.println("No referral path found");
            return new ArrayList<>();
        }

        if (graph.getNeighbors(start) == null) {
            System.out.println("No referral path found");
            return new ArrayList<>();
        }

        // implement Dijkstra's algorithm with inverted weights
        // PriorityQueue to select the next student with the smallest cumulative inverted weight
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));

        // map stores the minimum inverted distance to each student
        Map<UniversityStudent, Integer> distances = new HashMap<>();

        // map stores the predecessor of each student for path reconstruction
        Map<UniversityStudent, UniversityStudent> predecessors = new HashMap<>();

        // Initialize distances
        for (UniversityStudent student : graph.getAllNodes()) {
            distances.put(student, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        // Add the start student to the priority queue
        priorityQueue.offer(new NodeDistance(start, 0));

        // Set to keep track of found target students
        Set<UniversityStudent> foundTargets = new HashSet<>();

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            UniversityStudent currentStudent = current.node;
            int currentDistance = current.distance;

            // If the current student is one of the target students and not already found
            if (targetStudents.contains(currentStudent) && !foundTargets.contains(currentStudent)) {

                // Add to found targets to avoid duplicate processing
                foundTargets.add(currentStudent);

                // Print the referral path statement
                System.out.println("Found referral path for student " + currentStudent.getName() + " with weight: " + currentDistance);

                // Continue searching for other target students
                // Optionally, you can remove the found student from targetStudents
                // to potentially optimize the search
            }

            // Iterate through all neighbors
            for (StudentGraph.Edge edge : graph.getNeighbors(currentStudent)) {
                UniversityStudent neighbor = edge.getTarget();
                int invertedWeight = invertWeight(edge.getWeight());

                // Calculate new distance
                int newDistance = currentDistance + invertedWeight;

                // If a shorter path to neighbor is found
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, currentStudent);
                    priorityQueue.offer(new NodeDistance(neighbor, newDistance));
                }
            }
        }

        if (foundTargets.isEmpty()) {
            System.out.println("No referral path found");
        }

        // Return a list of found target students
        return new ArrayList<>(foundTargets);
    }

    /**
     * Finds all students who have interned at the specified company.
     *
     * @param targetCompany The company to search for.
     * @return A set of UniversityStudent objects who have interned at targetCompany.
     */
    private Set<UniversityStudent> findStudents(String targetCompany) {
        Set<UniversityStudent> targetStudents = new HashSet<>();
        for (UniversityStudent student : graph.getAllNodes()) {
            List<String> internships = student.getInternships();
            if (internships != null) {
                for (String internship : internships) {
                    if (internship.equalsIgnoreCase(targetCompany)) {
                        targetStudents.add(student);
                        break;
                    }
                }
            }
        }
        return targetStudents;
    }

    /**
     * Inverts the connection weight to treat stronger connections as shorter paths by 10 - weight.
     *
     * @param originalWeight The original connection strength.
     * @return The inverted weight.
     */
    private int invertWeight(int originalWeight) {
        return 10 - originalWeight;
    }


    /**
     * Helper class to store a student and their current cumulative inverted distance.
     */
    private static class NodeDistance {
        UniversityStudent node;
        int distance;

        public NodeDistance(UniversityStudent node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
