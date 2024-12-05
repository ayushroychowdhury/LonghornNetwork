import java.util.*;

/**
 * Represents a graph of students where nodes are students and edges are their connection strengths.
 */
public class StudentGraph {
    private Map<UniversityStudent, List<Edge>> adjacencyList;
    private Map<String, UniversityStudent> studentMap;

    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();
        studentMap = new HashMap<>();
        for (UniversityStudent student : students) {
            adjacencyList.put(student, new ArrayList<>());
            studentMap.put(student.getName(), student);
        }

        // Build the graph by adding edges between all pairs of students.
        for (int i = 0; i < students.size(); i++) {
            UniversityStudent s1 = students.get(i);
            for (int j = i + 1; j < students.size(); j++) {
                UniversityStudent s2 = students.get(j);
                int weight = s1.calculateConnectionStrength(s2);
                if (weight > 0) {
                    addEdge(s1, s2, weight);
                }
            }
        }
    }

    public void addEdge(UniversityStudent s1, UniversityStudent s2, int weight) {
        Edge edge1 = new Edge(s1, s2, weight);
        Edge edge2 = new Edge(s2, s1, weight); // Undirected graph.
        adjacencyList.get(s1).add(edge1);
        adjacencyList.get(s2).add(edge2);

        // Debug statements
        System.out.println("Added edge between " + s1.getName() + " and " + s2.getName() + " with weight: " + weight);
    }

    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjacencyList.getOrDefault(student, new ArrayList<>());
    }

    public List<Edge> getEdgesFrom(UniversityStudent student) {
        return adjacencyList.getOrDefault(student, new ArrayList<>());
    }

    public Set<UniversityStudent> getAllNodes() {
        return adjacencyList.keySet();
    }

    public UniversityStudent getStudent(String name) {
        return studentMap.get(name);
    }

    /**
     * Represents an edge in the student graph.
     */
    public static class Edge {
        private UniversityStudent source;
        private UniversityStudent target;
        private int weight;

        public Edge(UniversityStudent source, UniversityStudent target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public UniversityStudent getSource() {
            return source;
        }

        public UniversityStudent getTarget() {
            return target;
        }

        public int getWeight() {
            return weight;
        }
    }
}