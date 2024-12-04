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
            // User interaction for referral path finder
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the name of the starting student: ");
            String startName = scanner.nextLine();
            UniversityStudent startStudent = findStudentByName(startName, students);

            if (startStudent == null) {
                System.out.println("Student with name '" + startName + "' not found.");
                return;
            }

            System.out.print("Enter the target company: ");
            String targetCompany = scanner.nextLine();

            List<UniversityStudent> referralPath = pathFinder.findReferralPath(startStudent, targetCompany);

            if (referralPath.isEmpty()) {
                System.out.println("No referral path found from " + startName + " to a student with an internship at " + targetCompany + ".");
            } else {
                System.out.println("Referral path from " + startName + " to a student with an internship at " + targetCompany + ":");
                for (UniversityStudent student : referralPath) {
                    System.out.println(" - " + student.name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a student by name from the list of students.
     *
     * @param name     the name of the student to find
     * @param students the list of students
     * @return the UniversityStudent object if found, null otherwise
     */
    private static UniversityStudent findStudentByName(String name, List<UniversityStudent> students) {
        for (UniversityStudent student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
}