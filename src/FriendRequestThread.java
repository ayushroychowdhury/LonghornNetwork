/**
 * Enables a student to send friend requests to another student
 */
public class FriendRequestThread implements Runnable {
    private UniversityStudent sender, receiver;
    /**
     * Constructor
     * @param sender student
     * @param receiver student
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    /**
     * Sends a friend request
     */
    public void run() {
        System.out.println("Friend request sent by " + this.sender.name + " to " + this.receiver.name);
        becomeFriends();
    }

    private synchronized void becomeFriends() {
        sender.friends.add(receiver.name);
        receiver.friends.add(sender.name);
    }
}
