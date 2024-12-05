/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */


import java.util.concurrent.locks.ReentrantLock;

/**
 * FriendRequestThread class that implements Runnable. Allows for sending friend requests between two students.
 */
public class FriendRequestThread implements Runnable {

    private final ReentrantLock lock = new ReentrantLock();
    private UniversityStudent sender;
    private UniversityStudent receiver;

    /**
     * Constructor for FriendRequestThread class.
     * @param sender The sender of the friend request
     * @param receiver The receiver of the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
        if (sender.equals(receiver)) {
            return;
        }
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Sends a friend request from the sender to the receiver.
     */
    @Override
    public void run() {
        // Method signature only
        // sender.requestFriend(receiver);
        lock.lock();
        System.out.println(sender.getName() + " sent a friend request to " + receiver.getName());
        try{
            sender.requestFriend(receiver);
        } finally {
            lock.unlock();
        }
    }
}
