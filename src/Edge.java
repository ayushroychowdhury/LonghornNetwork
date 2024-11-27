public class Edge {
    UniversityStudent student;
    int weight;

    /**
     * Constructor for Edge
     * @param student
     * @param weight
     */
    public Edge(UniversityStudent student, int weight) {
        // Constructor
        this.student = student;
        this.weight = weight;
    }

    /**
     * returns head
     * @return
     */
    public UniversityStudent getStudent() {
        return student;
    }

    /**
     * returns weight
     * @return
     */
    public int getWeight() {
        return weight;
    }
}
