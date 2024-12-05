/**
 * Represents a thread for handling friend requests between two students.
 */
public class FriendRequestThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;

    /**
     * Constructs a FriendRequestThread with a sender and receiver.
     *
     * @param sender   The student sending the friend request.
     * @param receiver The student receiving the friend request.
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Executes the friend request logic when the thread is run.
     */
    @Override
    public void run() {
        UniversityStudent first, second;
        if (sender.getName().compareTo(receiver.getName()) < 0) {
            first = sender;
            second = receiver;
        } else {
            first = receiver;
            second = sender;
        }

        synchronized (first) {
            synchronized (second) {
                sender.addFriend(receiver);
                receiver.addFriend(sender);
                System.out.println(sender.getName() + " and " + receiver.getName() + " are now friends.");
            }
        }
    }
}