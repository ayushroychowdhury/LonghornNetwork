import java.util.*;

/**
 * Represents a PodFormation
 * @author Yahir Lopez
 */
public class PodFormation {

    /**
     * Local copy of student Graph
     */
    private StudentGraph podGraph;

    /**
     * Pods which are a list of StudentGraphs.
     */
    private List<StudentGraph> pods;

    /**
     * Create a PodFormation from a StudentGraph
     * @param graph StudentGraph
     */
    public PodFormation(StudentGraph graph) {
        this.podGraph = new StudentGraph(graph);
        pods = new ArrayList<>();
    }

    /**
     * Form pods implementing Prim's algorithm
     * @param podSize the maxNumber of students in a Pod
     */
    public void formPods(int podSize) {
        // Copy the original graph and create an array of visited students
        StudentGraph tempGraph = new StudentGraph(podGraph);
        List<UniversityStudent> visited = new ArrayList<>();

        // All students in the Graph need to be in a Pod
        // So continue this loop until no Students exist
        // in the original graph
        while(!tempGraph.isEmpty()) {
            // The SubGraph to be added to pods
            StudentGraph workingGraph = new StudentGraph();

            // Add random Student to Pod
            UniversityStudent PBAC = tempGraph.getRoot();
            if (PBAC == null) {break;}
            workingGraph.addStudent(PBAC);
            visited.add(PBAC);
            tempGraph.removeStudent(PBAC);

            // DEBUG
            System.out.println("PBAC Node for PodFormation: " + PBAC.getName());
            System.out.println("TempGraph before pod formation: ");
            tempGraph.printGraph();

            // Continue Creating a Pod until the podSize is reached or there are no more students in the original graph;
            while (workingGraph.numberOfStudents() < podSize) {

                // Get the adjacent Nodes with the strongest Edges
                List<StudentGraph.StudentGraphEdge> edgesList = new LinkedList<>();
                for (UniversityStudent students : workingGraph.getStudents()) {
                    edgesList.add(podGraph.getStrongestEdge(students, visited));
                }

                // Traverse through the list of strongestEdges to find the true strongest edge
                StudentGraph.StudentGraphEdge strongestEdge = edgesList.get(0);
                try {
                    for (StudentGraph.StudentGraphEdge edge : edgesList) {
                        if(visited.contains(edge.getDestStudent())) {continue;}

                        if (strongestEdge.getWeight() < edge.getWeight()) {
                            strongestEdge = edge;
                        }
                    }

                    // If there are no more nodes to add
                    if (strongestEdge.getDestStudent() == null) {
                        throw new Exception();
                    }

                    if (visited.contains(strongestEdge.getDestStudent())){
                        continue;
                    }

                    // Add Edges and student into subGraph
                    // source Student is guaranteed to already exit in the graph
                    workingGraph.addEdge(strongestEdge.getSourceStudent(), strongestEdge);
                    workingGraph.addStudent(strongestEdge.getDestStudent());
                    workingGraph.addEdge(strongestEdge.getDestStudent(), strongestEdge.getSourceStudent(), strongestEdge.getWeight());

                    visited.add(strongestEdge.getDestStudent());
                    tempGraph.removeStudent(strongestEdge.getDestStudent());
                } catch (Exception e) {
                   break;
                }
            }

            System.out.println("PodGraph: ");
            workingGraph.printGraph();

            pods.add(workingGraph);
        }

        System.out.println("Pod Assignments:");
        int i = 0;
        for (StudentGraph pod : pods) {
            System.out.print("  Pod "+ i +": ");
            for (UniversityStudent student : pod.getStudents()) {
                System.out.print(student.getName() + ", ");
            }
            ++i;
            System.out.println();
        }
    }

    /**
     * Get the pods
     * @return 2D List of UniversityStudent Objects
     */
    public List<List<UniversityStudent>> getPods() {
        List r = new LinkedList<>();
        for (StudentGraph pod : pods) {
            List r2 = new LinkedList<>();
            for (UniversityStudent student : pod.getStudents()) {
                r2.add(student);
            }
            r.add(r2);
        }
        return r;
    }
}
