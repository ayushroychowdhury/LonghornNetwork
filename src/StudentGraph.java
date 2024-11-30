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
     * Constructor for a StudentGraph, which represents the connections between students
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

    }

    /**
     * Gets all the students in the StudentGraph
     * @return a list of all the students in the StudentGraph
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
}