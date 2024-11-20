


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
}

