import jdk.dynalink.linker.support.CompositeTypeBasedGuardingDynamicLinker;

import java.util.*;

/**
 * formation of pods of students based on the student graph.
 */
public class PodFormation {
    private StudentGraph graph;
    private Map<UniversityStudent, List<UniversityStudent>> pods;
    /**
     * Constructs a PodFormation object using the given graph.
     *
     * @param graph the graph representing relationships between students
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
        this.pods = new HashMap<>();
    }

    /**
     * Forms pods of the specified size.
     *
     * @param podSize size
     */
    public void formPods(int podSize) {
        Set<UniversityStudent> visited = new HashSet<>();
        List<UniversityStudent> allStudents = new ArrayList<>(graph.getAllStudents());

        //for all students
        for (UniversityStudent student : allStudents) {
            //graph.printerGraph(UniversityStudent student);
            if (!visited.contains(student)) {
                List<UniversityStudent> component = getConnectedComponent(student, visited);
                List<List<UniversityStudent>> componentPods = createPodsFromComponent(component, podSize);
                assignPods(componentPods);
            }
        }
    }



    /**
     * Get all students in a connected component starting from one student.
     *
     * @param start the starting student
     * @param visited the set of visited students
     * @return a list of students in connected component
     */
    private List<UniversityStudent> getConnectedComponent(UniversityStudent start, Set<UniversityStudent> visited) {
        List<UniversityStudent> component = new ArrayList<>();
        Queue<UniversityStudent> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            UniversityStudent current = queue.poll();
            component.add(current);
            for (UniversityStudent neighbor : graph.getConnections(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return component;
    }

    /**
     * Diides a connected component into pods using Prim's algorithm (this is pod formation)
     *
     * @param component the list of students in the connected component
     * @param podSize the maximum size of each pod
     * @return a list a pods
     */
    private List<List<UniversityStudent>> createPodsFromComponent(List<UniversityStudent> component, int podSize) {
        List<List<UniversityStudent>> pods = new ArrayList<>();
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        Set<UniversityStudent> inPod = new HashSet<>();

        //initialise
        UniversityStudent start = component.get(0);
        addEdgesToQueue(start, edgeQueue, inPod);

        List<UniversityStudent> currentPod = new ArrayList<>();
        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            if (inPod.contains(edge.student2)) continue;

            currentPod.add(edge.student2);
            inPod.add(edge.student2);

            //add edge
            addEdgesToQueue(edge.student2, edgeQueue, inPod);

            //start a new one if the current one is full
            if (currentPod.size() == podSize) {
                pods.add(new ArrayList<>(currentPod));
                currentPod.clear();
            }
        }

        //add the remaining students as a pod
        if (!currentPod.isEmpty()) {
            pods.add(currentPod);
        }

        return pods;
    }

    /**
     * Adds edges from the given student to theq ueue.
     *
     * @param student the student whose edges are being added
     * @param edgeQueue the priority queue
     * @param inPod the students that are already in the pod
     */
    private void addEdgesToQueue(UniversityStudent student, PriorityQueue<Edge> edgeQueue, Set<UniversityStudent> inPod) {
        for (UniversityStudent neighbor : graph.getConnections(student)) {
            if (!inPod.contains(neighbor)) {
                int weight = student.calculateConnectionStrength(neighbor);
                edgeQueue.add(new Edge(student, neighbor, weight));
            }
        }
    }

    /**
     * assign pods
     *
     * @param componentPods the list of pods to assign
     */
    private void assignPods(List<List<UniversityStudent>> componentPods) {
        for (List<UniversityStudent> pod : componentPods) {
            for (UniversityStudent student : pod) {
                pods.put(student, new ArrayList<>(pod));
            }
        }
    }

    /**
     * just an edge
     */
    public static class Edge {
        UniversityStudent student1, student2;
        int weight;

        Edge(UniversityStudent student1, UniversityStudent student2, int weight) {
            this.student1 = student1;
            this.student2 = student2;
            this.weight = weight;
        }
    }

    /**
     * get pods after they are foemed.
     *
     * @return student and their pod
     */

    public Map<UniversityStudent, List<UniversityStudent>> getPods() {
        return pods;
    }
}
