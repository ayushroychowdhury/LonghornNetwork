import java.io.IOException;
import java.util.*;

/**
 * Main class used to run the program.
 */
public class Main {
    public static void main(String[] args) {
        // if (args.length == 0) {
        //     System.out.println("Please provide the input file name as a command-line argument.");
        //     return;
        // }
        // String inputFile = args[0];

        // For CMD LINE
        // String inputFile = "../testing/input_sample.txt";
        // String inputFile = "../testing/pod_sample.txt";
        String inputFile = "../testing/roommate_sample.txt";

        // For DEBUGGER
        // String inputFile = "testing/input_sample.txt";
        // String inputFile = "testing/pod_sample.txt";
        // String inputFile = "testing/roommate_sample.txt";

        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);
            // printStudents(students);

            // Roommate matching
            System.out.println("Roomate Assignments:");
            GaleShapley.assignRoommates(students);
            // printStudents(students);
            for (UniversityStudent student : students) {
                System.out.println(student.name + " is roommates with " + student.roommate);
            }
            System.out.println();

            // // Pod formation
            System.out.println("\nPod Assignments:");
            // StudentGraph graph = new StudentGraph(students);
            // PodFormation podFormation = new PodFormation(graph);
            // podFormation.formPods(4);

            // // Referral path finding
            System.out.println("Referral Path:");
            // ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // // TODO: Implement user interaction for specifying a target company

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printStudents(List<UniversityStudent> studs) {
        for (UniversityStudent stud : studs) {
            System.out.println(stud + "\n");
        }
    }
}
