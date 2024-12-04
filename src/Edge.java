public class Edge {
    UniversityStudent student;
    int weight;

    /**
     * Constructor for Edge
     * @param student destination student
     * @param weight weight of the edge
     */
    public Edge(UniversityStudent student, int weight) {
        // Constructor
        this.student = student;
        this.weight = weight;
    }

    /**
     * returns head
     * @return student
     */
    public UniversityStudent getStudent() {
        return student;
    }

    /**
     * returns weight
     * @return weight
     */
    public int getWeight() {
        return weight;
    }
}
