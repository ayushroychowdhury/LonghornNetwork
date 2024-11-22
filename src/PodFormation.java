import java.util.*;

/**
 * Class used to form pods of students
 */
public class PodFormation {

    private final StudentGraph graph;
    private List<List<String>> pods; // Field to store pod assignments

    /**
     * Constructor for PodFormation
     * @param graph graph representing the students and their connections
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
        this.pods = new ArrayList<>();
    }

    /**
     * Forms pods of students based on the given pod size.
     * Updates the `pods` field and prints the pod assignments.
     * @param podSize maximum number of students in each pod
     */
    public void formPods(int podSize) {
        pods = new ArrayList<>();
        Set<UniversityStudent> visited = new HashSet<>();

        // Process each connected component
        for (UniversityStudent student : graph.getAllNodes()) {
            if (!visited.contains(student)) {
                // Get connected component using BFS or DFS
                List<UniversityStudent> component = getConnectedComponent(student, visited);

                // Form MST for the component
                List<StudentGraph.Edge> mstEdges = buildMST(component);

                // Divide MST into pods and add to the field
                pods.addAll(divideIntoPods(mstEdges, component, podSize));
            }
        }

        // Print the pod assignments
        printPodAssignments();
    }

    /**
     * Prints the pod assignments.
     */
    private void printPodAssignments() {
        System.out.println("\nPod Assignments:");
        for (int i = 0; i < pods.size(); i++) {
            System.out.println("Pod " + (i + 1) + ": " + String.join(", ", pods.get(i)));
        }
    }

    /**
     * Finds all nodes in the same connected component.
     * @param start starting node for traversal
     * @param visited set to keep track of visited nodes
     * @return List of all nodes in the same connected component
     */
    private List<UniversityStudent> getConnectedComponent(UniversityStudent start, Set<UniversityStudent> visited) {
        List<UniversityStudent> component = new ArrayList<>();
        Queue<UniversityStudent> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            UniversityStudent current = queue.poll();
            component.add(current);

            for (StudentGraph.Edge neighborEdge : graph.getNeighbors(current)) {
                UniversityStudent neighbor = neighborEdge.neighbor;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return component;
    }

    /**
     * Builds a Minimum Spanning Tree (MST) for a connected component using Prim's algorithm.
     * @param component list of nodes in the connected component
     * @return List of edges in the MST
     */
    private List<StudentGraph.Edge> buildMST(List<UniversityStudent> component) {
        List<StudentGraph.Edge> mstEdges = new ArrayList<>();
        PriorityQueue<StudentGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        Set<UniversityStudent> inMST = new HashSet<>();

        // Start from the first node in the component
        UniversityStudent start = component.get(0);
        inMST.add(start);
        pq.addAll(graph.getNeighbors(start));

        while (!pq.isEmpty()) {
            StudentGraph.Edge edge = pq.poll();
            UniversityStudent neighbor = edge.neighbor;
            if (inMST.contains(neighbor)) continue;

            // Add edge to MST and mark node as visited
            mstEdges.add(edge);
            inMST.add(neighbor);

            // Add all neighbors of the new node
            for (StudentGraph.Edge neighborEdge : graph.getNeighbors(neighbor)) {
                if (!inMST.contains(neighborEdge.neighbor)) {
                    pq.add(neighborEdge);
                }
            }
        }

        return mstEdges;
    }

    /**
     * Divides the MST into pods of size ≤ podSize.
     * @param mstEdges list of edges in the MST
     * @param component list of nodes in the component
     * @param podSize maximum size of each pod
     * @return List of pods (each pod is a list of student names)
     */
    private List<List<String>> divideIntoPods(List<StudentGraph.Edge> mstEdges, List<UniversityStudent> component, int podSize) {
        List<List<String>> pods = new ArrayList<>();
        Queue<UniversityStudent> queue = new LinkedList<>(component);
        Set<UniversityStudent> assigned = new HashSet<>();

        while (!queue.isEmpty()) {
            List<String> pod = new ArrayList<>();
            while (!queue.isEmpty() && pod.size() < podSize) {
                UniversityStudent student = queue.poll();
                if (!assigned.contains(student)) {
                    pod.add(student.getName()); // Add student name to pod
                    assigned.add(student);
                }
            }
            pods.add(pod);
        }

        return pods;
    }
}
