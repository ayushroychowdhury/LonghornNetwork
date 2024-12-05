import java.io.IOException;
import java.util.*;

import javax.swing.SwingUtilities;



/**
 * This is the main class of my lab 5 project. This class/file primarily serves as the controller for the project.
 * It handles initializing everything and having the overall flow of the project dependent on what happens here.
 * 
 * 
 */


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
            graph.printGraph();
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);

                    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI(graph);
            }
        });

            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("Enter Cmd: ");
                while (!scanner.hasNext()){}
                String input = scanner.nextLine();
                handleCmd(input, scanner, graph, pathFinder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleCmd(String cmd, Scanner consoleScanner, StudentGraph graph, 
    ReferralPathFinder pathFinder){
        switch (cmd){
            case "Referral": handleReferral(consoleScanner, graph, pathFinder);
                break;
            case "Friend Request": handleFriendRequest(consoleScanner, graph);
                break;
            case "Friend Respond": handleFriendResponse(consoleScanner, graph);
                break;
            case "Message": handleMessage(consoleScanner, graph);
                break;
            default: System.out.println("Improper Input, please try again");
        }

    }

    private static void handleReferral(Scanner consoleScanner, StudentGraph graph,
    ReferralPathFinder pathFinder){
        System.out.println("");

        System.out.print("Enter starting student: ");
        while(!consoleScanner.hasNext()){}
        String startString = consoleScanner.nextLine();
        UniversityStudent student = graph.getStudent(startString);
        if (student == null){
            System.out.println("Student Name is incorrect retry");
            return;
        }
        System.out.print("\nEnter target company: ");
        while(!consoleScanner.hasNext()){}
        String compString = consoleScanner.nextLine();
        ArrayList<UniversityStudent> path = pathFinder.findReferralPath(student, compString);
        if (path.size() == 0){
            System.out.println("No path to target company");
        } else {
            System.out.print("\nShortest Path to Company:\n" + path.get(0).name);
            for (int i = 1; i < path.size(); ++i){
                System.out.print("->" + path.get(i).name);
            }
            System.out.println("");
        }
    }

    private static void handleFriendRequest( Scanner consoleScanner, StudentGraph graph){
        System.out.println("");

        System.out.print("Enter sending student: ");
        while(!consoleScanner.hasNext()){}
        String send = consoleScanner.nextLine();
        UniversityStudent sendStu = graph.getStudent(send);
        if (sendStu == null){
            System.out.println("Not a student name, retry");
            return;
        }

        System.out.print("\nEnter receiving student: ");
        while(!consoleScanner.hasNext()){}
        String receive = consoleScanner.nextLine();
        UniversityStudent receiveStu = graph.getStudent(receive);
        if (receiveStu == null){
            System.out.println("Not a student name, retry");
            return;
        }
        FriendRequestThread friendThread = new FriendRequestThread(sendStu, receiveStu, 0);
        Thread thread = new Thread(friendThread);
        thread.start();
    }

    private static void handleFriendResponse( Scanner consoleScanner, StudentGraph graph){
        System.out.println("");

        System.out.print("Enter sending student: ");
        while(!consoleScanner.hasNext()){}
        String send = consoleScanner.nextLine();
        UniversityStudent sendStu = graph.getStudent(send);
        if (sendStu == null){
            System.out.println("Not a student name, retry");
            return;
        }

        System.out.print("\nEnter receiving student: ");
        while(!consoleScanner.hasNext()){}
        String receive = consoleScanner.nextLine();
        UniversityStudent receiveStu = graph.getStudent(receive);
        if (receiveStu == null){
            System.out.println("Not a student name, retry");
            return;
        }

        System.out.print("\nDo you accept their request? y/n:");
        while(!consoleScanner.hasNext()){}
        String response = consoleScanner.nextLine();
        if (response.equals("y")){
            FriendRequestThread friendThread = new FriendRequestThread(sendStu, receiveStu, 1);
            Thread thread = new Thread(friendThread);
            thread.start();
        } else if (response.equals("n")){
            FriendRequestThread friendThread = new FriendRequestThread(sendStu, receiveStu, 2);
            Thread thread = new Thread(friendThread);
            thread.start();
        } else {
            System.out.println("Not a valid response retry!");
        }

    }

    private static void handleMessage( Scanner consoleScanner, StudentGraph graph){
        System.out.println("");

        System.out.print("Enter sending student: ");
        while(!consoleScanner.hasNext()){}
        String send = consoleScanner.nextLine();
        UniversityStudent sendStu = graph.getStudent(send);
        if (sendStu == null){
            System.out.println("Not a student name, retry");
            return;
        }

        System.out.print("\nEnter receiving student: ");
        while(!consoleScanner.hasNext()){}
        String receive = consoleScanner.nextLine();
        UniversityStudent receiveStu = graph.getStudent(receive);
        if (receiveStu == null){
            System.out.println("Not a student name, retry");
            return;
        }

        System.out.print("\nEnter the message sent: ");
        while(!consoleScanner.hasNext()){}
        String message = consoleScanner.nextLine();

        ChatThread newMessage = new ChatThread(sendStu, receiveStu,message);
        Thread messThread = new Thread(newMessage);
        messThread.start();
    }

}
