package program;

import java.util.*;

/**
 * A class that forms pods of students based on their connections
 */
public class PodFormation {
    private StudentGraph graph;
    private List<Student> unvisitedStudents;
    private List<List<Student>> pods;
    private int graphSize;

    /**
     * Constructor for a pod formation object, which stores the information about the student relations
     * @param graph the graph representing the connections between students
     */
    public PodFormation(StudentGraph graph) {
        this.graph = graph;
        this.unvisitedStudents = new ArrayList<Student>(graph.getStudents());
        this.graphSize = graph.getStudents().size();
    }

    /**
     * Forms pods of students in a specified size based on their connections
     * @param podSize the required size of each pod
     */
    public void formPods(int podSize) {
        /* First part: get minimum spanning forest */
        /* mstNodes stores the Students which are currently part of the MSF */
        List<Student> mstNodes = new ArrayList<Student>();

        /* mstEdges stores the StudentEdges which are currently part of the MSF */
        List<StudentEdge> mstEdges = new ArrayList<StudentEdge>();

        while (!unvisitedStudents.isEmpty()) {
            /* Start with any vertex. When the inner while loop terminates but there are still nodes left, start over in other connected component */
            Student currentStudent = unvisitedStudents.getFirst();
            mstNodes.add(currentStudent);
            unvisitedStudents.remove(currentStudent);
            List<StudentEdge> potentialEdges = new ArrayList<StudentEdge>(graph.getNonCyclicEdges(mstNodes));
            while (mstNodes.size() < graphSize && !potentialEdges.isEmpty()) {
                /* Choose the lowest weighted edge that won't form a cycle and add it (+ new node) to the mstEdges (and mstNodes) */
                StudentEdge minEdge = Collections.min(potentialEdges, Comparator.comparing(StudentEdge::getWeight));
                mstEdges.add(minEdge);
                mstNodes.add(minEdge.getTargetStudent());
                unvisitedStudents.remove(minEdge.getTargetStudent());

                /* Update potential new edges */
                potentialEdges = new ArrayList<StudentEdge>(graph.getNonCyclicEdges(mstNodes));
            }
        }
        System.out.println("Formation of MST completed.");

        /* Second part: divide minimum spanning forest in pods with size <= podSize */
        pods = StudentGraph.divideGraph(mstNodes, mstEdges, podSize);

        /* Print pods */
        System.out.println("Pods formed:");
        for (int i=0; i<pods.size(); i++) {
            System.out.print("Pod " + (i+1) + ": ");
            List<Student> pod = pods.get(i);
            for (Student student: pod){
                System.out.print(student.getName() + " ");
            }
            System.out.println();
        }
    }
}