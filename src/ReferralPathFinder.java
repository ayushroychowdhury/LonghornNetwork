import java.util.*;


/**
 * This is the class/function that handles the algorithm for attempting to get an internship
 * through who you know. It should use djkstra's algorithm?  (will have to look into it more)
 * to determine the path for this
 */
public class ReferralPathFinder {
    StudentGraph graph;
    /**
     * construction which will basically just set the correct graph for the class based on the input graph
     * @param graph
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor
        this.graph = graph;
    }
    /**
     * The function that will be doing the bulk of the work. It takes a starting student
     * and attempts to trace a path to get to a specific company they are hoping to intern at
     * @param start The starting student from which we will execute the algo
     * @param targetCompany The ending company that serves as the goal
     * @return The list of students that we must take to reach our target company
     */
    public ArrayList<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Method signature only
        ArrayList<UniversityStudent> finArray = new ArrayList<UniversityStudent>();
        Node startNode = graph.stuToNode.get(start.name);
        graph.resetGraph();
        startNode.setValue(0);
        Heap<Node> heap = new Heap<Node>();
        heap.buildHeap(graph.adjList);

        while (heap.getSize() > 0){
            Node u = heap.extractMin();
            if (u.getValue() == Integer.MAX_VALUE){
                break;
            }
            if (checkCompanies(u, targetCompany)){
                constructPath(u, finArray);
                
                return finArray;
            }

            int count = 0;
            for (UniversityStudent student : u.adjList){
                Node v = graph.stuToNode.get(student.name);
                if (heap.contains(v) && 1000 - u.weights.get(count) < v.getValue()){
                    heap.changeKey(v, 1000 - u.weights.get(count));
                    v.currMinEdge = u;
                }
                ++count;
            }
        }

        return new ArrayList<>();
    }


    private boolean checkCompanies(Node node, String targetCompany){
        if (node.getValue() == Integer.MAX_VALUE){
            return false;
        }

        for (String company : node.stu.previousInternships){
            if (company.equals(targetCompany)){
                return true;
            }
        }
        return false;
    }

    private void constructPath(Node endNode, ArrayList<UniversityStudent> finArray){
        Node currNode = endNode;
        Node backNode = endNode.currMinEdge;
        while (backNode != null){
            backNode.forwardEdge = currNode;
            backNode = backNode.currMinEdge;
            currNode = currNode.currMinEdge;
        }

        while (currNode != null){
            finArray.add(currNode.stu);
            currNode = currNode.forwardEdge;
        }
    }
}
