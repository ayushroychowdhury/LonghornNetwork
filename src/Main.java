import java.io.IOException;
import java.util.*;

/**
 * Entry point class for the program
 */
public class Main {

    /**
     * Entry point for the program. Reads the input file and calls the methods for room assignment, pod formation and referral path finding
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

            /* Roommate matching */
            GaleShapley.assignRoommates(students);

            /* Pod formation */
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            /* Invert weights in studentgraph for referral path finding */
            graph.invertWeights();

            /* Referral path finding */
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // TODO: Implement user interaction for specifying a target company

            while(true){
                /* Create scanner object */
                Scanner sc = new Scanner(System.in);

                /* Get student */
                System.out.print("Enter the Student: ");
                String studentName = sc.nextLine();

                /* Check if student is valid */
                UniversityStudent start = null;
                for(UniversityStudent student : students){
                    if(student.getName().equals(studentName)){
                        start = student;
                    }
                }

                /* Ask for new student if student was invalid */
                if(start == null){
                    System.out.println("Student not found.");
                    continue;
                }

                /* Get target company */
                System.out.print("Enter the Company: ");
                String targetCompany = sc.nextLine();

                /* Get referral path */
                List<UniversityStudent> referralPath = pathFinder.findReferralPath(start, targetCompany);

                if(referralPath == null) {
                    System.out.println("No path found.");
                }else{
                    System.out.print("Referral Path: ");
                    for(UniversityStudent student : referralPath){
                        System.out.print(student.getName() + " -> ");
                    }
                    System.out.println(targetCompany);
                }

                /* Ask if user wants to quit */
                System.out.print("Do you want to quit? (y/n): ");
                String quit = sc.nextLine();
                while (!quit.equals("y") && !quit.equals("n")) {
                    System.out.print("Invalid input. Do you want to quit? (y/n): ");
                    quit = sc.nextLine();
                }
                if (quit.equals("y")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}