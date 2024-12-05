/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * DijkstraNode class that represents a node in the graph for Dijkstra's algorithm.
 */
public class DijkstraNode {
    // Index of this student in the list of students
    private int studentIndex;
    // Distance from the start node
    private int distance;
    // Index of the parent node
    private int parentIndex;
    // List of edges
    private LinkedList<DijkstraNode> edges = new LinkedList<DijkstraNode>();
    // Whether the node has been visited
    private boolean visited = false;

    /**
     * Constructor for DijkstraNode class.
     * @param studentIndex
     */
    DijkstraNode(int studentIndex) {
        // Set student index
        this.studentIndex = studentIndex;
        // Set distance to -1 (unvisited)
        this.distance = -1;
    }

    /**
     * Connects this node to all other nodes in the graph. (CALL AFTER ALL NODES HAVE BEEN CREATED)
     * @param nodes
     * @param students
     */
    public void connectNodes(ArrayList<DijkstraNode> nodes, ArrayList<UniversityStudent> students) {
        // Add all nodes to edges except for this node
        for (DijkstraNode node : nodes) {
            if (! node.equals(this)) {
                edges.add(node);
            }
        }
        //System.out.println("Connected nodes for " + students.get(studentIndex).getName());
    }

    /**
     * Getters and setters for DijkstraNode class.
     */
    public int getStudentIndex() {
        return studentIndex;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public LinkedList<DijkstraNode> getEdges() {
        return edges;
    }

    /**
     * Visits the node.
     */
    public void visit() {
        visited = true;
    }

    /**
     * Checks if the node has been visited.
     * @return Whether the node has been visited
     */
    public boolean isVisited() {
        return visited;
    }


}
