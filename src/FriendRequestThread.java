import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reference for a friend request
 * @author Yahir Lopez
 */
public class FriendRequestThread implements Runnable {

    /**
     * Static Map to store who are friends
     */
    private static Map<UniversityStudent, List<UniversityStudent>> friends = new HashMap<>();
    private static final Lock lock = new ReentrantLock();

    private UniversityStudent sender;
    private UniversityStudent receiver;

    /**
     * Construct a FriendRequestThread
     * @param sender UniversityStudent
     * @param receiver UniversityStudent
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Main method for FriendRequestThread to run.
     */
    @Override
    public void run() {
        if (areFriends(sender, receiver)) {
            System.out.println(sender.getName() + " already added " + receiver.getName());
            return;
        }

        lock.lock();
        if (!friends.containsKey(sender)) {
            friends.put(sender, new ArrayList<>());
        }
        friends.get(sender).add(receiver);
        lock.unlock();

        System.out.println(sender.getName() + " added " + receiver.getName() + " to their friends list");
    }

    /**
     * Find out if UniversityStudent Objects are friends
     * @param a UniversityStudent
     * @param b UniversityStudent
     * @return boolean
     */
    static public boolean areFriends(UniversityStudent a, UniversityStudent b) {
        if (friends.containsKey(a)) {
            for (UniversityStudent s : friends.get(a)) {
                if (s.equals(b)) {
                    return true;
                }
            }
        }
        return false;
    }

    static public List<UniversityStudent> getFriends(UniversityStudent sender) {
        if (friends.containsKey(sender)) {
            return friends.get(sender);
        } else {
            return null;
        }
    }

}
