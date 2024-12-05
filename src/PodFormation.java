import java.util.*;

public class PodFormation {

    private StudentGraph graph;

    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Form pods by applying Prim’s algorithm to the graph and splitting the MST into pods of a given size.
     * @param podSize the maximum number of students per pod
     */
    public void formPods(int podSize) {
        Set<UniversityStudent> visited = new HashSet<>();
        List<List<UniversityStudent>> pods = new ArrayList<>();

        // For each student in the graph, apply Prim's algorithm to get the MST
        for (UniversityStudent student : graph.getAllNodes()) {
            if (!visited.contains(student)) {
                List<UniversityStudent> pod = new ArrayList<>();
                // Perform Prim's algorithm to get the MST for the current component
                formMST(student, visited, pod, podSize);
                pods.add(pod);
            }
        }

        // Print out the formed pods
        System.out.println("\nPod Formation:");
        int podCount = 1;
        for (List<UniversityStudent> pod : pods) {
            System.out.print("Pod " + podCount + ": ");
            for (UniversityStudent student : pod) {
                System.out.print(student.getName() + ", ");
            }
            System.out.println();
            podCount++;
        }
    }

    /**
     * Perform Prim's algorithm to form a Minimum Spanning Tree (MST) starting from the given student.
     * @param start the starting student for the MST
     * @param visited the set of visited students to avoid revisiting nodes
     * @param pod the list of students in the current pod
     * @param podSize the maximum size of each pod
     */
    private void formMST(UniversityStudent start, Set<UniversityStudent> visited, List<UniversityStudent> pod, int podSize) {
        // Priority queue to pick the student with the smallest connection strength
        PriorityQueue<StudentGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight));
        Map<UniversityStudent, Double> minEdgeWeight = new HashMap<>();
        minEdgeWeight.put(start, 0.0);
        pq.offer(new StudentGraph.Edge(start, 0.0));

        if (graph.getNeighbors(start) == null){
            return;
        }

        while (!pq.isEmpty() && pod.size() < podSize) {
            StudentGraph.Edge currentEdge = pq.poll();
            UniversityStudent currentStudent = currentEdge.neighbor;

            if (visited.contains(currentStudent)) {
                continue; // Skip already visited students
            }

            visited.add(currentStudent);
            pod.add(currentStudent);

            // If pod is full, stop adding students
            if (pod.size() >= podSize) {
                break;
            }

            // Check all neighbors of the current student
            for (StudentGraph.Edge edge : graph.getNeighbors(currentStudent)) {
                UniversityStudent neighbor = edge.neighbor;

                if (!visited.contains(neighbor) && !minEdgeWeight.containsKey(neighbor)) {
                    pq.offer(edge);
                    minEdgeWeight.put(neighbor, edge.weight);
                }
            }
        }
    }
}
