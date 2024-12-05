import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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

    // public StudentPair clone() {
    //     // reference to student remains
    //     StudentPair sp = new StudentPair(student, weight);
    //     return sp;
    // }
}

/**
 * Adjacency list representation of connections between students
 */
public class StudentGraph {
    private Map<String, UniversityStudent> nameStudentMap;
    private Map<String, List<StudentPair>> adjacencyList;
    /**
     * Constructor
     * @param students list of UniversityStudent objects
     */
    public StudentGraph(List<UniversityStudent> students, Map<String, UniversityStudent> nameStudentMap) {
        this.nameStudentMap = nameStudentMap;
        adjacencyList = new HashMap<>();

        for (UniversityStudent student : students) {
            List<StudentPair> edges = new ArrayList<>();
            
            for (UniversityStudent otherStudent : students) {
                if (!student.name.equals(otherStudent.name)) {
                    int weight = student.calculateConnectionStrength(otherStudent);
                    if (weight > 0) {
                        edges.add(new StudentPair(otherStudent, weight));
                    }
                }
            }

            adjacencyList.put(student.name, edges);
        }
    }

    public StudentGraph() {}

    public void addEdge(UniversityStudent one, UniversityStudent two) {
        int strength = one.calculateConnectionStrength(two);

        if (!adjacencyList.containsKey(one.name)) {
            adjacencyList.put(one.name, new ArrayList<StudentPair>());
            nameStudentMap.put(one.name, one);
        }
        if (!adjacencyList.containsKey(two.name)) {
            adjacencyList.put(one.name, new ArrayList<StudentPair>());
            nameStudentMap.put(two.name, two);
        }

        adjacencyList.get(one.name).add(new StudentPair(two, strength));
        adjacencyList.get(two.name).add(new StudentPair(one, strength));
    }

    public List<StudentPair> getNeighbors(Student node) {
        if (adjacencyList.containsKey(node.name)) {
            return adjacencyList.get(node.name);
        }
        else {
            System.out.println("Student doesn't exist in graph.");
            return null;
        }
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public Map<String, List<StudentPair>> getAllNodes() {
        return adjacencyList;
    }

    // public StudentGraph clone() {
    //     StudentGraph st = new StudentGraph();
    //     st.nameStudentMap = new HashMap<>();
    //     st.adjacencyList = new HashMap<>();
    //     for (Map.Entry<String, UniversityStudent> en : nameStudentMap.entrySet()) {
    //         st.nameStudentMap.put(en.getKey(), en.getValue());
    //     }

    //     for (Map.Entry<String, List<StudentPair>> en : adjacencyList.entrySet()) {
    //         List<StudentPair> l = new ArrayList<>();
    //         for (StudentPair sp : en.getValue()) {
    //             l.add(sp.clone());
    //         }
    //         st.adjacencyList.put(en.getKey(), l);
    //     }

    //     return st;
    // }

    public String toString() {
        String graph = "";
        for (Map.Entry<String, List<StudentPair>> node : adjacencyList.entrySet()) {
            graph += "[" + node.getKey() + "]";
            for (StudentPair sp : node.getValue()) {
                graph += " -> [" + sp.getStudent().name + ", " + sp.getWeight() + "]";
            }
            graph += "\n";
        }

        return graph;
    }
}
