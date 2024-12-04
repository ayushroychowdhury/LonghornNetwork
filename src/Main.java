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

    public static String graphText = "";
    public static String roommateText = "";
    public static String podText = "";

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
                // System.out.println(s.name + " wants to be roommates with " + s.roommatePreferences);
            }


            // Use Gale Shapley to assign roommates
            roommateText = GaleShapley.assignRoommates(students);
            System.out.println();


            // Initialize StudentGraph
            StudentGraph graph = new StudentGraph(students);
            for (int i = 0 ; i < students.size() ; i++) {
                for (int j = i+1 ; j < students.size() ; j++) {
                    graph.addEdge(students.get(i).name, students.get(j).name, students.get(i).calculateConnectionStrength(students.get(j)));
                }
            }

            for (Student s : students) {
                System.out.println(s.name + " is connected to " + graph.getNeighborsWeighted(s.name));
                graphText += s.name + " is connected to " + graph.getNeighborsWeighted(s.name) + ".\n";
            }



            // Pod formation
            // StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            System.out.println("Pod Assignments:");
            List<List<String>> pods = podFormation.formPods(4);
            int podNum = 0;
            for (List<String> pod : pods) {
                System.out.print("Pod " + ++podNum + ": ");
                podText += "Pod " + podNum + ": ";
                for (int i = 0 ; i < pod.size()-1 ; i++) {
                    System.out.print(pod.get(i) + ", ");
                    podText += pod.get(i) + ", ";
                }
                System.out.println(pod.get(pod.size()-1));
                podText += pod.get(pod.size()-1) + "\n";
            }
            System.out.println();


            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            // TODO: Implement user interaction for specifying a target company
//            pathFinder.findReferralPath(nameMap.get("Timmy"), "FindMe");
//            pathFinder.findReferralPath(nameMap.get("Issac"), "FindMe");
//            pathFinder.findReferralPath(nameMap.get("Timmy"), "IDontExist");


            ArrayList<String> studentNames = new ArrayList<>();
            for (UniversityStudent s : students) studentNames.add(s.name);
            simulate(studentNames);
            //System.out.println(chatHistory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static void simulate(List<String> students) {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            // Simulate friend requests
            executor.submit(new FriendRequestThread(nameMap.get("Alice"), nameMap.get("Bob")));
            executor.submit(new FriendRequestThread(nameMap.get("Bob"), nameMap.get("Alice")));
            executor.submit(new FriendRequestThread(nameMap.get("Alice"), nameMap.get("Charlie")));
            executor.submit(new FriendRequestThread(nameMap.get("Alice"), nameMap.get("Debbie")));

            // Simulate chat threads
            executor.submit(new ChatThread(nameMap.get("Edward"), nameMap.get("Frank"), "Hello, longhorn!"));
            executor.submit(new ChatThread(nameMap.get("Frank"), nameMap.get("Debbie"), "Hello, longhorn!"));
            executor.submit(new ChatThread(nameMap.get("Charlie"), nameMap.get("Alice"), "Hello, longhorn!"));
            executor.submit(new ChatThread(nameMap.get("Bob"), nameMap.get("Edward"), "Hello, longhorn!"));

        } finally {
            executor.shutdown();

            try {
                // Wait for all tasks to complete
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) { // Adjust timeout as needed
                    System.out.println("Some threads didn't finish in time, forcing shutdown.");
                    executor.shutdownNow(); // Forcefully terminate running tasks
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted while waiting for threads to finish.");
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        // Print results after simulation
        System.out.println("\nFriend Lists:");
        for (String s : friends.keySet()) System.out.println(s + " is friends with " + friends.get(s));
        System.out.println("\nChat History: " + ChatThread.chatHistory);


        GUI.displayGUI();

    }
}
