import java.util.*;

/**
 * Class that creates a weighted undirected graph
 * between students.
 */
public class StudentGraph {

    // Adjacency list to store the graph
    private Map<UniversityStudent, List<Edge>> adjacencyList;

    /**
     * Constructor to initialize the graph structure.
     * @param students The list of students to add as nodes in the graph.
     */
    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();
        for (UniversityStudent student : students) {
            adjacencyList.put(student, new ArrayList<>()); // Each student has an empty edge list initially
        }
    }

    /**
     * Adds a weighted undirected edge between two students.
     * @param one student 1.
     * @param two student 2.
     * @param weight The weight (connection strength) of the edge.
     */
    public void addEdge(UniversityStudent one, UniversityStudent two, int weight) {

        adjacencyList.get(one).add(new Edge(two, weight));
        adjacencyList.get(one).add(new Edge(two, weight));
    }

    /**
     * Returns a list of edges connected to a specific student.
     * @param student The student whose edges to retrieve.
     * @return A list of edges connected to the student.
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjacencyList.getOrDefault(student, Collections.emptyList());
    }

    /**
     * Retrieves all nodes (students) in the graph.
     * @return A set of all students in the graph.
     */
    public Set<UniversityStudent> getAllNodes() {
        return adjacencyList.keySet();
    }


    /**
     * Inner class for edges in student graph.
     */
    public static class Edge {
        private UniversityStudent otherStudent;
        private int weight;

        /**
         * Constructor for creating edge.
         * @param otherStudent destination of edge.
         * @param weight Connection strength.
         */
        public Edge(UniversityStudent otherStudent, int weight) {
            this.otherStudent = otherStudent;
            this.weight = weight;
        }

        /**
         *
         * @return the student at other end of edge
         */
        public UniversityStudent getOtherStudent() {
            return otherStudent;
        }

        /**
         *
         * @return weight of edge in graph
         */
        public int getWeight() {
            return weight;
        }
    }


}
