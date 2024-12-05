import java.io.IOException;
import java.util.*;

/**
 * The main class for the application.
 */
public class Main {
    /**
     * The main method for the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.out.println("Please provide the input file name as a command-line argument.");
//            return;
//        }
//        String inputFile = args[0];
        String inputFile = "testing\\roommate_sample.txt";
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
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Issac"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "IDontExist");


            // TODO: Implement user interaction for specifying a target company

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
