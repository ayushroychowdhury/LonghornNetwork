public class FriendRequestThread implements Runnable {
    UniversityStudent sender;
    UniversityStudent receiver;
    /**
     * Constructor for FriendRequestThread
     * @param sender sender of the friend request
     * @param receiver receiver of the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Method signature for run
     */
    @Override
    public void run() {
        // Method signature only
        updateFriendRequestHistory();
    }

    /**
     * Updates the friend request history of the sender and receiver
     */
    private synchronized void updateFriendRequestHistory() {
        // Updates the friend request history of the sender and receiver
        Main.friends.get(sender).add(receiver);
        Main.chatHistory.add(sender.getName() + " sent a friend request to " + receiver.getName());
    }
}
