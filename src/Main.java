import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class
 */
public class Main {
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
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Issac"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "IDontExist");


            MainGUI gui = new MainGUI(graph);
            gui.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
