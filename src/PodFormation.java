import java.util.*;

public class PodFormation {
    StudentGraph graph;
    Map<UniversityStudent, List<UniversityStudent>> pods;
    /**
     * Constructs a PodFormation object with the given graph.
     * @param graph
     */
    public PodFormation(StudentGraph graph) {
        // Constructor
        this.graph = graph;
        pods = new HashMap<>();
    }
    /**
     * Forms pods of the given size using Prims algorithm.
     * @param podSize
     */
    public void formPods(int podSize) {
        // Forms pods of the given size using Prims algorithm
        List<UniversityStudent> students = graph.getStudents();
        Set<UniversityStudent> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.getWeight() - e2.getWeight();
            }
        });
        UniversityStudent start = students.get(0);
        pods.put(start, new ArrayList<>());
        pods.get(start).add(start);
        visited.add(start);
        for (Edge edge : graph.getEdges(start)) {
            pq.add(edge);
        }
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            UniversityStudent student = edge.getStudent();
            if (!visited.contains(student)) {
                visited.add(student);
                if(pods.get(start).size() < podSize) {
                    pods.get(start).add(student);
                } else {
                    start = student;
                    pods.put(start, new ArrayList<>());
                    pods.get(start).add(start);
                }
                for (Edge nextEdge : graph.getEdges(student)) {
                    pq.add(nextEdge);
                }
            }
        }
    }
}
