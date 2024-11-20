import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

class StudentPair {
    public Student student;
    public int weight;
}

public class StudentGraph {
    Map<UniversityStudent, List<StudentPair>> adjacencyList;
    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();
        for (UniversityStudent student : students) {
            List<StudentPair> list = new ArrayList<>();


            adjacencyList.put(student, list);
        }
    }
}
