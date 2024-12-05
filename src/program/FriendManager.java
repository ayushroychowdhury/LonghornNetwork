package program;

import java.util.*;

/**
 * program.FriendManager is a utility class that manages friend requests and friendships between students
 */
public class FriendManager {
    private static List<Set<Student>> friends = new ArrayList<Set<Student>>();
    private static List<String> friendRequestsHistory = new ArrayList<String>();

    public static void handleFriendRequest(UniversityStudent sender, UniversityStudent receiver) {
        if (sender == null || receiver == null) {
            System.out.println("Invalid friend request.");
            return;
        }

        if (sender.equals(receiver)) {
            System.out.println("You cannot send a friend request to yourself.");
            return;
        }

        if (getFriends(sender).contains(receiver)) {
            System.out.println(sender.getName() + " is already friends with " + receiver.getName() + ".");
            return;
        }

        /* Notify about friend request and add request to history */
        System.out.println(sender.getName() + " sent a friend request to " + receiver.getName() + ".");
        friendRequestsHistory.add(sender.getName() + " requests " + receiver.getName() + " to be friends.");

        /* Ask receiver if he wants to be friends with the sender */
        askReceiver(sender, receiver);
    }

    /**
     * Asks receiver of friend request if they want to accept it
     * @param sender program.Student sending the friend request
     * @param receiver program.Student receiving the friend request
     */
    private synchronized static void askReceiver(UniversityStudent sender, UniversityStudent receiver) {
        System.out.print("Does " + receiver.getName() + " want to accept the friend request? (y/n): ");
        Scanner sc = new Scanner(System.in);
        String response = sc.nextLine();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.print("Invalid input. Does " + receiver.getName() + " want to accept the friend request? (y/n): ");
            response = sc.nextLine();
        }
        if (response.equals("y")) {
            System.out.println(receiver.getName() + " accepted the friend request from " + sender.getName() + ".");
            friendRequestsHistory.add(receiver.getName() + " accepted the friend request from " + sender.getName() + ".");
            addFriend(sender, receiver);
        } else {
            System.out.println(receiver.getName() + " declined the friend request from " + sender.getName() + ".");
            friendRequestsHistory.add(receiver.getName() + " declined the friend request from " + sender.getName() + ".");
        }
    }

    /**
     * Adds a friend to the friend list
     * @param student1 First student
     * @param student2 Second student
     */
    public synchronized static void addFriend(Student student1, Student student2) {
        /* Don't add friend set if they are already friends */
        for (Set<Student> friendGroup : friends) {
            if (friendGroup.contains(student1) && friendGroup.contains(student2)) {
                return;
            }
        }

        /* Add friend set to list */
        Set<Student> newFriendGroup = new HashSet<Student>();
        newFriendGroup.add(student1);
        newFriendGroup.add(student2);
        friends.add(newFriendGroup);
    }

    /**
     * Finds all friends of a student
     * @param student program.Student whose friends are to be found
     * @return List of friends of the student
     */
    public static List<UniversityStudent> getFriends(UniversityStudent student) {
        List<UniversityStudent> studentFriends = new ArrayList<UniversityStudent>();
        for (Set<Student> friendGroup : friends) {
            if (friendGroup.contains(student)) {
                for (Student friend : friendGroup) {
                    if (!friend.equals(student)) {
                        studentFriends.add((UniversityStudent) friend);
                    }
                }
            }
        }
        return studentFriends;
    }

    /**
     * Checks if two students are friends
     * @param student1 First student
     * @param student2 Second student
     * @return True if the students are friends, false otherwise
     */
    public static boolean areFriends(UniversityStudent student1, UniversityStudent student2) {
        for (Set<Student> friendGroup : friends) {
            if (friendGroup.contains(student1) && friendGroup.contains(student2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds friends between two students
     * @param firstStudent Name of the first student
     * @param secondStudent Name of the second student
     */
    public static void addFriends(String firstStudent, String secondStudent) {
        UniversityStudent student1 = null;
        UniversityStudent student2 = null;

        List<UniversityStudent> students = DataParser.getStudents();
        if (students == null) {
            return;
        }

        for (UniversityStudent student : DataParser.getStudents()) {
            if (student.getName().equals(firstStudent)) {
                student1 = student;
            }
            if (student.getName().equals(secondStudent)) {
                student2 = student;
            }
        }
        if (student1 == null || student2 == null) {
            throw new IllegalArgumentException("Invalid students");
        }
        addFriend(student1, student2);
    }


    public static List<Set<Student>> getFriendSets(){
        return friends;
    }

    /**
     * Clears the friend manager
     */
    public static void clear() {
        friends.clear();
        friendRequestsHistory.clear();
    }
}
