import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Class to create minimum spanning trees in a network of students
 */
public class PodFormation {
    /**
     * Constructor
     * @param graph
     */
    private Map<String, UniversityStudent> nameStudentMap;
    private StudentGraph graph;
    public PodFormation(StudentGraph graph, Map<String, UniversityStudent> nameStudentMap) {
        this.graph = graph;
        this.nameStudentMap = nameStudentMap;
    }

    /**
     * Runs Prim's algorithm to create minimum spanning trees of students
     * @param podSize
     */
    public List<List<String>> formPods(int podSize) {
        // initialization
        List<String> students = new ArrayList<String>(graph.getNodes());
        Map<String, String> parent = new HashMap<String, String>();
        Map<String, Integer> dist = new HashMap<String, Integer>();
        Map<String, Boolean> inMST = new HashMap<String, Boolean>();
        for (String student : students) {
            parent.put(student, null);
            dist.put(student, Integer.MAX_VALUE);
            inMST.put(student, false);
        }
        dist.put(students.get(0), 0);
        // heap initialization
        PriorityQueue<String> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(dist.get(a), dist.get(b))
        );
        for (String student : students) {
            pq.add(student);
        }
        
        // Prim's
        while (!pq.isEmpty()) {
            String stud = pq.remove();
            // In the case of a disconnected graph, create new instance of Prim's by setting this node's dist to 0
            if (dist.get(stud) == Integer.MAX_VALUE)
                dist.put(stud, 0);
            inMST.put(stud, true);

            List<StudentPair> adjacentNodes = graph.getNeighbors(nameStudentMap.get(stud));
            for (StudentPair otherStud : adjacentNodes) {
                String otherStudName = otherStud.getStudent().name;
                if (!inMST.get(otherStudName)) {
                    if (otherStud.getWeight() < dist.get(otherStudName)) {
                        parent.put(otherStudName, stud);
                        pq.remove(otherStudName); // O(n)
                        dist.put(otherStudName, otherStud.getWeight());
                        pq.add(otherStudName);
                    }
                }
            }
        }
        
        // find root nodes
        Map<String, Integer> numInputNodes = new HashMap<>();
        for (String student : students) {
            numInputNodes.put(student, 0);
        }

        // create graph of MST
        Map<String, List<String>> adjList = new HashMap<>();
        for (Map.Entry<String, String> node : parent.entrySet()) {
            adjList.putIfAbsent(node.getKey(), new ArrayList<>());
            if (node.getValue() != null)
                numInputNodes.put(node.getKey(), numInputNodes.get(node.getKey()) + 1);
            if (node.getValue() != null) {
                adjList.putIfAbsent(node.getValue(), new ArrayList<>());
                adjList.get(node.getValue()).add(node.getKey());
            }
        }

        // Divide MSTs into subtrees with at most podSize nodes
        List<List<String>> subtrees = new ArrayList<>();
        Set<String> visitedByDFS = new HashSet<>();
        for (String stud : students) {
            if (numInputNodes.get(stud) == 0) {
                dfs(true, stud, podSize, adjList, visitedByDFS, subtrees, new ArrayList<>());
            }
        }

        return subtrees;
    }

    private void dfs(boolean firstIter, String currentNode, int podSize, Map<String, List<String>> adjList, Set<String> visited, List<List<String>> subtrees, List<String> currentSubtree) {
        // process current node
        visited.add(currentNode);
        currentSubtree.add(currentNode);

        // podSize reached
        if (currentSubtree.size() == podSize) {
            subtrees.add(new ArrayList<>(currentSubtree));
            currentSubtree.clear();
        }

        // dfs on all neighbors
        for (String neighbor : adjList.getOrDefault(currentNode, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(false, neighbor, podSize, adjList, visited, subtrees, currentSubtree);
            }
        }

        // isolated nodes and leaf nodes
        if (firstIter && (!currentSubtree.isEmpty())) {
            subtrees.add(new ArrayList<>(currentSubtree));
        }
    }
}