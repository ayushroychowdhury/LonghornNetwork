package program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A student graph is a graph representing the connections between students based on their connection strength
 */
public class StudentGraph {

    private Map<UniversityStudent, List<StudentEdge>> adjacencyList;

    /**
     * Constructor for a program.StudentGraph, which represents the connections between students
     * @param students
     */
    public StudentGraph(List<UniversityStudent> students) {
        /* Initialize map of lists */
        adjacencyList = new HashMap<UniversityStudent, List<StudentEdge>>();
        for (UniversityStudent student : students) {
            adjacencyList.put(student, new ArrayList<StudentEdge>());
        }

        /* Populate adjacency list */
        for (UniversityStudent student : students) {
            for (UniversityStudent other : students) {
                if (!student.equals(other)) {
                    int connectionStrength = student.calculateConnectionStrength(other);
                    if (connectionStrength > 0) {
                        adjacencyList.get(student).add(new StudentEdge(student, other, connectionStrength));
                    }
                }
            }
        }

        printAdjacencyList();
        System.out.println();
    }

    /**
     * Gets all the students in the program.StudentGraph
     * @return a list of all the students in the program.StudentGraph
     */
    public List<UniversityStudent> getStudents() {
        return List.of(adjacencyList.keySet().toArray(new UniversityStudent[0]));
    }

    /**
     * Returns all adjacent students to a given student
     * @param student the student whose adjacent students are to be found
     * @return a list of all adjacent students to the given student
     */
    public List<UniversityStudent> getAdjacentStudents(UniversityStudent student) {
        List<UniversityStudent> adjacentStudents = new ArrayList<UniversityStudent>();
        for (StudentEdge edge : adjacencyList.get(student)) {
            adjacentStudents.add((UniversityStudent) edge.getTargetStudent());
        }
        return adjacentStudents;
    }

    /**
     * Returns the edges of a given student
     * @param student the student whose edges are to be found
     * @return a list of edges of the given student
     */
    public List<StudentEdge> getEdges(UniversityStudent student) {
        return adjacencyList.get(student);
    }

    public List<StudentEdge> getNonCyclicEdges(List<Student> mstNodes){
        List<StudentEdge> nonCyclicEdges = new ArrayList<StudentEdge>();
        for(Student student : mstNodes){
            for(StudentEdge edge : adjacencyList.get(student)){
                if(!mstNodes.contains(edge.getTargetStudent())){
                    nonCyclicEdges.add(edge);
                }
            }
        }
        return nonCyclicEdges;
    }

    /**
     * Divides a graph into groups with a maximum size. The groups are connected in the graph.
     * @param nodes Nodes of the graph
     * @param edges Edges of the graph
     * @param maxSize Maximum size of the groups
     * @return List of groups of nodes
     */
    public static List<List<Student>> divideGraph(List<Student> nodes, List<StudentEdge> edges, int maxSize){
        List<List<Student>> groups = new ArrayList<List<Student>>();
        if (maxSize <= 0){
            return groups;
        }
        List<Student> visited = new ArrayList<Student>();
        for(Student node : nodes){
            if(!visited.contains(node)){
                List<Student> group = new ArrayList<Student>();
                group.add(node);
                visited.add(node);
                divideGraphHelper(node, edges, visited, group, maxSize);
                groups.add(group);
            }
        }
        return groups;
    }

    /**
     * Recursive method that adds connected nodes to a group in a graph
     * @param node Node from which to continue adding connected nodes
     * @param edges Edges of the graph
     * @param visited List of visited nodes
     * @param group current group of nodes
     * @param maxSize Maximum size of the groups
     */
    private static void divideGraphHelper(Student node, List<StudentEdge> edges, List<Student> visited, List<Student> group, int maxSize){
        for(StudentEdge edge : edges){
            if(edge.getSourceStudent().equals(node) && !visited.contains(edge.getTargetStudent())){
                if (group.size() >= maxSize){
                    return;
                }
                visited.add(edge.getTargetStudent());
                group.add(edge.getTargetStudent());
                if(group.size() < maxSize){
                    divideGraphHelper(edge.getTargetStudent(), edges, visited, group, maxSize);
                }
            }
        }
    }


    /**
     * Inverts the weights of all edges in the graph to a value between 0 and 10
     */
    public void invertWeights(){
        for(UniversityStudent student : adjacencyList.keySet()){
            for(StudentEdge edge : adjacencyList.get(student)){
                edge.invertWeight();
            }
        }
    }

    /**
     * Gets the weight of the edge between two students
     * @param current the current student
     * @param neighbor the neighboring student
     * @return the weight of the edge between the two students
     */
    public Integer getEdgeWeight(UniversityStudent current, UniversityStudent neighbor) {
        for (StudentEdge edge : adjacencyList.get(current)) {
            if (edge.getTargetStudent().equals(neighbor)) {
                return edge.getWeight();
            }
        }
        return 0;
    }

    /**
     * Gets the adjacency list of the graph
     * @return the adjacency list of the graph
     */
    public Map<UniversityStudent, List<StudentEdge>> getAdjacencyList(){
        return adjacencyList;
    }


    /**
     * Prints the adjacency list of the graph
     */
    private void printAdjacencyList(){
        for(UniversityStudent student : adjacencyList.keySet()){
            System.out.print(student.getName() + " -> ");
            String edgeString = "";
            for(StudentEdge edge : adjacencyList.get(student)){
                edgeString += edge.getTargetStudent().getName() + "(" + edge.getWeight() + "), ";
            }
            if(edgeString.length() > 1){
                edgeString = edgeString.substring(0, edgeString.length()-2);
            }
            System.out.print(edgeString);
            System.out.println();
        }
    }
}