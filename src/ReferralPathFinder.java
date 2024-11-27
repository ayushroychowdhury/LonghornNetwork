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
        //pq
        //have a distance map (cost how many steps to find the end)
        //have a visited (or a set)
        //and parent map
        //build path method needed
        //store cost method
        return new ArrayList<>();
    }

    /**
     * build the referal path from the start student to the target student
     *
     * @param start the starting student
     * @param end the target
     * @return list of students representing the referral path
     */
    private List<UniversityStudent> buildPath(UniversityStudent start, UniversityStudent end) {
        List<UniversityStudent> path = new ArrayList<>();
        //for loop here...trace to the top?

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
