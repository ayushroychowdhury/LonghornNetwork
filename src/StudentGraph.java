import java.util.*;

/**
 * Represents a graph of students and their connections.
 */
public class StudentGraph {
    // List of students in the graph
    private final ArrayList<UniversityStudent> students;
    // Adjacency list representing connections between students and their weights
    private final Map<UniversityStudent, Map<UniversityStudent, Integer>> adjacencyList;

    /**
     * Constructs a new StudentGraph from a list of students.
     *
     * @param students the list of students to include in the graph.
     */
    public StudentGraph(List<UniversityStudent> students) {
        this.students = new ArrayList<>(students); // Use ArrayList for internal storage
        this.adjacencyList = new HashMap<>();

        // Initialize adjacency list
        for (UniversityStudent student : students) {
            adjacencyList.putIfAbsent(student, new HashMap<>());
        }

        // Add connections based on calculateConnectionStrength
        for (UniversityStudent student1 : students) {
            for (UniversityStudent student2 : students) {
                if (!student1.equals(student2)) {
                    int connectionStrength = student1.calculateConnectionStrength(student2);
                    addConnection(student1, student2, connectionStrength);
                    System.out.println("Connection strength between " + student1.name + " and " + student2.name + ": " + connectionStrength);
                }
            }
        }
    }

    /**
     * Adds a connection between two students with a specific weight.
     *
     * @param student1 the first student.
     * @param student2 the second student.
     * @param weight   the weight of the connection (e.g., connection strength).
     */
    public void addConnection(UniversityStudent student1, UniversityStudent student2, int weight) {
        // Only add edges with non-zero weight
        if (weight > 0) {
            adjacencyList.get(student1).put(student2, weight);
            adjacencyList.get(student2).put(student1, weight); // Undirected graph
        }
    }

    /**
     * Retrieves the neighbors and connection weights of a given student.
     *
     * @param student the student whose neighbors to retrieve.
     * @return a map of neighbors and their connection weights.
     */
    public Map<UniversityStudent, Integer> getNeighbors(UniversityStudent student) {
        return adjacencyList.getOrDefault(student, new HashMap<>());
    }

    /**
     * Retrieves all the students in the graph.
     *
     * @return the list of students.
     */
    public ArrayList<UniversityStudent> getStudents() {
        return students;
    }

    /**
     * Retrieves a student by name.
     *
     * @param name the name of the student to retrieve.
     * @return the UniversityStudent object with the specified name, or null if not found.
     */
    public UniversityStudent getStudent(String name) {
        for (UniversityStudent student : students) {
            if (student.name.equalsIgnoreCase(name.trim())) {
                return student;
            }
        }
        return null; // Student not found
    }

    /**
     * Prints the graph for debugging purposes.
     */
    public void printGraph() {
        System.out.println("Student Graph:");
        for (var entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey().name + ": ");
            for (var neighbor : entry.getValue().entrySet()) {
                System.out.print("(" + neighbor.getKey().name + ", " + neighbor.getValue() + ") ");
            }
            System.out.println();
        }
    }
}
