import java.util.*;

public class StudentGraph {
    private Map<UniversityStudent, List<UniversityStudent>> relationshipMap;

    public StudentGraph (List<UniversityStudent> students) {
        relationshipMap = new HashMap<>();
        for (UniversityStudent student: students) {
            relationshipMap.put(student, new ArrayList<>());
        }
    }

    public void addConnection (UniversityStudent student1, UniversityStudent student2) {
        relationshipMap.get(student1).add(student2);
        relationshipMap.get(student1).add(student2);
    }

    public void removeConnection (UniversityStudent student1, UniversityStudent student2) {
        relationshipMap.get(student1).remove(student2);
        relationshipMap.get(student2).remove(student1);
    }

    public List<UniversityStudent> getConnections(UniversityStudent student) {
        return relationshipMap.get(student);
    }

    public boolean hasConnection(UniversityStudent student1, UniversityStudent student2) {
        return relationshipMap.get(student1).contains(student2);
    }

    public List<UniversityStudent> bfs
}

