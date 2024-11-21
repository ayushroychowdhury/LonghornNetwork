import java.io.IOException;
import java.util.*;

/**
 * This is the main class of my lab 5 project. This class/file primarily serves as the controller for the project.
 * It handles initializing everything and having the overall flow of the project dependent on what happens here.
 * 
 * 
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
            // TODO: Implement user interaction for specifying a target company

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
