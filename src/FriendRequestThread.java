/**
 * a thread to handle sending a friend request between two university students.
 */
public class FriendRequestThread implements Runnable {
    private final UniversityStudent sender;
    private final UniversityStudent receiver;
    /**
     * make a FriendRequestThread with a sender and a receiver.
     *
     * @param sender   the student sending the friend request
     * @param receiver the student receiving the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Runs the thread to handle the friend request.
     * Executes the logic for sending the request.
     */
    @Override
    public void run() {
        synchronized (receiver) {
            System.out.println(sender.name + " sent a friend request to " + receiver.name);

            if (receiver.isFriend(sender)) {
                System.out.println(receiver.name + " and " + sender.name + " are already friends. Cannot add again.");
            } else {
                receiver.addFriend(sender);
                sender.addFriend(receiver);
                System.out.println(receiver.name + " accepted the friend request from " + sender.name);
            }
        }
    }
}
