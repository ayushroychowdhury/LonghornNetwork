/**
 * Simulates a friend request operation.
 */
public class FriendRequestThread implements Runnable {
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
        synchronized (this) {
            System.out.println(sender.name + " sent a friend request to " + receiver.name);
        }
    }
}

