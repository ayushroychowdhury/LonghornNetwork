/**
 * FriendRequestThread class that implements Runnable interface and manages threads for friend requests
 */
public class FriendRequestThread implements Runnable {

    private UniversityStudent sender;
    private UniversityStudent receiver;

    /**
     * Constructor for a FriendRequestThread, which represents a friend request from one student to another
     * @param sender The student who sent the friend request
     * @param receiver The student who received the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Run method for the FriendRequestThread, which starts the thread and sends the friend request from the sender to the receiver
     */
    @Override
    public void run() {
        FriendManager.handleFriendRequest(sender, receiver);
    }
}