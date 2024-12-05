import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The Main class serves as the entry point for the program, orchestrating the
 * roommate matching, pod formation, and referral path finding processes.
 */
public class Main {
    /**
     * The main method processes the input file, assigns roommates, forms pods,
     * and facilitates referral path finding.
     *
     * @param args The command-line arguments.
     *             The first argument is expected to be the input file name.
     *             The second argument (optional) is a file containing test queries.
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

            // Output roommate assignments
            System.out.println("\nRoommate Assignment:");
            for (UniversityStudent student : students) {
                Student roommate = student.getRoommate();
                if (roommate != null) {
                    System.out.println(student.getName() + " is roommates with " + roommate.getName());
                }
            }

            // Pod formation
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);

            // Read test queries from file if provided
            List<String[]> testQueries = new ArrayList<>();
            if (args.length > 1) {
                String queryFile = args[1];
                BufferedReader queryReader = new BufferedReader(new FileReader(queryFile));
                String queryLine;
                while ((queryLine = queryReader.readLine()) != null) {
                    if (!queryLine.trim().isEmpty()) {
                        String[] queryParts = queryLine.split(",");
                        if (queryParts.length == 2) {
                            testQueries.add(new String[]{queryParts[0].trim(), queryParts[1].trim()});
                        }
                    }
                }
                queryReader.close();
            }

            if (!testQueries.isEmpty()) {
                for (String[] query : testQueries) {
                    String startName = query[0];
                    String targetCompany = query[1];
                    UniversityStudent startStudent = graph.getStudent(startName);

                    if (startStudent == null) {
                        System.out.println("Student " + startName + " not found.");
                    } else {
                        List<UniversityStudent> path = pathFinder.findReferralPath(startStudent, targetCompany);
                        if (path != null) {
                            System.out.print("Referral Path: ");
                            for (int i = 0; i < path.size(); i++) {
                                System.out.print(path.get(i).getName());
                                if (i < path.size() - 1) {
                                    System.out.print(" -> ");
                                }
                            }
                            System.out.println();
                        } else {
                            System.out.println("No referral path found to " + targetCompany);
                        }
                    }
                }
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\nEnter referral path queries. Type 'exit' to stop.");
                while (true) {
                    System.out.print("Enter the name of the starting student: ");
                    String startName = scanner.nextLine();
                    if (startName.equalsIgnoreCase("exit")) {
                        break;
                    }
                    System.out.print("Enter the target company: ");
                    String targetCompany = scanner.nextLine();
                    if (targetCompany.equalsIgnoreCase("exit")) {
                        break;
                    }

                    UniversityStudent startStudent = graph.getStudent(startName);

                    if (startStudent == null) {
                        System.out.println("Student not found.");
                    } else {
                        List<UniversityStudent> path = pathFinder.findReferralPath(startStudent, targetCompany);
                        if (path != null) {
                            System.out.print("Referral Path: ");
                            for (int i = 0; i < path.size(); i++) {
                                System.out.print(path.get(i).getName());
                                if (i < path.size() - 1) {
                                    System.out.print(" -> ");
                                }
                            }
                            System.out.println();
                        } else {
                            System.out.println("No referral path found to " + targetCompany);
                        }
                    }
                }

                scanner.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}