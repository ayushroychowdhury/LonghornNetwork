
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the input file name as a command-line argument.");
            return;
        }
        String inputFile = args[2];
        try {
            List<UniversityStudent> students = DataParser.parseStudents(inputFile);

            // Roommate matching
            GaleShapley.assignRoommates(students);

            // Pod formation
            StudentGraph graph = StudentGraph.buildStudentGraph(students);
            PodFormation podFormation = new PodFormation(graph);
            podFormation.formPods(4);

            // Referral path finding
            ReferralPathFinder pathFinder = new ReferralPathFinder(graph);
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Issac"), "FindMe");
            pathFinder.findReferralPath(graph.getStudent("Timmy"), "IDontExist");

            ThreadManager.configureLogger();
            ThreadManager interactionManager = new ThreadManager(10);
            Map<String, UniversityStudent> studentMap = makeMap(students);

            interactionManager.sendFriendRequest(
                    studentMap.get("Timmy"),
                    studentMap.get("Issac")
            );
            interactionManager.sendFriendRequest(
                    studentMap.get("Jimmy"),
                    studentMap.get("Simon")
            );
            interactionManager.sendFriendRequest(
                    studentMap.get("Issac"),
                    studentMap.get("Jimmy")
            );


            interactionManager.sendMessage(
                    studentMap.get("Timmy"),
                    studentMap.get("Issac"),
                    "Hi Isaac!"
            );
            interactionManager.sendMessage(
                    studentMap.get("Issac"),
                    studentMap.get("Timmy"),
                    "Hey Timmy!"
            );
            interactionManager.sendMessage(
                    studentMap.get("Jimmy"),
                    studentMap.get("Simon"),
                    "Hello Simon!"
            );
            interactionManager.sendMessage(
                    studentMap.get("Simon"),
                    studentMap.get("Jimmy"),
                    "Hi Jimmy!"
            );
            interactionManager.sendMessage(
                    studentMap.get("Whale"),
                    studentMap.get("Timmy"),
                    "Hi Timmy!"
            );

            interactionManager.shutdown();

            displayChatHistories(studentMap.values());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates map of students using thier name as a key
     *
     * @param studentList List of students to create map from
     */
    public static Map<String, UniversityStudent> makeMap (List<UniversityStudent> studentList){

        Map<String, UniversityStudent> studentsMap = new HashMap<>();
        for (UniversityStudent student : studentList) {
            if (student.getName() != null && !student.getName().isEmpty()) {
                studentsMap.put(student.getName(), student);
            } else {
                System.err.println("Skipping student with no name");
            }
        }
        return studentsMap;
    }

    /**
     * Displays the chat histories between students.
     *
     * @param students students to display histories for.
     */
    private static void displayChatHistories(Collection<UniversityStudent> students) {
        for (UniversityStudent student : students) {
            System.out.println("\nChat History for " + student.getName() + ":");
            for (UniversityStudent friend : students) {
                if (friend.getName().equals(student.getName())) {
                    continue;
                }
                List<String> chats = student.getChatHistory(friend.getName());
                if (chats.size() != 0 ) {
                    System.out.println("With " + friend.getName() + ":");
                    for (String chat : chats) {
                        System.out.println("  " + chat);
                    }
                }

            }
        }
    }

}
