import java.util.*;

public class StudentGraph {

    private Map<UniversityStudent, List<Edge>> adjacencyList;
    private List<UniversityStudent> students;

    /**
     * Constructor for StudentGraph
     */
    public StudentGraph(List<UniversityStudent> students) {
        this.students = students;
        this.adjacencyList = new HashMap<>();
        for (UniversityStudent student : students) {
            adjacencyList.put(student, new ArrayList<>());
        }
    }

    /** Adds edge between 2 students with a specified weight */
    public void addEdge(UniversityStudent student1, UniversityStudent student2, int weight) {
        adjacencyList.get(student1).add(new Edge(student1, student2, weight));
        adjacencyList.get(student2).add(new Edge(student2, student1, weight)); // For undirected graph
    }

    /**
     * Returns a list of edges connected to a student
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjacencyList.getOrDefault(student, new ArrayList<>());
    }

    /**
     * Return all students in graph
     */
    public List<UniversityStudent> getAllNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Builds a graph by calculating connection strengths between students
     */
    public static StudentGraph buildStudentGraph(List<UniversityStudent> students) {
        StudentGraph graph = new StudentGraph(students);

        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) { // Avoid duplicate edges
                UniversityStudent student1 = students.get(i);
                UniversityStudent student2 = students.get(j);

                int connectionStrength = student1.calculateConnectionStrength(student2);

                if (connectionStrength > 0) {
                    graph.addEdge(student1, student2, connectionStrength);

                }
            }
        }
        //graph.printGraph();
        return graph;
    }

    public void printGraph() {
        System.out.println("Student Graph:");
        for (Map.Entry<UniversityStudent, List<Edge>> entry : adjacencyList.entrySet()) {
            UniversityStudent student = entry.getKey();
            List<Edge> edges = entry.getValue();

            System.out.print(student.getName() + " -> ");
            if (edges.isEmpty()) {
                System.out.println("No connections");
            } else {
                for (Edge edge : edges) {
                    System.out.print("(" + edge.getTarget().getName() + ", weight: " + edge.getWeight() + ") ");
                }
                System.out.println();
            }
        }
    }

    public UniversityStudent getStudent(String name) {
        for (UniversityStudent student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }


    /**
     * Helper class to represent an edge
     */
    public static class Edge {
        private UniversityStudent source;
        private UniversityStudent target;
        private int weight;

        public Edge(UniversityStudent source, UniversityStudent target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }


        public UniversityStudent getTarget() {
            return target;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "source=" + source.getName() +
                    ", target=" + target.getName() +
                    ", weight=" + weight +
                    '}';
        }
    }

}
