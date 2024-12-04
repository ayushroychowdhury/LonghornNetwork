import program.*;

import java.io.IOException;
import java.util.*;

/**
 * Entry point class for the program
 */
public class Main {

    /**
     * Entry point for the program. Reads the input file and calls the methods for room assignment, pod formation and referral path finding
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[0];

        List<UniversityStudent> students = new ArrayList<UniversityStudent>();
        try {
            students = DataParser.parseStudents(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
            /* Roommate matching */
            GaleShapley.assignRoommates(students);

            /* Pod formation */
            StudentGraph graph = new StudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            /* Invert weights in studentgraph for referral path finding */
            graph.invertWeights();

            /* Referral path finding */
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);

            /* User interaction */
            /* Create scanner object */
            Scanner sc = new Scanner(System.in);
        label:
        while(true){
            /* Ask for action user wants to perform */
            System.out.print("What do you want to do? (Quit: q, Find referral path: p, Send friend request: f, Send message: m): ");
            String action = sc.nextLine();
            while (!action.equals("q") && !action.equals("p") && !action.equals("f") && !action.equals("m")) {
                System.out.print("Invalid input. What do you want to do? (Quit: q, Find referral path: p, Send friend request: f, Send message: m): ");
                action = sc.nextLine();
            }
            switch (action) {
                case "q":
                    break label;
                case "p":
                    /* Get student */
                    System.out.print("Enter the Student: ");
                    String studentName = sc.nextLine();
                    studentName = studentName.strip();

                    /* Check if student is valid */
                    UniversityStudent start = null;
                    for (UniversityStudent student : students) {
                        if (student.getName().equals(studentName)) {
                            start = student;
                        }
                    }

                    /* Ask for new student if student was invalid */
                    if (start == null) {
                        System.out.println("Student not found.");
                        continue;
                    }

                    /* Get target company */
                    System.out.print("Enter the Company: ");
                    String targetCompany = sc.nextLine();
                    targetCompany = targetCompany.strip();

                    /* Get referral path */
                    List<UniversityStudent> referralPath = pathFinder.findReferralPath(start, targetCompany);

                    if (referralPath == null) {
                        System.out.println("No path found.");
                    } else {
                        System.out.print("Referral Path: ");
                        for (UniversityStudent student : referralPath) {
                            System.out.print(student.getName() + " -> ");
                        }
                        System.out.println(targetCompany);
                    }
                    break;
                case "f":
                    /* Friend request */
                    /* Get sender */
                    System.out.print("Enter the Sender: ");
                    String friendSenderName = sc.nextLine();
                    friendSenderName = friendSenderName.strip();

                    /* Check if sender is valid */
                    UniversityStudent friendSender = null;
                    friendSender = UniversityStudent.getStudentFromString(friendSenderName, students);
                    while (friendSender == null){
                        System.out.println("Student not found.");
                        System.out.print("Enter the Sender: ");
                        friendSenderName = sc.nextLine();
                        friendSender = UniversityStudent.getStudentFromString(friendSenderName, students);
                    }

                    /* Get Receiver */
                    System.out.print("Enter the Receiver: ");
                    String friendReceiverName = sc.nextLine();
                    friendReceiverName = friendReceiverName.strip();

                    /* Check if sender is valid */
                    UniversityStudent friendReceiver = null;
                    friendReceiver = UniversityStudent.getStudentFromString(friendReceiverName, students);
                    while (friendReceiver == null){
                        System.out.println("Student not found.");
                        System.out.print("Enter the Receiver: ");
                        friendReceiverName = sc.nextLine();
                        friendReceiver = UniversityStudent.getStudentFromString(friendReceiverName, students);
                    }

                    /* Send friend request */
                    FriendRequestThread friendRequestThread = new FriendRequestThread(friendSender, friendReceiver);
                    friendRequestThread.run();
                case "m":
                    /* Chat message */
                    /* Get sender */
                    System.out.print("Enter the Sender: ");
                    String messageSenderName = sc.nextLine();
                    messageSenderName = messageSenderName.strip();

                    /* Check if sender is valid */
                    UniversityStudent messageSender = null;
                    messageSender = UniversityStudent.getStudentFromString(messageSenderName, students);
                    while (messageSender == null){
                        System.out.println("Student not found.");
                        System.out.print("Enter the Sender: ");
                        messageSenderName = sc.nextLine();
                        messageSender = UniversityStudent.getStudentFromString(messageSenderName, students);
                    }

                    /* Get Receiver */
                    System.out.print("Enter the Receiver: ");
                    String messageReceiverName = sc.nextLine();
                    messageReceiverName = messageReceiverName.strip();

                    /* Check if sender is valid */
                    UniversityStudent messageReceiver = null;
                    messageReceiver = UniversityStudent.getStudentFromString(messageReceiverName, students);
                    while (messageReceiver == null){
                        System.out.println("Student not found.");
                        System.out.print("Enter the Receiver: ");
                        messageReceiverName = sc.nextLine();
                        messageReceiver = UniversityStudent.getStudentFromString(messageReceiverName, students);
                    }

                    /* Get message */
                    System.out.print("Enter the Message: ");
                    String message = sc.nextLine().strip();

                    /* Send chat message */
                    ChatThread chatThread = new ChatThread(messageSender, messageReceiver, message);
                    chatThread.run();
                    break;
            }
        }
    }
}