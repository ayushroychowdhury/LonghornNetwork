import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class used to run the program.
 */
public class Main {
    public static List<String> chatLog;
    public static void main(String[] args) {
        chatLog = new ArrayList<>();
        // if (args.length == 0) {
        //     System.out.println("Please provide the input file name as a command-line argument.");
        //     return;
        // }
        // String inputFile = args[0];

        // For CMD LINE
        // String inputFile = "../testing/input_sample.txt";
        // String inputFile = "../testing/pod_sample.txt";
        String inputFile = "../testing/referral_path_sample.txt";
        // String inputFile = "../testing/roommate_sample.txt";

        // For DEBUGGER
        // String inputFile = "testing/input_sample.txt";
        // String inputFile = "testing/pod_sample.txt";
        // String inputFile = "testing/referral_path_sample.txt";
        // String inputFile = "testing/roommate_sample.txt";

        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);
            printStudents(students);

            Map<String, UniversityStudent> nameStudentMap = new HashMap<>();
            for (UniversityStudent student : students) {
                nameStudentMap.put(student.name, student);
            }

            // ROOMMATE MATCHING
            System.out.println("Roomate Assignments:");
            GaleShapley.assignRoommates(students, nameStudentMap);
            for (UniversityStudent student : students) {
                System.out.println(student.name + " is roommates with " + student.roommate);
            }
            System.out.println();

            // POD FORMATION
            System.out.println("\nPod Assignments:");
            StudentGraph graph = new StudentGraph(students, nameStudentMap);
            // System.out.println(graph);
            PodFormation podFormation = new PodFormation(graph, nameStudentMap);
            List<List<String>> pods = podFormation.formPods(4);
            for (int i = 0; i < pods.size(); ++i) {
                System.out.print("Pod " + i + ":");
                for (String stud : pods.get(i)) {
                    System.out.print(" " + stud);
                }
                System.out.println();
            }

            // REFERRAL PATH FINDING
            System.out.println("\nReferral Path:");
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph, nameStudentMap);
            System.out.println(processPath("Timmy", "FindMe", pathFinder, nameStudentMap));
            System.out.println(processPath("Issac", "FindMe", pathFinder, nameStudentMap));
            System.out.println(processPath("Jimmy", "FindMe", pathFinder, nameStudentMap));
            System.out.println(processPath("Whale", "FindMe", pathFinder, nameStudentMap));
            System.out.println(processPath("Simon", "FindMe", pathFinder, nameStudentMap));
            System.out.println(processPath("Timmy", "IDontExist", pathFinder, nameStudentMap));

            // THREADS
            simulation(students);

            new GUI();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processPath(String name, String company, ReferralPathFinder pf, Map<String, UniversityStudent> nsm) {
        Map<String, Object> results = pf.findReferralPath(nsm.get(name), company);
        Integer weight = (Integer) results.get("weight");
        String result = "";
        if (weight == -1)
            result += "No referral path found for " + name;
        else {
            List<UniversityStudent> path = (List<UniversityStudent>) results.get("path");
            result += "Path ( weight = " + weight + " ): ";
            for (UniversityStudent stud : path) {
                result += stud.name + " -> ";
            }
        }
        return result;
    }

    private static void simulation(List<UniversityStudent> students) {
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            for (int i = 0, n = students.size(); i < n; ++i) {
                for (int k = 0; k < n; ++k) {
                    if (i == k)
                        continue;
                    executor.submit(new FriendRequestThread(students.get(i), students.get(k)));
                }
            }
            for (int i = 0, n = students.size(); i < n; ++i) {
                for (int k = 0; k < n; ++k) {
                    if (i == k)
                        continue;
                    executor.submit(new ChatThread(students.get(i), students.get(k), "my favorite number is " + (i + k)));
                }
            }
        }
        finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("Not all threads completed.");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                System.out.println("Threads interrupted before completion.");
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        for (UniversityStudent student : students) {
            System.out.print(student.name + " is friends with ");
            if (student.friends.size() == 0)
                System.out.println(" nobody.");
            else {
                for (String stu : student.friends) {
                    System.out.print(stu + ", ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("CHAT LOG");
        for (String log : chatLog) {
            System.out.println(log);
        }
    }

    private static void printStudents(List<UniversityStudent> studs) {
        for (UniversityStudent stud : studs) {
            System.out.println(stud + "\n");
        }
    }
}
