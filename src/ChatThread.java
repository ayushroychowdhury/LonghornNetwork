/**
 * A chat thread between 2 university students.
 * This handles sending a message from the sender to the receiver.
 */
public class ChatThread implements Runnable {
    private final UniversityStudent sender;
    private final UniversityStudent receiver;
    private final String message;
    /**
     * a chat thread with a sender, receiver, and a message.
     * @param sender the student sending the message
     * @param receiver the student receiving the message
     * @param message the message
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * runs the thread to handle the message.
     * executes the logic for sending the message.
     */
    @Override
    public void run() {
        // Method signature only
        //????????
    }
}
