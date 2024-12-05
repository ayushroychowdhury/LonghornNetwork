import java.util.*;

/**
 * Represents a PodFormation
 *
 * @author Cooper Nelson
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
     *
     * @param newGraph StudentGraph
     */
    public PodFormation(StudentGraph newGraph) {
        this.podGraph = new StudentGraph(newGraph);
        pods = new ArrayList<>();
    }

    /**
     * Form pods implementing Prim's algorithm
     *
     * @param podSize the maxNumber of students in a Pod
     */
    public void formPods(int podSize) {
        StudentGraph newGraph = new StudentGraph(podGraph);
        List<UniversityStudent> visited = new ArrayList<>();
        
        while (!newGraph.isEmpty()) {
            StudentGraph currentGraph = new StudentGraph();
            UniversityStudent PBAC = newGraph.getRoot();
            if (PBAC == null) {
                break;
            }
            currentGraph.addStudent(PBAC);
            visited.add(PBAC);
            newGraph.removeStudent(PBAC);
            
            while (currentGraph.numberOfStudents() < podSize) {
                List<StudentGraph.StudentGraphEdge> edgesList = new LinkedList<>();
                for (UniversityStudent students : currentGraph.getStudents()) {
                    edgesList.add(podGraph.getStrongestEdge(students, visited));
                }
                StudentGraph.StudentGraphEdge strongestEdge = edgesList.get(0);
                try {
                    for (StudentGraph.StudentGraphEdge edge : edgesList) {
                        if (visited.contains(edge.getDestStudent())) {
                            continue;
                        }
                        if (strongestEdge.getWeight() < edge.getWeight()) {
                            strongestEdge = edge;
                        }
                    }
                    if (strongestEdge.getDestStudent() == null) {
                        throw new Exception();
                    }
                    if (visited.contains(strongestEdge.getDestStudent())) {
                        continue;
                    }
                    currentGraph.addEdge(strongestEdge.getSourceStudent(), strongestEdge);
                    currentGraph.addStudent(strongestEdge.getDestStudent());
                    currentGraph.addEdge(strongestEdge.getDestStudent(), strongestEdge.getSourceStudent(), strongestEdge.getWeight());
                    visited.add(strongestEdge.getDestStudent());
                    newGraph.removeStudent(strongestEdge.getDestStudent());
                } catch (Exception e) {
                    break;
                }
            }

            pods.add(currentGraph);
        }
        printPodAssignments();
    }

    /**
     * Print the pod assignments
     */
    public void printPodAssignments() {
        System.out.println("Pod Assignments:");
        int i = 0;
        for (StudentGraph pod : pods) {
            System.out.print("  Pod " + i + ": ");
            for (UniversityStudent student : pod.getStudents()) {
                System.out.print(student.getName() + ", ");
            }
            ++i;
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Get the pods
     *
     * @return 2D List of UniversityStudent Objects
     */
    public List<List<UniversityStudent>> getPods() {
        List linkedPods = new LinkedList<>();
        for (StudentGraph pod : pods) {
            List studentLinkedPods = new LinkedList<>();
            for (UniversityStudent student : pod.getStudents()) {
                studentLinkedPods.add(student);
            }
            linkedPods.add(studentLinkedPods);
        }
        return linkedPods;
    }
}