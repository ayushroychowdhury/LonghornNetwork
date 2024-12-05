import java.util.*;

/**
 * Handles the formation of student pods based on a given student graph.
 */
public class PodFormation {
    private StudentGraph graph;

    /**
     * Constructs a PodFormation instance with the specified student graph.
     *
     * @param graph The graph representing students and their connections.
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Forms pods of the specified size based on the student graph.
     *
     * @param podSize The maximum size of each pod.
     */
    public void formPods(int podSize) {
        Set<UniversityStudent> visited = new HashSet<>();
        int podID = 0;

        List<UniversityStudent> students = new ArrayList<>(graph.getAllNodes());

        for (UniversityStudent student : students) {
            if (!visited.contains(student)) {
                List<StudentGraph.Edge> mstEdges = buildMST(student, visited);
                List<List<UniversityStudent>> pods = partitionMST(student, mstEdges, podSize);

                for (List<UniversityStudent> pod : pods) {
                    for (UniversityStudent s : pod) {
                        s.setPodID(podID);
                    }
                    System.out.println("Formed Pod " + podID + " with students: " + pod);
                    podID++;
                }
            }
        }

        // Output pods.
        Map<Integer, List<UniversityStudent>> podMap = new HashMap<>();
        for (UniversityStudent student : graph.getAllNodes()) {
            int id = student.getPodID();
            podMap.computeIfAbsent(id, k -> new ArrayList<>()).add(student);
        }

        System.out.println("Pod Assignments:");
        for (Map.Entry<Integer, List<UniversityStudent>> entry : podMap.entrySet()) {
            System.out.print("  Pod " + entry.getKey() + ": ");
            List<UniversityStudent> podMembers = entry.getValue();
            for (int i = 0; i < podMembers.size(); i++) {
                System.out.print(podMembers.get(i).getName());
                if (i < podMembers.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        // Assign unvisited students to their own pods
        for (UniversityStudent student : graph.getAllNodes()) {
            if (student.getPodID() == -1) {
                student.setPodID(podID);
                System.out.println("Assigned " + student.getName() + " to Pod " + podID);
                podID++;
            }
        }
    }

    private List<StudentGraph.Edge> buildMST(UniversityStudent start, Set<UniversityStudent> visited) {
        List<StudentGraph.Edge> mstEdges = new ArrayList<>();
        Set<UniversityStudent> inMST = new HashSet<>();
        PriorityQueue<StudentGraph.Edge> edgeQueue = new PriorityQueue<>(
                (e1, e2) -> Integer.compare(e2.getWeight(), e1.getWeight())); // Max heap based on edge weight

        visited.add(start);
        inMST.add(start);
        edgeQueue.addAll(graph.getEdgesFrom(start));

        while (!edgeQueue.isEmpty()) {
            StudentGraph.Edge edge = edgeQueue.poll();
            UniversityStudent target = edge.getTarget();
            if (!inMST.contains(target)) {
                inMST.add(target);
                visited.add(target);
                mstEdges.add(edge);
                System.out.println("Added edge to MST: " + edge.getSource().getName() + " - " + target.getName() + " (weight: " + edge.getWeight() + ")");
                for (StudentGraph.Edge nextEdge : graph.getEdgesFrom(target)) {
                    if (!inMST.contains(nextEdge.getTarget())) {
                        edgeQueue.add(nextEdge);
                    }
                }
            }
        }

        return mstEdges;
    }

    private List<List<UniversityStudent>> partitionMST(UniversityStudent start, List<StudentGraph.Edge> mstEdges, int podSize) {
        Map<UniversityStudent, List<UniversityStudent>> adjacency = new HashMap<>();
        for (StudentGraph.Edge edge : mstEdges) {
            adjacency.computeIfAbsent(edge.getSource(), k -> new ArrayList<>()).add(edge.getTarget());
            adjacency.computeIfAbsent(edge.getTarget(), k -> new ArrayList<>()).add(edge.getSource());
        }

        List<List<UniversityStudent>> pods = new ArrayList<>();
        Set<UniversityStudent> visited = new HashSet<>();

        for (UniversityStudent student : adjacency.keySet()) {
            if (!visited.contains(student)) {
                List<UniversityStudent> pod = new ArrayList<>();
                Queue<UniversityStudent> queue = new LinkedList<>();
                queue.add(student);
                visited.add(student);

                while (!queue.isEmpty() && pod.size() < podSize) {
                    UniversityStudent current = queue.poll();
                    pod.add(current);
                    for (UniversityStudent neighbor : adjacency.getOrDefault(current, new ArrayList<>())) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }

                pods.add(pod);
            }
        }

        return pods;
    }
}