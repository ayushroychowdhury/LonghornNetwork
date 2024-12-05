import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[0];
        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);
            System.out.println("Successfully parsed " + students.size() + " students.");

            // Roommate matching
            System.out.println("Performing roommate matching...");
            GaleShapley.assignRoommates(students);


            // Pod formation
            System.out.println("Forming pods...");
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            System.out.println("Finding referral paths...");
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // TODO: Implement user interaction for specifying a target company
//            Map<UniversityStudent, List<UniversityStudent>> paths = pathFinder.findReferralPaths(graph.getStudent("Timmy"), "FindMe");
//            for (Map.Entry<UniversityStudent, List<UniversityStudent>> entry : paths.entrySet()) {
//                System.out.print("Found referral path for student " + entry.getKey().name + " with weight: " + entry.getKey().getWeight() + ". Path: ");
//                for (UniversityStudent student : entry.getValue()) {
//                    System.out.print(student.name + " -> ");
//                }
//                System.out.println();
//            }


            pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Issac"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "IDontExist");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter a student name to find referral paths or 'exit' to quit: ");
                String studentName = scanner.nextLine();
                if (studentName.equalsIgnoreCase("exit")) {
                    break;
                }
                UniversityStudent student = findStudentByName(students, studentName);
                if (student == null) {
                    System.out.println("Student not found. Please try again.");
                    continue;
                }
                System.out.print("Enter the target company: ");
                String targetCompany = scanner.nextLine();
                // changed
//                Map<UniversityStudent, List<UniversityStudent>> paths = pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
//
//                for (Map.Entry<UniversityStudent, List<UniversityStudent>> entry : paths.entrySet()) {
//                    UniversityStudent targetStudent = entry.getKey();
//                    List<UniversityStudent> path = entry.getValue();
//
//                    System.out.print("Found referral path for student " + targetStudent.name + " with weight: ");
//                    System.out.print(distances.get(targetStudent) + ". Path: ");
//                    for (int i = 0; i < path.size(); i++) {
//                        System.out.print(path.get(i).name);
//                        if (i < path.size() - 1) System.out.print(" -> ");
//                    }
//                    System.out.println();
//                }

//                Map<UniversityStudent, List<UniversityStudent>> paths = pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
//
//                for (Map.Entry<UniversityStudent, List<UniversityStudent>> entry : paths.entrySet()) {
//                    UniversityStudent targetStudent = entry.getKey();
//                    List<UniversityStudent> path = entry.getValue();
//
//                    System.out.print("Found referral path for student " + targetStudent.name + " with weight: ");
//                    System.out.print(graph.getWeight(path)); // Replace with actual calculation or method to retrieve weight
//                    System.out.print(". Path: ");
//                    for (int i = 0; i < path.size(); i++) {
//                        System.out.print(path.get(i).name);
//                        if (i < path.size() - 1) System.out.print(" -> ");
//                    }
//                    System.out.println();
//                }
                Map<UniversityStudent, List<UniversityStudent>> paths = pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");

                for (Map.Entry<UniversityStudent, List<UniversityStudent>> entry : paths.entrySet()) {
                    UniversityStudent targetStudent = entry.getKey();
                    List<UniversityStudent> path = entry.getValue();

                    // Calculate the path weight
                    int weight = 0;
                    for (int i = 0; i < path.size() - 1; i++) {
                        UniversityStudent from = path.get(i);
                        UniversityStudent to = path.get(i + 1);
                        int edgeWeight = graph.getNeighbors(from).get(to);
                        weight += edgeWeight; // Use inverted weight as per the requirements
                    }

                    // Print the results
                    System.out.print("Found referral path for student " + targetStudent.name + " with weight: " + weight + ". Path: ");
                    for (int i = 0; i < path.size(); i++) {
                        System.out.print(path.get(i).name);
                        if (i < path.size() - 1) System.out.print(" -> ");
                    }
                    System.out.println();
                }


//                List<UniversityStudent> path = pathFinder.findReferralPath(student, targetCompany);
//
//                if (path.isEmpty()) {
//                    System.out.println("No referral path found to " + targetCompany + ".");
//                } else {
//                    System.out.print("Path to " + targetCompany + ": ");
//                    for (int i = 0; i < path.size(); i++) {
//                        System.out.print(path.get(i).name);
//                        if (i < path.size() - 1) System.out.print(" -> ");
//                    }
//                }
            }
            // Simulate Interactions
            System.out.println("Simulating interactions...");
            if (students.size() > 1) {
                Thread friendRequest = new Thread(new FriendRequestThread(students.get(0), students.get(1)));
                Thread chat = new Thread(new ChatThread(students.get(0), students.get(1), "Hello, how's it going?"));

                friendRequest.start();
                chat.start();

                friendRequest.join();
                chat.join();
            } else {
                System.out.println("Not enough students to simulate interactions.");
            }

            System.out.println("All operations completed.");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Error during thread execution: " + e.getMessage());
        }
    }

    /**
     * Finds a student by their name in the list of students.
     *
     * @param students the list of students.
     * @param name     the name of the student to find.
     * @return the UniversityStudent object if found, otherwise null.
     */
    private static UniversityStudent findStudentByName(List<UniversityStudent> students, String name) {
        for (UniversityStudent student : students) {
            if (student.name.equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
}
