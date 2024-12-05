import java.util.*;

/**
 * Reference for Referral Path finder
 *
 * @author Cooper Nelson
 */
public class ReferralPathFinder {

    /**
     * Local Copy of StudentGraph
     */
    private StudentGraph referralGraph;

    /**
     * Construct a ReferralPathFinder object from a StudentGraph
     *
     * @param graph StudentGraph
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.referralGraph = new StudentGraph(graph);
    }

    /**
     * Implements Dijkstra's algorithm to find the shortest path to a student who interned at a company
     *
     * @param start         UniversityStudent to begin algorithm from
     * @param targetCompany The desired company student should have interned at
     * @return student with the shortest path from starting student
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        if (start == null || targetCompany == null) {
            return null;
        }
        Map<UniversityStudent, Integer> weights = new HashMap<>();
        for (UniversityStudent student : referralGraph.getStudents()) {
            weights.put(student, 0);
        }
        List<UniversityStudent> visited = new ArrayList<>();
        PriorityQueue<StudentQueue> weightQueue = new PriorityQueue<>();
        weightQueue.add(new StudentQueue(start, 0));
        while (!weightQueue.isEmpty()) {
            StudentQueue current = weightQueue.poll();
            UniversityStudent student = current.student;
            if (!visited.contains(student)) {
                visited.add(student);
                for (StudentGraph.StudentGraphEdge edge : referralGraph.getEdges(student)) {
                    if ((current.weight + edge.getWeight() > weights.get(edge.getDestStudent())) && !visited.contains(edge.getDestStudent())) {
                        weights.replace(edge.getDestStudent(), current.weight + edge.getWeight());
                        weightQueue.add(new StudentQueue(edge.getDestStudent(), current.weight + edge.getWeight()));
                    }
                }
            }
        }
        boolean found = false;
        List<UniversityStudent> referralPath = new ArrayList<>();
        for (UniversityStudent student : weights.keySet()) {
            referralPath.add(student);
            for (String company : student.getPreviousInternships()) {
                if (company.equals(targetCompany) && !(weights.get(student).equals(0))) {
                    found = true;
                    System.out.println("Found referral path for student " + student.getName() + " with weight: " + weights.get(student));
                }
            }
        }
        if (!found) {
            System.out.println("No referral path found");
            System.out.println();
            return null;
        }
        System.out.println();
        return referralPath;
    }

    /**
     * Private class to store a student and it's weight
     */
    private class StudentQueue implements Comparable<StudentQueue> {
        /**
         * Student Object
         */
        private UniversityStudent student;
        /**
         * Weight of edge
         */
        private int weight;

        /**
         * Construct a pair of Student Object, and it's weight
         *
         * @param student Student
         * @param weight  int
         */
        public StudentQueue(UniversityStudent student, int weight) {
            this.student = student;
            this.weight = weight;
        }

        /**
         * The function used to order elements in the priority queue
         *
         * @param other the object to be compared.
         */
        @Override
        public int compareTo(StudentQueue other) {
            if (this.weight > other.weight) {
                return -1;
            } else if (this.weight < other.weight) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}