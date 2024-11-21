import java.util.*;

/**
 * A representation of the Students' connections in a graph
 */
public class StudentGraph {
    // ex. Alice = {(Bob, 2), (Charlie, 4)}
    private HashMap<String, List<List<Object>>> adjList;

    /**
     * Constructor for the StudentGraph class.
     * @param students The list of students to initialize into the graph. Does not establish connections.
     */
    public StudentGraph(List<UniversityStudent> students) {
        // TODO
        adjList = new HashMap<>();
        for (UniversityStudent s : students) {
            adjList.put(s.name, new ArrayList<>());
        }
    }

    /**
     * Adds a weighted edge between two Students
     * @param one Student 1
     * @param two Student 2
     * @param weight The connection strength between the two students
     */
    public void addEdge(String one, String two, int weight) {
        // TODO
        if (!adjList.containsKey(one)) {
            adjList.put(one, new ArrayList<>());
        }
        ArrayList<Object> nameWeightPair = new ArrayList<>();
        nameWeightPair.add(two);
        nameWeightPair.add(weight);
        adjList.get(one).add(nameWeightPair);

        if (!adjList.containsKey(two)) {
            adjList.put(two, new ArrayList<>());
        }
        ArrayList<Object> nameWeightPair2 = new ArrayList<>();
        nameWeightPair2.add(one);
        nameWeightPair2.add(weight);
        adjList.get(two).add(nameWeightPair2);
    }

    /**
     * Gets a list of pairs of neighboring students and their connecting weights
     * @param s the student to find neighbor-weight pairs of
     * @return A list of pairs of names and weights
     */
    public List<List<Object>> getNeighborsWeighted(String s) {
        return adjList.get(s);
    }

    /**
     * Gets the neighbors of a given student
     * @param s the student to find neighbors of
     * @return A list of names of students who are connected to the input student
     */
    public List<String> getNeighbors(String s) {
        ArrayList<String> neighbors = new ArrayList<>();
        for (List<Object> pair : adjList.get(s)) {
            neighbors.add((String)pair.get(0));
        }
        return neighbors;
    }

    /**
     * Gets a list of the names of all the students who are in the graph
     * @return A list of the names of the students in the graph
     */
    public List<String> getAllNodes() {
        return new ArrayList<String>(adjList.keySet());
    }


    public HashMap<String, List<List<Object>>> getAdjList() {
        return adjList;
    }
}
