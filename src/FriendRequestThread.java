import java.util.*;

/**
 * Represents a thread for handling friend requests between university students.
 */
public class FriendRequestThread implements Runnable {
    private static Map<UniversityStudent, List<UniversityStudent>> friends = new HashMap<>();
    private UniversityStudent sender;
    private UniversityStudent receiver;

    /**
     * Constructs a FriendRequestThread with the specified sender and receiver.
     *
     * @param sender   the sender of the friend request
     * @param receiver the receiver of the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Runs the friend request thread.
     */
    @Override
    public void run() {
        synchronized (friends) {
            friends.putIfAbsent(sender, new ArrayList<>());
            friends.putIfAbsent(receiver, new ArrayList<>());

            if (!friends.get(sender).contains(receiver)) {
                friends.get(sender).add(receiver);
            }

            if (!friends.get(receiver).contains(sender)) {
                friends.get(receiver).add(sender);
            }
        }
    }

    /**
     * Checks if two students are friends.
     *
     * @param a the first student
     * @param b the second student
     * @return true if the students are friends, false otherwise
     */
    public static boolean areFriends(UniversityStudent a, UniversityStudent b) {
        return friends.getOrDefault(a, Collections.emptyList()).contains(b);
    }
}