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
        // Method signature only
        return new ArrayList<>();
    }
}