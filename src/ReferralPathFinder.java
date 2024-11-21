import java.util.*;


/**
 * This is the class/function that handles the algorithm for attempting to get an internship
 * through who you know. It should use djkstra's algorithm?  (will have to look into it more)
 * to determine the path for this
 */
public class ReferralPathFinder {
    /**
     * construction which will basically just set the correct graph for the class based on the input graph
     * @param graph
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor
    }
    /**
     * The function that will be doing the bulk of the work. It takes a starting student
     * and attempts to trace a path to get to a specific company they are hoping to intern at
     * @param start The starting student from which we will execute the algo
     * @param targetCompany The ending company that serves as the goal
     * @return The list of students that we must take to reach our target company
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        // Method signature only
        return new ArrayList<>();
    }
}
