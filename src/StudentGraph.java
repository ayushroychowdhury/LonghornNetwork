import java.util.*;

/**
 * Reference for a Student Graph
 */
public class StudentGraph {

    /**
     * Adjacent List to store StudentGraph
     */
    private Map<UniversityStudent, List<StudentGraphEdge>> graph;

    /**
     * Construct a student Graph from a list of Student objects
     * @param students List of Students
     */
    public StudentGraph(List<UniversityStudent> students) {
        graph = new HashMap<>();
        for (UniversityStudent student : students) {
            List edges = new LinkedList<>();
            for(UniversityStudent student2 : students) {
                if (student.equals(student2)) {continue;}
                edges.add(new StudentGraphEdge(student, student2, student.calculateConnectionStrength(student2)));
            }
            graph.put(student, edges);
        }

        // Print out Constructed Graph
        for (UniversityStudent student : graph.keySet()) {
            System.out.print(student.getName() + " -> ");
            List<StudentGraphEdge> edges = graph.get(student);
            for (StudentGraphEdge edge : edges) {
                System.out.print("[ " + edge.getDestStudent().getName() + " , " + edge.getWeight() + "] ");
                if (!edge.getSourceStudent().equals(student)) {
                    System.out.print("ERROR:" + student.getName() + " does not match above edge source student");
                }
            }
            System.out.println();
        }
    }

    /**
     * Construct an empty student Graph
     */
    public StudentGraph() {
        graph = new HashMap<>();
    }

    /**
     * Construct a graph from an existing graph
     * @param graph StudentGraph
     */
    public StudentGraph(StudentGraph graph){
        this.graph = new HashMap<>();
        this.graph.putAll(graph.graph);
    }

    /**
     * Getter for the List of Edges associated with a UniversityStudent
     * @param student UniversityStudent
     * @return List of StudentGraphEdge object's
     */
    public List<StudentGraphEdge> getEdges(UniversityStudent student) {
        try {
            return graph.get(student);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Get the list of UniversityStudent Objects in a Graph
     * @return List of UniversityStudent Objects
     */
    public List<UniversityStudent> getStudents() {
        List<UniversityStudent> students = new LinkedList<>();
        for (UniversityStudent student : graph.keySet()) {
            students.add(student);
        }
        return students;
    }

    /**
     * Add an Edge to a UniversityStudent
     * @param start UniversityStudent
     * @param end UniversityStudent
     * @param weight int
     */
    public void addEdge(UniversityStudent start, UniversityStudent end, int weight) {
        graph.get(start).add(new StudentGraphEdge(start, end , weight));
    }

    /**
     * Add an Edge to a UniversityStudent
     * @param start UniversityStudent
     * @param edge StudentGraphEdge
     */
    public void addEdge(UniversityStudent start, StudentGraphEdge edge) {
        graph.get(start).add(edge);
    }

    /**
     * Get the Strongest Edge for a UniversityStudent Node
     * @param student the node to check for the Strongest Edge
     * @param visited List of Visited Nodes
     * @return StudentGraphEdge
     */
    public StudentGraphEdge getStrongestEdge(UniversityStudent student, List<UniversityStudent> visited) {
        try {
            StudentGraphEdge r = new StudentGraphEdge(student,null , 0);

            for (StudentGraphEdge edge : graph.get(student)) {
                if (r.weight < edge.getWeight() && !visited.contains(edge.getDestStudent())) {
                    r.weight = edge.getWeight();
                    r.destStudent = edge.getDestStudent();
                }
            }

            return r;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Remove a Student from the Graph
     * @param student UniversityStudent
     */
    public void removeStudent(UniversityStudent student) {
        graph.remove(student);
        for (UniversityStudent students : graph.keySet()) {
            ListIterator i = graph.get(students).listIterator();
            while (i.hasNext()) {
                StudentGraphEdge edge = (StudentGraphEdge) i.next();
                if (edge.getDestStudent().equals(student)) {
                    i.remove();
                }
            }
        }
    }

    /**
     * Get the random root of a graph
     * @return UniversityStudent
     */
    public UniversityStudent getRoot() {
        try {
            return graph.keySet().iterator().next();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get number of Students in Graph
     * @return int
     */
    public int numberOfStudents() {
        return graph.size();
    }

    /**
     * Check if Graph is Empty
     * @return boolean
     */
    public boolean isEmpty() {
        return graph.isEmpty();
    }

    /**
     * Add a student to the Graph
     * @param student UniversityStudent
     */
    public void addStudent(UniversityStudent student) {
        graph.put(student, new LinkedList<>());
    }

    /**
     * Add a student to the Graph with an edge
     * @param student UniversityStudent
     * @param edge StudentGraphEdge
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
         * The cost to travel to Edge
         */
        private int weight;

        /**
         * Destination of Edge
         */
        private UniversityStudent destStudent;

        /**
         * Source of Edge
         */
        private UniversityStudent sourceStudent;

        /**
         * Construct a StudentGraphEdge
         *
         * @param sourceStudent UniversityStudent
         * @param destStudent       UniversityStudent
         * @param weight        int
         */
        public StudentGraphEdge(UniversityStudent sourceStudent, UniversityStudent destStudent, int weight) {
            this.weight = weight;
            this.destStudent = destStudent;
            this.sourceStudent = sourceStudent;
        }

        /**
         * Getter for StudentGraphEdge weight
         * @return int
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Getter for Destination StudentGraphEdge student
         * @return UniversityStudent
         */
        public UniversityStudent getDestStudent() {
            return destStudent;
        }

        /**
         * Getter for Source StudentGraphEdge student
         * @return UniversityStudent
         */
        public UniversityStudent getSourceStudent() {
            return sourceStudent;
        }
    }
}
