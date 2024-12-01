import java.util.*;

/**
 * ReferralPathFinder is a utility class that finds a referral path from a student to a target company
 */
public class ReferralPathFinder {

    private StudentGraph graph;

    /**
     * Constructs a ReferralPathFinder object based on the student connection information in the student graph
     * @param graph the student graph representing the connections between students
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor
        this.graph = graph;
    }

    /**
     * Finds a referral path from the start student to the target company
     * @param start the student from which the referral path should start
     * @param targetCompany the company to which the referral path should lead
     * @return a list of students representing the referral path from the start student to the target company
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        /* Find referral path using dijkstras algorithm */
        /* Store distances, previous nodes and queue */
        Map<UniversityStudent, Integer> distance = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        PriorityQueue<UniversityStudent> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        /* Initialize distances and previous nodes */
        for (UniversityStudent student : graph.getStudents()) {
            distance.put(student, Integer.MAX_VALUE);
            previous.put(student, null);
        }

        /* Set distance of start node to 0 */
        distance.put(start, 0);
        queue.add(start);

        /* Dijkstras algorithm */
        while (!queue.isEmpty()) {
            UniversityStudent current = queue.poll();
            for (UniversityStudent neighbor : graph.getAdjacentStudents(current)) {
                int newDistance = distance.get(current) + graph.getEdgeWeight(current, neighbor);
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        /* Reconstruct path */
        List<UniversityStudent> path = new ArrayList<>();

        /* Find all students with target company as previous internship and sort them ascending according to distance to start student */
        PriorityQueue<UniversityStudent> targetStudents = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        for (UniversityStudent student : graph.getStudents()) {
            if (student.getPreviousInternships().contains(targetCompany)) {
                targetStudents.add(student);
            }
        }

        if (targetStudents.isEmpty()) {
            return null;
        }

        /* Check if path between start and targetStudent exists */
        for (UniversityStudent targetStudent : targetStudents) {
            UniversityStudent current = targetStudent;
            while (current != null) {
                path.add(current);
                current = previous.get(current);
            }
            if (path.contains(start)) {
                Collections.reverse(path);
                return path;
            }
            path.clear();
        }

        return null;
    }
}