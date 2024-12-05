import java.util.*;

/**
 * Handles the formation of pods using Prim's algorithm.
 */
public class PodFormation {
    private final StudentGraph graph;
    private final List<List<UniversityStudent>> pods = new ArrayList<>();

    /**
     * Constructs a new PodFormation instance with the given student graph.
     *
     * @param graph the graph representing students and their connections.
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Forms pods with the specified size using Prim's algorithm.
     *
     * @param podSize the desired size of each pod.
     */
    public void formPods(int podSize) {
        Set<UniversityStudent> visited = new HashSet<>();
        for (UniversityStudent student : graph.getStudents()) {
            if (!visited.contains(student)) {
                List<UniversityStudent> pod = new ArrayList<>();
                Queue<UniversityStudent> queue = new LinkedList<>();
                queue.add(student);

                while (!queue.isEmpty() && pod.size() < podSize) {
                    UniversityStudent current = queue.poll();
                    if (!visited.contains(current)) {
                        visited.add(current);
                        pod.add(current);
                        queue.addAll(graph.getNeighbors(current).keySet());
                    }
                }
                pods.add(pod);
            }
        }

        // Print pod assignments
        System.out.println("Pod Assignments:");
        int podNumber = 1;
        for (List<UniversityStudent> pod : pods) {
            System.out.print("Pod " + podNumber + ": ");
            for (int i = 0; i < pod.size(); i++) {
                System.out.print(pod.get(i).name);
                if (i < pod.size() - 1) System.out.print(", ");
            }
            System.out.println();
            podNumber++;
        }
    }
}
