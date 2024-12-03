import java.util.*;
import java.util.stream.Collectors;

/**
 * Reference for Referral Path finder
 * @author Yahir Lopez
 */
public class ReferralPathFinder {

    /**
     * Local Copy of StudentGraph
     */
    private StudentGraph referralGraph;

    /**
     * Construct a ReferralPathFinder object from a StudentGraph
     * @param graph StudentGraph
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.referralGraph = new StudentGraph(graph);
    }

    /**
     * Implements Dijkstra's algorithm to find the shortest path to a student who interned at a company
     * @param start UniversityStudent to begin algorithm from
     * @param targetCompany The desired company student should have interned at
     * @return student with the shortest path from starting student
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Create a list of University Student and associated weight
        // Initialize all to 0 (meaning unconnected)

        if (start == null || targetCompany == null) { return null; }

        Map<UniversityStudent, Integer> weights = new HashMap<>();
        for (UniversityStudent student : referralGraph.getStudents()) {
            weights.put(student, 0);
        }

        //Create another list to keep track of visited nodes
        List<UniversityStudent> visited = new ArrayList<>();

        // Set First student to be starting student and add it to que
        PriorityQueue<StudentQueue> pq = new PriorityQueue<>();
        pq.add(new StudentQueue(start, 0));

        while (!pq.isEmpty()) {
            StudentQueue current = pq.poll();
            UniversityStudent student = current.student;

            if (!visited.contains(student)) {
                visited.add(student);
                for (StudentGraph.StudentGraphEdge edge : referralGraph.getEdges(student)) {
                    // If current path weights more, add set the weight tracker and add it to pq
                    if ( (current.weight + edge.getWeight() > weights.get(edge.getDestStudent())) && !visited.contains(edge.getDestStudent())  ) {
                        weights.replace(edge.getDestStudent(), current.weight + edge.getWeight());
                        pq.add(new StudentQueue(edge.getDestStudent(), current.weight + edge.getWeight()));
                    }
                }
            }
        }

        // Print out the students with targetCompany
        boolean found = false;
        List<UniversityStudent> r = new ArrayList<>();
        for (UniversityStudent student : weights.keySet()) {
            r.add(student);
            for (String company : student.getPreviousInternships()) {
                if (company.equals(targetCompany) && !(weights.get(student).equals(0)) ) {
                    found = true;
                    System.out.println("Found referral path for student " + student.getName() + " with weight: " + weights.get(student));
                }
            }
        }

        if (!found) {
            System.out.println("No referral path found");
            return null;
        }

        return r;
    }

    /**
     * Reference for an element in the priority queue used in ReferralPathFinder
     */
    private class StudentQueue implements Comparable<StudentQueue> {
        /**
         * University Student node
         */
        UniversityStudent student;
        /**
         * Weight of edge
         */
        int weight;

        /**
         * Construct a pair of Student Object, and it's weight to be used in Dijkstra's algo
         * @param student Student
         * @param weight int
         */
        StudentQueue(UniversityStudent student, int weight) {
            this.student = student;
            this.weight = weight;
        }

        /**
         * The function used to order elements in the priority queue
         * @param other the object to be compared.
         * @return 0 if equal, 1 if object is greater than, -1 if object is less than
         * If an object is greater, it is placed later in the queue
         * If an object is less, it is placed earlier in the queue
         */
        @Override
        public int compareTo(StudentQueue other) {
            // The following logic is in the reverse of the natural order
            // because we want edges with larger weight to be
            // put earlier in the queue
            if (this.weight > other.weight) {
                return -1;
            }
            else if (this.weight < other.weight) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
