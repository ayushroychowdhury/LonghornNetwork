import java.util.*;

/**
 * A graph representing the UniversityStudent's relationship with other UniversityStudents
 * and there are methods to add/delete relationships.
 * Also includes a method to perform BFS for specific targets.
 */
public class StudentGraph {
    private Map<UniversityStudent, List<UniversityStudent>> relationshipMap;

    /**
     * Constructs a StudentGraph from a list of university students.
     * Initializes an adjacency list for all students and creates connections
     * based on their calculated connection strengths.
     *
     * @param students the list of students to be included in the graph
     */
    public StudentGraph(List<UniversityStudent> students) {
        relationshipMap = new HashMap<>();
        for (UniversityStudent student : students) {
            relationshipMap.put(student, new ArrayList<>());
        }

        // Automatically build connections based on connection strengths
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                UniversityStudent student1 = students.get(i);
                UniversityStudent student2 = students.get(j);

                int connectionStrength = student1.calculateConnectionStrength(student2);

                // Add connections only if there is a meaningful relationship
                if (connectionStrength > 0) {
                    addConnection(student1, student2);
                }
            }
        }
    }

    /**
     * Adds a connection between two students.
     * If student1 has a connection with student2, then student2 should also have a connection with student1.
     * The connections are undirected.
     *
     * @param student1 the first student
     * @param student2 the second student
     */
    public void addConnection(UniversityStudent student1, UniversityStudent student2) {
        relationshipMap.get(student1).add(student2);
        relationshipMap.get(student2).add(student1);
    }

    /**
     * Removes a connection between two UniversityStudents.
     *
     * @param student1 the first student
     * @param student2 the second student
     */
    public void removeConnection(UniversityStudent student1, UniversityStudent student2) {
        relationshipMap.get(student1).remove(student2);
        relationshipMap.get(student2).remove(student1);
    }

    /**
     * Retrieves a list of all students directly connected to the specified student.
     *
     * @param student the student whose connections are to be retrieved
     * @return a list of students directly connected to the specified student
     */
    public List<UniversityStudent> getConnections(UniversityStudent student) {
        return relationshipMap.get(student);
    }

    /**
     * Checks whether a direct connection exists between two students.
     *
     * @param student1 the first student
     * @param student2 the second student
     * @return true if a connection exists, false otherwise
     */
    public boolean hasConnection(UniversityStudent student1, UniversityStudent student2) {
        return relationshipMap.get(student1).contains(student2);
    }

    /**
     * Retrieves all students (nodes) in the graph.
     *
     * @return a set of all students in the graph
     */
    public Set<UniversityStudent> getAllStudents() {
        return relationshipMap.keySet();
    }

    /**
     * Performs a BFS starting from a given student
     * to find a path to another student who has interned at the specified company.
     *
     * @param start   the student from which to start the search
     * @param company the target company for finding a referral path
     * @return a list of students representing the path from the start student
     *         to a student with the target company in their internships, or an
     *         empty list if no such path exists
     */
    public List<UniversityStudent> bfs(UniversityStudent start, String company) {
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

            for (UniversityStudent neighbor : relationshipMap.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * Builds the path from the start student to the end student based on the parent map.
     *
     * @param parentMap the map storing the parent of each visited student during BFS
     * @param start     the starting student
     * @param end       the ending student
     * @return a list of students representing the path from start to end
     */
    private List<UniversityStudent> buildPath(Map<UniversityStudent, UniversityStudent> parentMap,
                                              UniversityStudent start, UniversityStudent end) {
        List<UniversityStudent> path = new ArrayList<>();
        for (UniversityStudent at = end; at != null; at = parentMap.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Prints the relationships of the given student.
     *
     * @param student the student
     */
    public void printerGraph(UniversityStudent student) {
        if (!relationshipMap.containsKey(student)) {
            System.out.println("Student not found in the graph.");
            return;
        }

        List<UniversityStudent> connections = relationshipMap.get(student);
        System.out.println("Connections for " + student.name + ":");

        if (connections.isEmpty()) {
            System.out.println("  No connections.");
        } else {
            for (UniversityStudent connection : connections) {
                System.out.println("  - " + connection.name);
            }
        }
    }

    /**
     * Prints the entire graph structure.
     */
    public void printFullGraph() {
        for (UniversityStudent student : relationshipMap.keySet()) {
            System.out.print(student.name + " -> ");
            List<UniversityStudent> connections = relationshipMap.get(student);
            if (connections.isEmpty()) {
                System.out.println("No connections.");
            } else {
                for (UniversityStudent connection : connections) {
                    System.out.print(connection.name + ", ");
                }
                System.out.println();
            }
        }
    }
}
