import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Container to store an edge node
 * Contains a student and the weight of the connection 
 */
class StudentPair {
    public Student student;
    public int weight;
}

/**
 * Adjacency list representation of connections between students
 */
public class StudentGraph {
    private Map<UniversityStudent, List<StudentPair>> adjacencyList;
    /**
     * Constructor
     * @param students list of UniversityStudent objects
     */
    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();
        for (UniversityStudent student : students) {
            List<StudentPair> list = new ArrayList<>();


            adjacencyList.put(student, list);
        }
    }
}
