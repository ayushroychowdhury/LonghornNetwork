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
        this.podGraph = graph;
    }

    /**
     * Form pods implementing Prim's algorithm
     * @param podSize the maxNumber of students in a Pod
     */
    public void formPods(int podSize) {

    }
}
