import java.util.logging.Logger;

/**
 * Simulates sending friend requests
 */
public class FriendRequestThread implements Runnable {
    private static final Logger logger = Logger.getLogger(FriendRequestThread.class.getName());
    private final UniversityStudent sender;
    private final UniversityStudent receiver;

    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        // Method signature only
        sender.addFriend(receiver);
        receiver.addFriend(sender);
        logger.info(sender.getName() + " sent a friend request to " + receiver.getName());
    }
}
