import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public HashMap<String, UniversityStudent> students;
    public static HashMap<String, List<UniversityStudent>> friends;
    public static ArrayList<String> chatHistory;

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

            // Friend request simulation
            simulateFriendRequests(students);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simulates friend requests between students.
     * @param students
     */
    public static void simulateFriendRequests(List<UniversityStudent> students) {
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            for (UniversityStudent student : students) {
                for (UniversityStudent other : students) {
                    if (!student.equals(other)) {
                        executor.submit(new FriendRequestThread(student, other));
                    }
                }
            }
            for (UniversityStudent student : students) {
                for (UniversityStudent other : students) {
                    if (!student.equals(other)) {
                        executor.submit(new ChatThread(student, other, "Hello!"));
                    }
                }
            }
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
    }
}
