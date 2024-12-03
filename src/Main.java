import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class
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
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Issac"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "IDontExist");

            ExecutorService service = Executors.newSingleThreadExecutor();
            for (UniversityStudent student : graph.getStudents()) {
                for (UniversityStudent student1 : graph.getStudents()) {
                    //service.execute(new FriendRequestThread(student, student1));
                    //service.execute(new FriendRequestThread(student1, student));
//                    service.execute(new ChatThread(student, student1, "HELLO!"));
                }
            }
            service.shutdown();
//            try {
//                service.awaitTermination(1, TimeUnit.MINUTES);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            ExecutorService service2 = Executors.newSingleThreadExecutor();
//
//            for (UniversityStudent student : graph.getStudents()) {
//                for (UniversityStudent student1 : graph.getStudents()) {
//                    if (FriendRequestThread.areFriends(student, student1)) {
//                        System.out.println(student.getName() + " is friends with " + student1.getName());
//                        service2.execute(new ChatThread(student, student1, "HELLO!"));
//                    } else {
//                        System.out.println(student.getName() + " is NOT friends with " + student1.getName());
//                    }
//                }
//            }
//
//            service2.shutdown();
//            try {
//                service2.awaitTermination(1, TimeUnit.MINUTES);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            MainGUI gui = new MainGUI(graph);
            gui.show();


//            service.shutdown();
//            try {
//                service.awaitTermination(1, TimeUnit.MINUTES);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
