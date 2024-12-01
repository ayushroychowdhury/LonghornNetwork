/**
 * Class that represents an edge between two students in the graph.
 */
public class StudentEdge {
    private Student sourceStudent;
    private Student targetStudent;
    private int weight;

    /**
     * Constructs a StudentEdge object with the given student and weight
     * @param sourceStudent the student at the start of the edge
     * @param targetStudent the student at the other end of the edge
     * @param weight the weight of the edge
     */
    public StudentEdge(Student sourceStudent, Student targetStudent, int weight) {
        this.sourceStudent = sourceStudent;
        this.targetStudent = targetStudent;
        this.weight = weight;
    }

    /**
     * Gets the student at the other end of the edge
     * @return the student at the other end of the edge
     */
    public Student getTargetStudent() {
        return targetStudent;
    }

    /**
     * Gets the weight of the edge
     * @return the weight of the edge
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the source student of the edge
     * @return the source student of the edge
     */
    public Student getSourceStudent() {
        return sourceStudent;
    }

    /**
     * Inverts the weight of the edge to a value between 0 and 10
     */
    public void invertWeight() {
        weight = 10 - weight;
        if (weight < 0) {
            weight = 0;
        }
    }
}