/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

/**
 * Edge class that represents a connection between two students in the graph.
 */
public class Edge {
    // The student at the end of the connection
    private UniversityStudent student;
    // The strength of the connection
    private int connectionStrength;
    // The source student
    private UniversityStudent source;

    /**
     * Constructor for the Edge class.
     * @param connection
     * @param node
     */
    Edge(UniversityStudent connection, UniversityStudent node) {
        this.student = connection;
        this.connectionStrength = node.calculateConnectionStrength(connection);
        this.source = node;
    }

    /**
     * Get student for the Edge class
     */
    public UniversityStudent getStudent() {
        return student;
    }

    /**
     * Get source student for the Edge class
     */
    public UniversityStudent getSource() {
        return source;
    }

    /**
     * Get student for the Edge class
     */
    public int getConnectionStrength() {
        return connectionStrength;
    }
    
}