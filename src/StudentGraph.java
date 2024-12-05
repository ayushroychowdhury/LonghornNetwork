import java.util.*;

/**
 * Reference for a Student Graph
 *
 * This class represents a graph of university students, where each student is a node and edges represent connections between students.
 * The graph is implemented using an adjacency list.
 *
 * Author: Cooper Nelson
 */
public class StudentGraph {

    /**
     * Adjacency list to store the student graph
     */
    private final Map<UniversityStudent, List<StudentGraphEdge>> graph;

    /**
     * Construct a student graph from a list of student objects
     *
     * @param students List of students
     */
    public StudentGraph(List<UniversityStudent> students) {
        graph = new HashMap<>();
        for (UniversityStudent student : students) {
            List<StudentGraphEdge> edges = new LinkedList<>();
            for (UniversityStudent connectingStudent : students) {
                if (!student.equals(connectingStudent)) {
                    edges.add(new StudentGraphEdge(student, connectingStudent, student.calculateConnectionStrength(connectingStudent)));
                }
            }
            graph.put(student, edges);
        }
    }

    /**
     * Construct an empty student graph
     */
    public StudentGraph() {
        graph = new HashMap<>();
    }

    /**
     * Construct a graph from an existing graph
     * Everything but UniversityStudent objects are deep-copied
     *
     * @param original StudentGraph
     */
    public StudentGraph(StudentGraph original) {
        graph = new HashMap<>();
        for (Map.Entry<UniversityStudent, List<StudentGraphEdge>> entry : original.graph.entrySet()) {
            graph.put(entry.getKey(), new LinkedList<>());
            for (StudentGraphEdge edge : entry.getValue()) {
                graph.get(entry.getKey()).add(new StudentGraphEdge(edge.getSourceStudent(), edge.getDestStudent(), edge.getWeight()));
            }
        }
    }

    /**
     * Getter for the list of edges associated with a UniversityStudent
     *
     * @param student UniversityStudent
     * @return List of StudentGraphEdge objects
     */
    public List<StudentGraphEdge> getEdges(UniversityStudent student) {
        return graph.getOrDefault(student, null);
    }

    /**
     * Get the list of UniversityStudent objects in the graph
     *
     * @return List of UniversityStudent objects
     */
    public List<UniversityStudent> getStudents() {
        return new LinkedList<>(graph.keySet());
    }

    /**
     * Get a UniversityStudent object from the graph via its name
     *
     * @param name String
     * @return UniversityStudent
     */
    public UniversityStudent getStudent(String name) {
        for (UniversityStudent student : graph.keySet()) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Add an edge to a UniversityStudent
     *
     * @param start  UniversityStudent
     * @param end    UniversityStudent
     * @param weight int
     */
    public void addEdge(UniversityStudent start, UniversityStudent end, int weight) {
        graph.get(start).add(new StudentGraphEdge(start, end, weight));
    }

    /**
     * Add an edge to a UniversityStudent
     *
     * @param start UniversityStudent
     * @param edge  StudentGraphEdge
     */
    public void addEdge(UniversityStudent start, StudentGraphEdge edge) {
        graph.get(start).add(edge);
    }

    /**
     * Get the strongest edge for a UniversityStudent node
     *
     * @param student the node to check for the strongest edge
     * @param visited List of visited nodes
     * @return StudentGraphEdge
     */
    public StudentGraphEdge getStrongestEdge(UniversityStudent student, List<UniversityStudent> visited) {
        StudentGraphEdge strongestEdge = new StudentGraphEdge(student, null, 0);
        for (StudentGraphEdge edge : graph.get(student)) {
            if (strongestEdge.weight < edge.getWeight() && !visited.contains(edge.getDestStudent())) {
                strongestEdge = edge;
            }
        }
        return strongestEdge;
    }

    /**
     * Remove a student from the graph
     *
     * @param student UniversityStudent
     */
    public void removeStudent(UniversityStudent student) {
        graph.remove(student);
        for (UniversityStudent students : graph.keySet()) {
            ListIterator<StudentGraphEdge> iterator = graph.get(students).listIterator();
            while (iterator.hasNext()) {
                StudentGraphEdge edge = iterator.next();
                if (edge.getDestStudent().equals(student)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Get the random root of a graph
     *
     * @return UniversityStudent
     */
    public UniversityStudent getRoot() {
        return graph.keySet().stream().findFirst().orElse(null);
    }

    /**
     * Get the number of students in the graph
     *
     * @return int
     */
    public int numberOfStudents() {
        return graph.size();
    }

    /**
     * Check if the graph is empty
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return graph.isEmpty();
    }

    /**
     * Add a student to the graph
     *
     * @param student UniversityStudent
     */
    public void addStudent(UniversityStudent student) {
        graph.put(student, new LinkedList<>());
    }

    /**
     * Add a student to the graph with an edge
     *
     * @param student UniversityStudent
     * @param edge    StudentGraphEdge
     */
    public void addStudent(UniversityStudent student, StudentGraphEdge edge) {
        graph.put(student, new LinkedList<>());
        graph.get(student).add(edge);
    }

    /**
     * Reference for a StudentGraphEdge
     */
    public class StudentGraphEdge {
        /**
         * The cost to travel to the edge
         */
        private int weight;

        /**
         * Destination of the edge
         */
        private UniversityStudent destStudent;

        /**
         * Source of the edge
         */
        private final UniversityStudent sourceStudent;

        /**
         * Construct a StudentGraphEdge
         *
         * @param sourceStudent UniversityStudent
         * @param destStudent   UniversityStudent
         * @param weight        int
         */
        public StudentGraphEdge(UniversityStudent sourceStudent, UniversityStudent destStudent, int weight) {
            this.weight = weight;
            this.destStudent = destStudent;
            this.sourceStudent = sourceStudent;
        }

        /**
         * Getter for StudentGraphEdge weight
         *
         * @return int
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Getter for destination StudentGraphEdge student
         *
         * @return UniversityStudent
         */
        public UniversityStudent getDestStudent() {
            return destStudent;
        }

        /**
         * Getter for source StudentGraphEdge student
         *
         * @return UniversityStudent
         */
        public UniversityStudent getSourceStudent() {
            return sourceStudent;
        }
    }
}