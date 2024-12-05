
import java.util.*;

/**
 * com.studentgraph.model.PodFormation class uses Prim's algorithm to form pods (groups of students)
 * by minimizing connection strengths. It handles disconnected graphs by forming
 * separate pods for each connected component.
 */
public class PodFormation {

    private StudentGraph graph;
    private List<List<UniversityStudent>> pods;

    /**
     * Constructor forPodFormation.
     *
     * @param graph The StudentGraph representing student connections.
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
        this.pods = new ArrayList<>();
    }

    /**
     * Forms pods of students based on the specified pod size using Prim's algorithm.
     *
     * @param podSize The maximum number of students allowed in a pod.
     */
    public void formPods(int podSize) {
        // find sections
        List<Set<UniversityStudent>> connectedComponents = findConnectedComponents();

        // each section is separte
        for (Set<UniversityStudent> component : connectedComponents) {
            if (component.size() <= podSize) {
                // If unconnnceted part of graph is < podsize, form own pod
                pods.add(new ArrayList<>(component));
            } else {
                //use prims
                StudentGraph mst = buildMST(component);
                divideMSTIntoPods(mst, podSize);
            }
        }
        printPods();
    }

    /**
     * Finds all connected components in the graph using Depth-First Search (DFS).
     *
     * @return A list of sets, each representing a connected component.
     */
    private List<Set<UniversityStudent>> findConnectedComponents() {
        List<Set<UniversityStudent>> connectedComponents = new ArrayList<>();
        Set<UniversityStudent> visited = new HashSet<>();

        for (UniversityStudent student : graph.getAllNodes()) {
            if (!visited.contains(student)) {
                Set<UniversityStudent> component = new HashSet<>();
                dfs(student, visited, component);
                connectedComponents.add(component);
            }
        }

        return connectedComponents;
    }

    /**
     * Performs Depth-First Search (DFS) to find all students in section of graph.
     *
     * @param current   The current student being visited.
     * @param visited   Set of already visited students.
     * @param component Set to store students in the current connected component.
     */
    private void dfs(UniversityStudent current, Set<UniversityStudent> visited, Set<UniversityStudent> component) {
        visited.add(current);
        component.add(current);

        for (StudentGraph.Edge edge : graph.getNeighbors(current)) {
            UniversityStudent neighbor = edge.getTarget();
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, component);
            }
        }
    }

    /**
     * Builds the MST for a section using Prim's algorithm.
     *
     * @param component The set of students in the section.
     * @return A com.studentgraph.model.StudentGraph representing MST of the section.
     */
    private StudentGraph buildMST(Set<UniversityStudent> component) {
        StudentGraph mst = new StudentGraph(new ArrayList<>(component));

        // set up hashset to keep track of what's already included
        // and priority queue to select edges with minimum weight
        Set<UniversityStudent> inMST = new HashSet<>();
        PriorityQueue<EdgeWrapper> min = new PriorityQueue<>(Comparator.comparingInt(e -> e.edge.getWeight()));

        Iterator<UniversityStudent> iterator = component.iterator();
        if (!iterator.hasNext()) {
            return mst;
        }
        //starting student
        UniversityStudent start = iterator.next();
        inMST.add(start);


        for (StudentGraph.Edge edge : graph.getNeighbors(start)) {
            min.offer(new EdgeWrapper(edge, start));
        }

        // get edge with smallest weight from priority queue
        while (!min.isEmpty() && inMST.size() < component.size()) {
            EdgeWrapper wrapper = min.poll();
            StudentGraph.Edge edge = wrapper.edge;
            UniversityStudent target = edge.getTarget();

            //if edge target is not already in mst, add it to mst and mark as visited and add its neighbors to queue
            if (!inMST.contains(target)) {
                mst.addEdge(wrapper.source, target, edge.getWeight());
                inMST.add(target);

                for (StudentGraph.Edge nextEdge : graph.getNeighbors(target)) {
                    if (!inMST.contains(nextEdge.getTarget())) {
                        min.offer(new EdgeWrapper(nextEdge, target));
                    }
                }
            }
        }

        return mst;
    }

    /**
     * Helper class to wrap edges with their source for the priority queue.
     */
    private static class EdgeWrapper {
        StudentGraph.Edge edge;
        UniversityStudent source;

        public EdgeWrapper(StudentGraph.Edge edge, UniversityStudent source) {
            this.edge = edge;
            this.source = source;
        }
    }

    /**
     * Divides the MST into pods of specified size by performing a BFS traversal.
     *
     * @param mst     The Minimum Spanning Tree of the connected component.
     * @param podSize The maximum number of students allowed in a pod.
     */
    private void divideMSTIntoPods(StudentGraph mst, int podSize) {
        Set<UniversityStudent> visited = new HashSet<>();
        Queue<UniversityStudent> queue = new LinkedList<>();

        // bfs
        UniversityStudent start = mst.getAllNodes().get(0);
        queue.offer(start);
        visited.add(start);

        List<UniversityStudent> currentPod = new ArrayList<>();

        // get node from queue and add to current pod and add unvisited nighbors to queue
        while (!queue.isEmpty()) {
            UniversityStudent current = queue.poll();
            currentPod.add(current);

            // if current pod is at limit, store pod in pod list and start new pod
            if (currentPod.size() == podSize) {
                pods.add(new ArrayList<>(currentPod));
                currentPod.clear();
            }

            for (StudentGraph.Edge edge : mst.getNeighbors(current)) {
                UniversityStudent neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        if (!currentPod.isEmpty()) {
            pods.add(new ArrayList<>(currentPod));
        }
    }

    /**
     * Prints  pods
     */
    private void printPods() {
        System.out.println("Pod Assignment:");
        int podNumber = 1;
        for (List<UniversityStudent> pod : pods) {
            System.out.print("Pod " + podNumber + ": ");
            List<String> names = new ArrayList<>();
            for (UniversityStudent student : pod) {
                names.add(student.getName());
            }
            System.out.println(String.join(", ", names));
            podNumber++;
        }
    }
}