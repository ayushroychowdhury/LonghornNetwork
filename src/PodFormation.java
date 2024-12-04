import java.util.*;

/**
 * Forms pods of students based on the student graph using Prim's algorithm.
 */
public class PodFormation {
    private StudentGraph graph;
    private Map<UniversityStudent, List<UniversityStudent>> pods;

    /**
     * Constructs a PodFormation object using the given graph.
     *
     * @param graph the graph representing relationships between students
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
        this.pods = new HashMap<>();
    }

    /**
     * Forms pods of the specified size using Prim's algorithm.
     *
     * @param podSize the maximum size of each pod
     */
    public void formPods(int podSize) {
        Set<UniversityStudent> visited = new HashSet<>();
        List<UniversityStudent> allStudents = new ArrayList<>(graph.getAllStudents());

        for (UniversityStudent student : allStudents) {
            if (!visited.contains(student)) {
                // Get connected component using Prim's algorithm
                List<UniversityStudent> connectedComponent = getConnectedComponentPrim(student, visited);

                // Divide the connected component into pods
                List<List<UniversityStudent>> componentPods = divideIntoPods(connectedComponent, podSize);
                assignPods(componentPods);
            }
        }
    }

    /**
     * Retrieves all students in a connected component using Prim's algorithm.
     *
     * @param start   the starting student
     * @param visited the set of visited students
     * @return a list of students in the connected component
     */
    private List<UniversityStudent> getConnectedComponentPrim(UniversityStudent start, Set<UniversityStudent> visited) {
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        List<UniversityStudent> component = new ArrayList<>();
        visited.add(start);
        component.add(start);

        // Add all edges from the starting node
        addEdgesToQueue(start, edgeQueue, visited);

        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            if (visited.contains(edge.student2)) continue;

            visited.add(edge.student2);
            component.add(edge.student2);
            addEdgesToQueue(edge.student2, edgeQueue, visited);
        }

        return component;
    }

    /**
     * Adds edges from a student to the priority queue for processing.
     *
     * @param student   the student whose edges are being added
     * @param edgeQueue the priority queue of edges
     * @param visited   the set of visited students
     */
    private void addEdgesToQueue(UniversityStudent student, PriorityQueue<Edge> edgeQueue, Set<UniversityStudent> visited) {
        for (UniversityStudent neighbor : graph.getConnections(student)) {
            if (!visited.contains(neighbor)) {
                int weight = student.calculateConnectionStrength(neighbor);
                edgeQueue.add(new Edge(student, neighbor, weight));
            }
        }
    }

    /**
     * Divides a connected component into pods of the specified size.
     *
     * @param component the list of students in the connected component
     * @param podSize   the maximum size of each pod
     * @return a list of pods
     */
    private List<List<UniversityStudent>> divideIntoPods(List<UniversityStudent> component, int podSize) {
        List<List<UniversityStudent>> pods = new ArrayList<>();
        List<UniversityStudent> currentPod = new ArrayList<>();

        for (UniversityStudent student : component) {
            currentPod.add(student);
            if (currentPod.size() == podSize) {
                pods.add(new ArrayList<>(currentPod));
                currentPod.clear();
            }
        }

        if (!currentPod.isEmpty()) {
            pods.add(currentPod);
        }

        return pods;
    }

    /**
     * Assigns the pods to the global map.
     *
     * @param componentPods the list of pods to assign
     */
    private void assignPods(List<List<UniversityStudent>> componentPods) {
        for (List<UniversityStudent> pod : componentPods) {
            for (UniversityStudent student : pod) {
                pods.put(student, pod);
            }
        }
    }

    /**
     * Retrieves the formed pods.
     *
     * @return a map of students and their corresponding pods
     */
    public Map<UniversityStudent, List<UniversityStudent>> getPods() {
        return pods;
    }

    /**
     * Represents an edge in the graph.
     */
    public static class Edge {
        UniversityStudent student1, student2;
        int weight;

        public Edge(UniversityStudent student1, UniversityStudent student2, int weight) {
            this.student1 = student1;
            this.student2 = student2;
            this.weight = weight;
        }
    }
}
