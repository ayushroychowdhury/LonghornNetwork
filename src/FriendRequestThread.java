import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FriendRequestThread implements Runnable {

    private UniversityStudent sender;
    private UniversityStudent receiver;
    private static final Lock lock = new ReentrantLock(); // To ensure thread-safe modifications to friend lists

    /**
     * Constructor for FriendRequestThread
     * @param sender person sending the friend request
     * @param receiver person receiving the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        lock.lock(); // Acquire the lock to ensure thread-safe access to shared resources

        try {
            // Send friend request: Add each student to the other's friend list
            if (!sender.getFriends().contains(receiver.getName())) {
                sender.addFriend(receiver.getName());
            }
            if (!receiver.getFriends().contains(sender.getName())) {
                receiver.addFriend(sender.getName());
            }
        } finally {
            lock.unlock(); // Release the lock
        }
    }
}
