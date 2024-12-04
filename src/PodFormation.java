import java.util.*;


/**
 * This is the class for handling pod formations. It mainly just takes a graph as an input and 
 * alters that graph so that there are now correct pods in the graph.
 */
public class PodFormation {
    private StudentGraph graphRef;
    private ArrayList<ArrayList<Node>> graphs = new ArrayList<ArrayList<Node>>();
    /**
     * This is the constructor for the class. Will simply set the graph field to what is 
     * inputed
     */
    public PodFormation(StudentGraph graph) {
        // Constructor
        //StudentGraph safe = graph.deepCopy();
        graphRef = graph;
        constructGraphs(graph);
        for (int i = 0; i < graphs.size(); ++i){
            createMST(graphs.get(i), graph.stuToNode);
        }

    }


    private Node findStart(ArrayList<Node> adjList, HashSet<Node> bfsSet){
        for (int i = 0; i < adjList.size(); ++i){
            if (!bfsSet.contains(adjList.get(i))){
                return adjList.get(i);
            }
        }
        return null;
    }

    /**
     * This function splits the overall graph into multiple graphs that only contain students
     * that have some connection to one another some way.
     * @param graph
     */
    private void constructGraphs(StudentGraph graph){
        ArrayList<Node> adjList = graph.adjList;
        HashSet<Node> bfsSet = new HashSet<Node>();

        Node startNode = findStart(adjList,bfsSet);

        while (startNode != null){
            graphs.add(new ArrayList<Node>());
            Queue<Node> q = new LinkedList<>();
            q.add(startNode);
            bfsSet.add(startNode);
            while (q.size() > 0){
                Node currNode = q.remove();
                graphs.get(graphs.size() - 1).add(currNode);
                for (int i = 0; i < currNode.adjList.size(); ++i){
                    Node obtainedNode = graph.stuToNode.get(currNode.adjList.get(i).name);
                    if (!bfsSet.contains(obtainedNode)){
                        q.add(obtainedNode);
                        bfsSet.add(obtainedNode);
                    }
                }
            }
            startNode = findStart(adjList, bfsSet);
        }
    }

    private void createMST(ArrayList<Node> graph, HashMap<String, Node> stuMap){
        graph.get(0).setValue(0);
        Heap<Node> heap = new Heap<Node>();
        heap.buildHeap(graph);
        while (heap.getSize() > 0){
            Node u = heap.extractMin();
            if (u.currMinEdge != null){
                u.addMstAdj(u.currMinEdge.stu,u.getValue());
                u.currMinEdge.addMstAdj(u.stu, u.getValue());
            }
            int count = 0;
                for (UniversityStudent student : u.adjList){
                    Node v = stuMap.get(student.name);
                    if (heap.contains(v) && u.weights.get(count) < v.getValue()){
                        heap.changeKey(v, u.weights.get(count));
                        v.currMinEdge = u;
                    }
                    ++count;
                }
        }

    }

    /**
     * This is the function that will do the bulk of the work on forming the pods. Input the size
     * of pods that you are looking for and they will be created accordingly
     * @param podSize The size of the pods that are desired
     */
    public void formPods(int podSize) {
        LinkedList<UniversityStudent> currPod = new LinkedList<UniversityStudent>();
        System.out.println("\nPod Assignments:");
        int podCounter = 1;
        for (int l = 0; l < graphs.size(); ++l){
            HashSet<Node> bfsSet = new HashSet<Node>();

            Queue<Node> q = new LinkedList<Node>();
            q.add(graphs.get(l).get(0));
            bfsSet.add(q.peek());
            while (q.size() > 0){
                Node currNode = q.remove();
                currPod.add(currNode.stu);
                if (currPod.size() == podSize){
                    System.out.print("Pod " + (podCounter) + ":");
                    for (UniversityStudent stu : currPod){
                        if (stu.name != currPod.getFirst().name){
                            System.out.print(",");
                        }
                        System.out.print(" " + stu.name);
                        for (UniversityStudent stu2 : currPod){
                            stu.addPodMember(stu2);
                        }
                    }
                    podCounter++;
                    currPod.clear();
                    System.out.println("");
                }

                for (int i = 0; i < currNode.mstAdj.size(); ++i){
                    Node obtainedNode = graphRef.stuToNode.get(currNode.mstAdj.get(i).name);
                    if (!bfsSet.contains(obtainedNode)){
                        q.add(obtainedNode);
                        bfsSet.add(obtainedNode);
                    }
                }
            }
            if (currPod.size() > 0){
                System.out.print("Pod " + (podCounter) + ":");
                    for (UniversityStudent stu : currPod){
                        if (stu.name != currPod.getFirst().name){
                            System.out.print(",");
                        }
                        System.out.print(" " + stu.name);
                        for (UniversityStudent stu2 : currPod){
                            stu.addPodMember(stu2);
                        }
                    }
                    podCounter++;
                    currPod.clear();
                    System.out.println("");
            }
        }   
        System.out.println("");
    }


}
