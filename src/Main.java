import java.io.IOException;
import java.util.*;

/**
 * 'Main' of the application.
 * Handles all operations.
 * this can parse student data, assign roommates, form pods, and find paths.
 */
public class Main {
    /**
     * The main method to run the application.
     * Parses command-line arguments, processes student data, and performs graph-based operations.
     *
     * @param args command-line arguments; expects the input file name as the first argument
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[0];
        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);

            // Roommate matching
            GaleShapley.assignRoommates(students);

            // Pod formation
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // TODO: Implement user interaction for specifying a target company

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
