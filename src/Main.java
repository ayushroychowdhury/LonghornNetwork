/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */



import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String inputFile = "C:\\Users\\stefa\\LonghornNetwork\\testing\\input_sample.txt";

        try {
            // Parse data from input file
            ArrayList<UniversityStudent> students = DataParser.parseStudents(inputFile);

            // Roommate matching
            GaleShapley.assignRoommates(students);

            // Print roommate pairs
//            for (UniversityStudent student : students) {
//                student.printStudent();
//                System.out.print(student.getName() + " " + student.getRoommate().getName() + ": ");
//                System.out.println(student.calculateConnectionStrength(student.getRoommate()));
//            }


            // Create graph of students for use in pod formation and referral path finding
            StudentGraph graph = new StudentGraph(students);
            System.out.println("Initial graph: ");
            graph.printGraph();
            System.out.println();

            // Form pods
            PodFormation podFormation = new PodFormation(graph, students);
            podFormation.generateMST();

            // Print initial MST
            System.out.println("MST: ");
            podFormation.printMST();
            System.out.println();

            // Form pods of size 3 and print new MST
            podFormation.formPods(3);
            System.out.println("MST w/ pods: ");
            podFormation.printMST();
            System.out.println();


            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph, students);


            ArrayList<UniversityStudent> path = pathFinder.findReferralPath(students.get(14), "Amazon");
            System.out.println();
            System.out.println("Referral path for student: ");
            for (UniversityStudent student : path) {
                System.out.print(student.getName() + ", ");
            }
            System.out.println();
            System.out.println();


            // Friend request simulation
            // Send friend requests to some students
            for (int i = 0; i < 3; i++) {
                for (int j = i+1; j < 3; j++) {
                    System.out.println("Friend request from " + students.get(i).getName() + " to " + students.get(j).getName());
                    FriendRequestThread thread = new FriendRequestThread(students.get(i), students.get(j));
                    thread.run();
                }
            }

            // Chat simulation

            for (int i = 0; i < 4; i++) {
                ChatThread thread = new ChatThread(students.get(i), students.get(i+1), "Hello Dude");
                ChatThread thread2 = new ChatThread(students.get(i+1), students.get(i), "Hello Back");
                thread.run();
                thread2.run();
            }
            for (int i = 0; i < 4; i++) {
                ChatThread thread = new ChatThread(students.get(i), students.get(i+1), "Hello Dude again");
                ChatThread thread2 = new ChatThread(students.get(i+1), students.get(i), "Hello Back");
                thread.run();
                thread2.run();
            }


            System.out.println();
            students.get(0).printChats();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
