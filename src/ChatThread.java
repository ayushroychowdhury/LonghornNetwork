/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

/**
 * ChatThread class that implements Runnable. Allows for sending messages between two students.
 */
public class ChatThread implements Runnable {
    /**
     * Constructor for ChatThread class.
     * @param sender The sender of the message
     * @param receiver The receiver of the message
     * @param message The message content
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
    }

    /**
     * Sends the message from the sender to the receiver.
     */
    @Override
    public void run() {
        // Method signature only
        sender.sendMessage(receiver, message);
    }
}

