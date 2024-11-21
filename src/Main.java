import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

/**
 * The main class. Program starts running here.
 */
public class Main {

    public static HashMap<String, UniversityStudent> nameMap = new HashMap<>();

    // if friends[Alice] contains Bob, then Alice sent a friend request to Bob but not necessarily vice versa.
    public static HashMap<String, HashSet<String>> friends = new HashMap<>();
    public static HashMap<String, HashSet<String>> interacted = new HashMap<>();

    // Chat messages and friend requests are stored sequentially as strings
    public static ArrayList<String> chatHistory = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[0];
        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);

            // Set up name map
            for (UniversityStudent s : students) {
                nameMap.put(s.name, s);
            }


            // Use Gale Shapley to assign roommates
            GaleShapley.assignRoommates(students);
            System.out.println("Roommate assignments:");
            for (Student s : students) {
                if (s.roommate != null) {
                    System.out.println(s.name + " is roommates with " + s.roommate);
                } else {
                    System.out.println(s.name + " is not paired with a roommate.");
                }
            }
            System.out.println();


            // Initialize StudentGraph
            StudentGraph graph = new StudentGraph(students);
            for (int i = 0 ; i < students.size() ; i++) {
                for (int j = i+1 ; j < students.size() ; j++) {
                    graph.addEdge(students.get(i).name, students.get(j).name, students.get(i).calculateConnectionStrength(students.get(j)));
                }
            }

//            for (Student s : students) {
//                System.out.println(s.name + " is connected to " + graph.getNeighborsWeighted(s.name));
//            }



            // Pod formation
            // StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            System.out.println("Pods are as follows:");
            System.out.println(podFormation.formPods(4));
            System.out.println();


            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // TODO: Implement user interaction for specifying a target company


            // Simulate chat message
            ChatThread chat = new ChatThread(students.get(0), students.get(1), "Hello, Bob!");
            Thread t1 = new Thread(chat);
            t1.start();

            // Simulate friend request
            FriendRequestThread friendRequest = new FriendRequestThread(students.get(1), students.get(0));
            Thread t2 = new Thread(friendRequest);
            t2.start();

            while (chatHistory.size() < 2) {}
            System.out.println("Chat history: " + chatHistory);

            ArrayList<String> studentNames = new ArrayList<>();
            for (UniversityStudent s : students) studentNames.add(s.name);
            simulate(studentNames);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static void simulate(List<String> students) {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            // Simulate friend requests
            for (String student : students) {
                for (String target : students) {
                    if (!student.equals(target)) {
                        executor.submit(new FriendRequestThread(nameMap.get(student), nameMap.get(target)));
                    }
                }
            

            // Simulate chat threads
            for (String student : students) {
                for (String target : students) {
                    if (!student.equals(target)) {
                        executor.submit(new ChatThread(nameMap.get(student), nameMap.get(target), "Hi " + target + " from " + student + "!"));
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

        // Print results after simulation
        System.out.println("Friend Lists:");
        friends.forEach((student, friends) -> System.out.println(student + ": " + friends));

    }
}
