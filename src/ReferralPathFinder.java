import java.util.*;

/**
 * Finds referral paths in a student graph about companies.
 */
public class ReferralPathFinder {

    private StudentGraph graph;

    /**
     * Constructs a ReferralPathFinder using the given student graph.
     *
     * @param graph the graph representing relationships between students
     */
    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds a referral path from a starting student to a target company.
     *
     * @param start         the starting student
     * @param targetCompany the company for which to find the referral path
     * @return a list of students representing the referral path, or an empty list if no path exists
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        //maps and queues I need
        PriorityQueue<Pathcost> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Set<UniversityStudent> visited = new HashSet<>();
        Map<UniversityStudent, UniversityStudent> parentMap = new HashMap<>();
        Map<UniversityStudent, Integer> costMap = new HashMap<>();

        //initialise
        priorityQueue.add(new Pathcost(start, 0));
        costMap.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            Pathcost current = priorityQueue.poll();
            UniversityStudent currentstudent = current.student;

            //if visited
            if (visited.contains(currentstudent)) {
                continue;
            }
            visited.add(currentstudent);

            //if has the target company in their internship history
            if (currentstudent.previousInternships.contains(targetCompany)) {
                return buildPath(start, currentstudent, parentMap);
            }

            //check neighbors
            for (UniversityStudent neighbor : graph.getConnections(currentstudent)) {
                int connectionStrength = currentstudent.calculateConnectionStrength(neighbor);
                int newCost = current.cost;

                if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                    costMap.put(neighbor, newCost);
                    parentMap.put(neighbor, currentstudent);
                    priorityQueue.add(new Pathcost(neighbor, newCost));
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * build the referal path from the start student to the target student
     *
     * @param parentMap map containing the parent of each visited student
     * @param start the starting student
     * @param end the target
     * @return list of students representing the referral path
     */
    private List<UniversityStudent> buildPath(UniversityStudent start, UniversityStudent end, Map<UniversityStudent, UniversityStudent> parentMap) {
        List<UniversityStudent> path = new ArrayList<>();
        for (UniversityStudent at = end; at != null; at = parentMap.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * helper clcass to store students and costs in Dijkstra's algorithm
     */
    private static class Pathcost {
        UniversityStudent student;
        int cost;

        Pathcost(UniversityStudent student, int cost) {
            this.student = student;
            this.cost = cost;
        }
    }

}
