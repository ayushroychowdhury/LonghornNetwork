import java.util.*;

public class ReferralPathFinder {
    private StudentGraph graph;
    /**
     * Constructor for the ReferralPathFinder class.
     * @param graph
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor
        this.graph = graph;
    }
    /**
     * Finds the shortest referral path from the start student to the target company using Dijkstra's algorithm.
     * @param start
     * @param targetCompany
     * @return
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Finds the shortest referral path from the start student to the target company using Dijkstra's algorithm, inverting the edge weights
        Map<UniversityStudent, Integer> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> parents = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.getWeight() - e2.getWeight();
            }
        });
        distances.put(start, 0);
        pq.add(new Edge(start, 0));
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            UniversityStudent student = edge.getStudent();
            if (!visited.contains(student)) {
                visited.add(student);
                for (Edge nextEdge : graph.getEdges(student)) {
                    UniversityStudent nextStudent = nextEdge.getStudent();
                    int weight = 10 - nextEdge.getWeight();
                    if (!visited.contains(nextStudent)) {
                        int newDistance = distances.get(student) + weight;
                        if (!distances.containsKey(nextStudent) || newDistance < distances.get(nextStudent)) {
                            distances.put(nextStudent, newDistance);
                            parents.put(nextStudent, student);
                            pq.add(new Edge(nextStudent, newDistance));
                        }
                    }
                }
            }
        }
        List<UniversityStudent> path = new ArrayList<>();
        UniversityStudent current = getStudentByCompany(targetCompany);
        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }
        Collections.reverse(path); 
        return path;
    }

    /**
     * Returns the student with the given company name.
     * @param company
     * @return
     */
    private UniversityStudent getStudentByCompany(String company) {
        // Returns the student with the given company name
        for (UniversityStudent student : graph.getStudents()) {
            if (student.getPreviousInternships().contains(company)) {
                return student;
            }
        }
        return null;
    }
}
