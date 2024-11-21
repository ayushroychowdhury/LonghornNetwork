import java.util.*;

/**
 * Represents a graph of students.
 */
public class StudentGraph {
    private Map<String, List<Edge>> adjacencyList;

    /**
     * Constructs an empty StudentGraph.
     */
    public StudentGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds an edge between two students with the specified weight.
     *
     * @param student1 the first student
     * @param student2 the second student
     * @param weight   the weight of the edge
     */
    public void addEdge(String student1, String student2, int weight) {
        adjacencyList.putIfAbsent(student1, new ArrayList<>());
        adjacencyList.putIfAbsent(student2, new ArrayList<>());
        adjacencyList.get(student1).add(new Edge(student2, weight));
        adjacencyList.get(student2).add(new Edge(student1, weight));
    }

    /**
     * Returns the neighbors of the specified student.
     *
     * @param student the student whose neighbors are to be returned
     * @return a list of edges representing the neighbors of the student
     */
    public List<Edge> getNeighbors(String student) {
        return adjacencyList.getOrDefault(student, new ArrayList<>());
    }

    /**
     * Returns all the nodes in the graph.
     *
     * @return a set of all the nodes in the graph
     */
    public Set<String> getAllNodes() {
        return adjacencyList.keySet();
    }

    /**
     * Represents an edge in the student graph.
     */
    public static class Edge {
        private String student;
        private int weight;

        /**
         * Constructs an Edge with the specified student and weight.
         *
         * @param student the student at the end of the edge
         * @param weight  the weight of the edge
         */
        public Edge(String student, int weight) {
            this.student = student;
            this.weight = weight;
        }

        /**
         * Returns the student at the end of the edge.
         *
         * @return the student at the end of the edge
         */
        public String getStudent() {
            return student;
        }

        /**
         * Returns the weight of the edge.
         *
         * @return the weight of the edge
         */
        public int getWeight() {
            return weight;
        }
    }
}