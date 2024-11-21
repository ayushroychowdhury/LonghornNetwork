import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Reference for a Student Graph
 */
public class StudentGraph {

    /**
     * Adjacent List to store StudentGraph
     */
    private Map<UniversityStudent, List<StudentGraphEdge>> graph = new HashMap<UniversityStudent, List<StudentGraphEdge>>();

    /**
     * Construct a student Graph from a list of Student objects
     * @param students List of Students
     */
    public StudentGraph(List<UniversityStudent> students) {

    }

    /**
     * Getter for the List of Edges associated with a UniversityStudent
     * @param student UniversityStudent
     * @return List of StudentGraphEdge object's
     */
    public Map<UniversityStudent, Integer> getEdges(UniversityStudent student) {
        Map<UniversityStudent, Integer> r = new HashMap<UniversityStudent, Integer>();
        List<StudentGraphEdge> edges = graph.get(student);
        for (StudentGraphEdge edge : edges) {
            r.put(edge.getStudent(), edge.getWeight());
        }
        return r;
    }

    /**
     * Add an Edge to a UniversityStudent
     * @param start UniversityStudent
     * @param end UniversityStudent
     * @param weight int
     */
    public void addEdge(UniversityStudent start, UniversityStudent end, int weight) {
        graph.get(start).add(new StudentGraphEdge(end, weight));
    }

    /**
     * Add a student to the Graph
     * @param student UniversityStudent
     */
    public void addStudent(UniversityStudent student) {
        graph.put(student, new LinkedList<>());
    }

    /**
     * Reference for a StudentGraphEdge
     */
    private class StudentGraphEdge {
        /**
         * The cost to travel to Edge
         */
        private int weight;

        /**
         * Destination of Edge
         */
        private UniversityStudent student;

        /**
         * Construct a StudentGraphEdge
         * @param student UniversityStudent
         * @param weight int
         */
        public StudentGraphEdge(UniversityStudent student, int weight) {
            this.weight = weight;
            this.student = student;
        }

        /**
         * Getter for StudentGraphEdge weight
         * @return int
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Getter for StudentGraphEdge student
         * @return UniversityStudent
         */
        public UniversityStudent getStudent() {
            return student;
        }
    }
}
