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
        relationshipMap.get(student2).add(student1);
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

    public List<UniversityStudent> bfs (UniversityStudent start, String company) {
        Queue<UniversityStudent> queue = new LinkedList<>();
        Set<UniversityStudent> visited = new HashSet<>();
        Map<UniversityStudent, UniversityStudent> parentMap = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            UniversityStudent current = queue.poll();
            
            if (current.previousInternships.contains(company)) {
                return buildPath(parentMap, start, current);
            }

            for (UniversityStudent neighbor: relationshipMap.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return new ArrayList<>();
    }

    private List<UniversityStudent> buildPath(Map<UniversityStudent, UniversityStudent> parentMap,
                                              UniversityStudent start, UniversityStudent end) {
                                                List <UniversityStudent> path = new ArrayList<>();
                                                for (UniversityStudent at = end; at != null; at = parentMap.get(at)) {
                                                    path.add(at);
                                                }
                                                Collections.reverse(path);
                                                return path;
                                              }
}

