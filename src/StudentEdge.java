/**
 * Class that represents an edge between two students in the graph.
 */
public class StudentEdge {
    private Student student;
    private int weight;

    /**
     * Constructs a StudentEdge object with the given student and weight
     * @param student the student at the other end of the edge
     * @param weight the weight of the edge
     */
    public StudentEdge(Student student, int weight) {
        this.student = student;
        this.weight = weight;
    }

    /**
     * Gets the student at the other end of the edge
     * @return the student at the other end of the edge
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Gets the weight of the edge
     * @return the weight of the edge
     */
    public int getWeight() {
        return weight;
    }
}