import java.util.*;

public class ReferralPathFinder {
    private StudentGraph graph;
    private Map<UniversityStudent, Integer> distances;
    private Map<UniversityStudent, UniversityStudent> parents;
    private Set<UniversityStudent> visited;
    private UniversityStudent start;
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
        distances = new HashMap<>();
        parents = new HashMap<>();
        visited = new HashSet<>();
        this.start = start;
        for(UniversityStudent student : graph.getStudents()) {
            distances.put(student, Integer.MAX_VALUE);
            parents.put(student, null);
            visited.clear();
        }
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
        if(path.size() == 1) {
            return new ArrayList<>();
        }
        Collections.reverse(path); 
        //printTotalConnectionWeight(targetCompany);
        return path;
    }

    /**
     * Prints the total connection weight of the shortest referral path.
     */
    private void printTotalConnectionWeight(String target) {
        // Prints the total connection weight of the shortest referral path
        int totalWeight = 0;
        for(UniversityStudent student : graph.getStudents()) {
            if(student.previousInternships.contains(target)) {
                UniversityStudent current = student;
                while(parents.get(current) != null) {
                    UniversityStudent parent = parents.get(current);
                    for(Edge edge : graph.getEdges(parent)) {
                        if(edge.getStudent().equals(current)) {
                            totalWeight += edge.getWeight();
                        }
                    }
                    current = parent;
                }
            }
        }
        System.out.println("Total connection weight: " + totalWeight);
    }

    /**
     * Returns the student with the given company name.
     * @param company
     * @return
     */
    private UniversityStudent getStudentByCompany(String company) {
        // Returns the student with the given company name
        UniversityStudent shortestStudent = null;
        for (UniversityStudent student : graph.getStudents()) {
            if (student.getPreviousInternships().contains(company) && student != start) {
                if(shortestStudent == null || distances.get(student) < distances.get(shortestStudent)) {
                    shortestStudent = student;
                }
            }
        }
        return shortestStudent;
    }
}
