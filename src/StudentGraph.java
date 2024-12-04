import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Container to store an edge node
 * Contains a student and the weight of the connection 
 */
class StudentPair {
    private UniversityStudent student;
    private int weight;

    public StudentPair(UniversityStudent student, int weight) {
        this.student = student;
        this.weight = weight;
    }

    public UniversityStudent getStudent() {
        return student;
    }
    public void setStudent(UniversityStudent student) {
        this.student = student;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
}

/**
 * Adjacency list representation of connections between students
 */
public class StudentGraph {
    private Map<String, UniversityStudent> nameStudentMap;
    private Map<UniversityStudent, List<StudentPair>> adjacencyList;
    /**
     * Constructor
     * @param students list of UniversityStudent objects
     */
    public StudentGraph(List<UniversityStudent> students) {
        nameStudentMap = new HashMap<>();
        adjacencyList = new HashMap<>();

        for (UniversityStudent student : students) {
            nameStudentMap.put(student.name, student);
        }

        for (UniversityStudent student : students) {
            List<StudentPair> edges = new ArrayList<>();
            for (String roommate : student.roommatePreferences) {
                UniversityStudent rm = nameStudentMap.get(roommate);
                edges.add(new StudentPair(rm, student.calculateConnectionStrength(rm)));
            }

            adjacencyList.put(student, edges);
        }
    }

    public void addEdge(UniversityStudent one, UniversityStudent two) {
        int strength = one.calculateConnectionStrength(two);

        if (!adjacencyList.containsKey(one)) {
            adjacencyList.put(one, new ArrayList<StudentPair>());
            nameStudentMap.put(one.name, one);
        }
        if (!adjacencyList.containsKey(two)) {
            adjacencyList.put(one, new ArrayList<StudentPair>());
            nameStudentMap.put(two.name, two);
        }

        adjacencyList.get(one).add(new StudentPair(two, strength));
        adjacencyList.get(two).add(new StudentPair(one, strength));
    }

    public List<StudentPair> getNeighbors(Student node) {
        if (adjacencyList.containsKey(node)) {
            return adjacencyList.get(node);
        }
        else {
            System.out.println("Student doesn't exist in graph.");
            return null;
        }
    }

    public Map<UniversityStudent, List<StudentPair>> getAllNodes() {
        return adjacencyList;
    }
}
