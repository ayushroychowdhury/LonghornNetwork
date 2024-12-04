import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static HashMap<UniversityStudent, List<UniversityStudent>> friends = new HashMap<>();
    public static ArrayList<String> chatHistory = new ArrayList<>();

    private static List<UniversityStudent> students;
    private static StudentGraph graph;
    private static Map<Integer, List<UniversityStudent>> pods;
    
    /**
     * Main method that runs the Longhorn Network Simulation.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[0];
        try {
            students = DataParser.parseStudents(inputFile);
            for (UniversityStudent student : students) {
                Main.friends.put(student, new ArrayList<>());
            }
            // Roommate matching
            GaleShapley.assignRoommates(students);

            // Pod formation
            graph = new StudentGraph(students);
            System.out.println(graph);
            PodFormation podFormation = new PodFormation(graph);
            pods = podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            pathFinder.findReferralPath(students.get(1), "FindMe");
            pathFinder.findReferralPath(students.get(2), "FindMe");
            pathFinder.findReferralPath(students.get(1), "IDontExist");
            // TODO: Implement user interaction for specifying a target company

            // Friend request simulation

            // Output results
            System.out.println("\n\nRoommate Assignments:\n");
            for (UniversityStudent student : students) {
                System.out.println(student.getName() + " is roommates with " + student.getRoommate());
            }
            System.out.println("\nPods:");
            for (Integer i : pods.keySet()) {
                System.out.print("Pod " + i + ": ");
                System.out.print(pods.get(i).get(0).getName());
                for (UniversityStudent podMember : pods.get(i)) {
                    if(podMember != pods.get(i).get(0)) {
                        System.out.print(", " + podMember.getName());
                    }
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("Referral Paths:");
            System.out.print("Simon");
            for (UniversityStudent student : pathFinder.findReferralPath(graph.getStudent("Simon"), "FindMe")) {
                if(student != graph.getStudent("Simon")) {
                    System.out.print(" -> " + student.getName());
                }
            }
            System.out.println();
            System.out.print("Jimmy");
            for (UniversityStudent student : pathFinder.findReferralPath(graph.getStudent("Jimmy"), "FindMe")) {
                if(student != graph.getStudent("Jimmy")) {
                    System.out.print(" -> " + student.getName());
                }
            }
            System.out.println();
            System.out.print("Whale");
            for (UniversityStudent student : pathFinder.findReferralPath(graph.getStudent("Whale"), "FindMe")) {
                if(student != graph.getStudent("Whale")) {
                    System.out.print(" -> " + student.getName());
                }
            }
            System.out.println("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        simulateFriendRequests(students);
        runGUI();
    }

    /**
     * Runs the Longhorn Network GUI.
     */
    private static void runGUI() {
        // Create the frame
        JFrame frame = new JFrame("LONGHORN NETWORK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        
        // Create the title label
        JLabel titleLabel = new JLabel("--- Longhorn Network ---", SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        // Create the text area
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Create buttons
        JButton showGraphButton = new JButton("Show Graph");
        JButton showRoommatesButton = new JButton("Show Roommates");
        JButton showPodsButton = new JButton("Show Pods");
        JButton simulateThreadsButton = new JButton("Simulate Threads");
        
        // Add buttons to the panel
        buttonPanel.add(showGraphButton);
        buttonPanel.add(showRoommatesButton);
        buttonPanel.add(showPodsButton);
        buttonPanel.add(simulateThreadsButton);
        
        // Add the panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        simulateThreadsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulateFriendRequests(students);
                String chatHistoryString = "";
                for (String message : chatHistory) {
                    chatHistoryString += message + "\n";
                }
                textArea.setText(chatHistoryString);
            }
        });

        // Add action listeners to the buttons
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(graph.toString());
            }
        });

        showRoommatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Roommates = "";
                for (UniversityStudent student : students) {
                    String studentName = student.getName();
                    String roommateName = student.getRoommate();
                    if(roommateName != null) {
                        Roommates += studentName + " is roommates with " + roommateName + ".\n";
                    } else {
                        Roommates += studentName + " is not assigned a roommate.\n";
                    }
                }
                textArea.setText(Roommates);
            }
        });

        showPodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Pods = "";
                for (Integer i : pods.keySet()) {
                    Pods += "Pod " + i + ": ";
                    Pods += pods.get(i).get(0).getName();
                    for (UniversityStudent podMember : pods.get(i)) {
                        if(podMember != pods.get(i).get(0)) {
                            Pods += ", " + podMember.getName();
                        }
                    }
                    Pods += "\n";
                }
                textArea.setText(Pods);
            }
        });
        // Set the frame visibility
        frame.setVisible(true);
    }

    /**
     * Simulates friend requests between students.
     * @param students List of students
     */
    private static void simulateFriendRequests(List<UniversityStudent> students) {
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

        System.out.println("Chat history:");
        for (String message : chatHistory) {
            System.out.println(message);
        }
        System.out.println("Friendships:");
        for (UniversityStudent student : friends.keySet()) {
            for (UniversityStudent friend : friends.get(student)) {
                System.out.println(student.getName() + " is friends with " + friend.getName());
            }
        }
    }
}
