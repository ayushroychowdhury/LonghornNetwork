import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reference for a friend request
 * @author Yahir Lopez
 */
public class FriendRequestThread implements Runnable {

    /**
     * Static Map to store who are friends
     */
    private static Map<UniversityStudent, List<UniversityStudent>> friends = new HashMap<>();

    /**
     * Construct a FriendRequestThread
     * @param sender UniversityStudent
     * @param receiver UniversityStudent
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
    }

    /**
     * Main method for FriendRequestThread to run.
     */
    @Override
    public void run() {
        // Method signature only
    }

    /**
     * Find out if UniversityStudent Objects are friends
     * @param a UniversityStudent
     * @param b UniversityStudent
     * @return boolean
     */
    static public boolean areFriends(UniversityStudent a, UniversityStudent b) {
        for (UniversityStudent s : friends.get(a)) {
            if (s.equals(b)) {
                return true;
            }
        }
        return false;
    }
}
