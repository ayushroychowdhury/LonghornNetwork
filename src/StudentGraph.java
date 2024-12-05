import java.util.*;

/**
 * Graph to represent connections between UniversityStudents (for use with Pod Formation and Referral Path Finding)
 */
public class StudentGraph {

    // Adjacency list representation of the graph using UniversityStudent as the key
    private Map<UniversityStudent, List<Edge>> graph;

    /**
     * Initializes a weighted graph from a list of students
     * @param students list of University Students
     */
    public StudentGraph(List<UniversityStudent> students) {
        this.graph = new HashMap<>();

        // Add all students as nodes
        for (UniversityStudent student : students) {
            addStudent(student);
        }

        // Add edges between all pairs of students using calculateConnectionStrength
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                UniversityStudent student1 = students.get(i);
                UniversityStudent student2 = students.get(j);

                int weight = student1.calculateConnectionStrength(student2);
                addEdge(student1, student2, weight);
            }
        }
    }

    /**
     * Adds a student (node) to the graph
     * @param student student to add
     */
    private void addStudent(UniversityStudent student) {
        graph.putIfAbsent(student, new ArrayList<>());
    }

    /**
     * Adds a weighted edge between two students
     * @param student1 first student
     * @param student2 second student
     * @param weight weight between students
     */
    private void addEdge(UniversityStudent student1, UniversityStudent student2, double weight) {
        // Add the edge in both directions (undirected graph)
        graph.get(student1).add(new Edge(student2, weight));
        graph.get(student2).add(new Edge(student1, weight));
    }

    /**
     * Returns all neighbors (edges) of a specific student
     * @param student student to find edges of
     * @return list of neighboring students
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return graph.getOrDefault(student, new ArrayList<>());
    }

    /**
     * Returns all students (nodes) in the graph
     * @return all nodes in the graph
     */
    public Set<UniversityStudent> getAllNodes() {
        return graph.keySet();
    }

    /**
     * Returns the UniversityStudent object corresponding to a given name
     * @param name name of the student to find
     * @return the UniversityStudent object if found, null otherwise
     */
    public UniversityStudent getStudent(String name) {
        for (UniversityStudent student : graph.keySet()) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null; // Return null if no student with the given name is found
    }

    /**
     * Debugging utility: Prints the adjacency list
     */
    public void printGraph() {
        for (Map.Entry<UniversityStudent, List<Edge>> entry : graph.entrySet()) {
            System.out.println(entry.getKey().getName() + " -> " + entry.getValue());
        }
    }

    /**
     * Inner class to represent an edge
     */
    public static class Edge {
        UniversityStudent neighbor;
        double weight;

        /**
         * Constructor for edges
         * @param neighbor neighbor connected by edge
         * @param weight weight of edge
         */
        public Edge(UniversityStudent neighbor, double weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + neighbor.getName() + ", " + String.format("%.2f", weight) + ")";
        }
    }
}
