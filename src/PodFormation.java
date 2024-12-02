import java.util.*;

public class PodFormation {
    StudentGraph graph;
    Map<Integer, List<UniversityStudent>> pods;
    /**
     * Constructs a PodFormation object with the given graph.
     * @param graph
     */
    public PodFormation(StudentGraph graph) {
        // Constructor
        this.graph = graph;
        pods = new HashMap<Integer, List<UniversityStudent>>();
    }
    /**
     * Forms pods of the given size using Prims algorithm.
     * @param podSize
     */
    public Map<Integer, List<UniversityStudent>> formPods(int podSize) {
        // Forms pods of the given size using Prims algorithm
        List<UniversityStudent> students = graph.getStudents();
        Set<UniversityStudent> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.getWeight() - e2.getWeight();
            }
        });
        for(UniversityStudent start : students) {
            if(visited.contains(start)) {
                continue;
            }
            int podId = pods.size() + 1;
            pods.put(podId, new ArrayList<>());
            pods.get(podId).add(start);
            visited.add(start);
            for (Edge edge : graph.getEdges(start)) {
                pq.add(edge);
            }
            while (!pq.isEmpty()) {
                Edge edge = pq.poll();
                UniversityStudent student = edge.getStudent();
                if (!visited.contains(student)) {
                    if(pods.get(podId).size() < podSize) {
                        visited.add(student);
                        pods.get(podId).add(student);
                    } else {
                        break;
                    }
                    for (Edge nextEdge : graph.getEdges(student)) {
                        pq.add(nextEdge);
                    }
                }
            }
            pq.clear();
        }
        return pods;
    }
}
