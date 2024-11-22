import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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

            System.out.println("\nReferral Path:");

            List<UniversityStudent> referralPath1 = pathFinder.findReferralPath(students.get(1), "Amazon");
            pathFinder.printReferralPath(referralPath1, "Amazon");

            List<UniversityStudent> referralPath2 = pathFinder.findReferralPath(students.get(0), "Microsoft");
            pathFinder.printReferralPath(referralPath2, "Microsoft");

            System.out.println();

            // Friend Requests & Chatting
            ExecutorService executor = Executors.newFixedThreadPool(3);

            executor.submit(new FriendRequestThread(students.getFirst(), students.getLast()));
            executor.submit(new ChatThread(students.getFirst(), students.getLast(), "Hey Bob, are you free for lunch?"));
            executor.submit(new ChatThread(students.getLast(), students.getFirst(), "Sorry Alice, I'm too busy doing ur mom."));

            executor.shutdown();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
