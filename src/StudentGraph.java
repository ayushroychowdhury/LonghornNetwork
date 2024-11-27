import java.util.*;

public class StudentGraph {
    Map<UniversityStudent, List<Edge>> graph;
    /**
     * Constructs a StudentGraph object with the given list of students.
     * @param students
     */
    public StudentGraph(List<UniversityStudent> students) {
        // Constructor
        graph = new HashMap<UniversityStudent, List<Edge>>();
        for (UniversityStudent student : students) {
            List<Edge> edges = new ArrayList<>();
            for (UniversityStudent other : students) {
                if (!student.equals(other)) {
                    int weight = student.calculateConnectionStrength(other);
                    if (weight > 0) {
                        edges.add(new Edge(other, weight));
                    }
                }
            }
            graph.put(student, edges);
        }
    }

    /**
     * Returns the list of students in the graph.
     * @return
     */
    public List<UniversityStudent> getStudents() {
        // Returns the list of students in the graph
        return new ArrayList<>(graph.keySet());
    }

    /**
     * Returns the list of edges for the given student.
     * @param student
     * @return
     */
    public List<Edge> getEdges(UniversityStudent student) {
        // Returns the list of edges for the given student
        return graph.get(student);
    }

    public String toString() {
        // Returns a string representation of the graph
        StringBuilder sb = new StringBuilder();
        for (UniversityStudent student : graph.keySet()) {
            sb.append(student.getName() + " -> ");
            List<Edge> edges = graph.get(student);
            for (Edge edge : edges) {
                sb.append(edge.getStudent().getName() + "(" + edge.getWeight() + "), ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
