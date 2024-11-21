import java.util.*;

/**
 * Partitions students into pods
 */
public class PodFormation {

    // This line is my code
    private List<List<Student>> pods;
    private HashMap<String, List<List<Object>>> adjList;
    StudentGraph graph;

    /**
     * Pod formation constructor.
     * @param graph The student graph to make pods from.
     */
    public PodFormation(StudentGraph graph) {
        // Constructor
        this.graph = graph;
        adjList = graph.getAdjList();

        // Remove connections with connection strength of 0
        for (List<List<Object>> connections : adjList.values()) {
            for (int i = connections.size()-1 ; i >= 0 ; i--) {
                if ((Integer)connections.get(i).get(1) == 0) {
                    connections.remove(i);
                }
            }
        }
    }

    /**
     * Creates pods of students
     * @param podSize How large each pod should be
     */
    public List<List<String>> formPods(int podSize) {
        // Method signature only
        List<List<Edge>> MSTs = findMSTs();
        // Divide MSTs
        List<List<String>> pods = new ArrayList<>();

        for (int i = 0 ; i < MSTs.size() ; i++) {

            List<Edge> mstEdges = MSTs.get(i);

            // Step 1: Build adjacency list from edge list
            Map<String, List<String>> adjacencyList = new HashMap<>();
            for (Edge edge : mstEdges) {
                adjacencyList.computeIfAbsent(edge.from, k -> new ArrayList<>()).add(edge.to);
                adjacencyList.computeIfAbsent(edge.to, k -> new ArrayList<>()).add(edge.from);
            }

            // Step 2: Perform grouping
            List<List<String>> groups = new ArrayList<>();
            Set<String> visited = new HashSet<>();

            for (String node : adjacencyList.keySet()) {
                if (!visited.contains(node)) {
                    // Create a new group for this connected component
                    List<String> currentGroup = new ArrayList<>();
                    Queue<String> queue = new LinkedList<>();
                    queue.add(node);

                    while (!queue.isEmpty() && currentGroup.size() < podSize) {
                        String current = queue.poll();
                        if (visited.contains(current)) continue;

                        visited.add(current);
                        currentGroup.add(current);

                        for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                            if (!visited.contains(neighbor) && currentGroup.size() < podSize) {
                                queue.add(neighbor);
                            }
                        }
                    }

                    pods.add(currentGroup);
                }
            }

        }
        return pods;
    }





    // Helper class to represent edges
    static class Edge implements Comparable<Edge> {
        String from;
        String to;
        int weight;

        Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight) * -1;
        }
    }

    public List<List<Edge>> findMSTs() {
        // Track visited nodes to handle multiple connected components
        Set<String> visited = new HashSet<>();
        List<List<Edge>> MSTs = new ArrayList<>();

        for (String startNode : adjList.keySet()) {
            if (!visited.contains(startNode)) {
                // Compute MST for the current connected component
                // System.out.println("MST for connected component starting at: " + startNode);
                MSTs.add(findMST(startNode, visited));
                // System.out.println();
            }
        }

        return MSTs;
    }

    private List<Edge> findMST(String startNode, Set<String> visited) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Set<String> mstSet = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();

        // Add all edges from the start node to the priority queue
        addEdgesToPQ(startNode, pq, mstSet);

        // While there are edges in the priority queue
        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();

            // If the destination node is already in the MST, skip
            if (mstSet.contains(minEdge.to)) {
                continue;
            }

            // Add edge to the MST
            mstSet.add(minEdge.to);
            mstEdges.add(minEdge);

            // Mark the node as visited
            visited.add(minEdge.to);

            // Add new edges to the priority queue
            addEdgesToPQ(minEdge.to, pq, mstSet);
        }

        // Print the MST edges
//        for (Edge edge : mstEdges) {
//            System.out.println(edge.from + " -- " + edge.to + " [" + edge.weight + "]");
//        }

        return mstEdges;
    }

    private void addEdgesToPQ(String node, PriorityQueue<Edge> pq, Set<String> mstSet) {
        mstSet.add(node); // Add to MST set

        // Add all edges of the current node to the priority queue
        for (List<Object> neighborInfo : adjList.getOrDefault(node, new ArrayList<>())) {
            String neighbor = (String) neighborInfo.get(0);
            int weight = (int) neighborInfo.get(1);

            if (!mstSet.contains(neighbor)) {
                pq.add(new Edge(node, neighbor, weight));
            }
        }
    }
}
