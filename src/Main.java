import java.io.IOException;
import java.util.*;

public class Main {
    /**
     * Main method that runs the Longhorn Network Simulation.
     * @param args
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
            System.out.println(graph);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            pathFinder.findReferralPath(students.get(0), "Butthead Inc.");
            // TODO: Implement user interaction for specifying a target company

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
