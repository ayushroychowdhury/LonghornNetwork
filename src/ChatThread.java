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
        handleChat(sender, receiver, message);
    }

    /**
     * a handle chat method to be called in run() method.
     * @param sender the sender
     * @param receiver the receiver
     * @param message the message
     */
    private void handleChat(UniversityStudent sender, UniversityStudent receiver, String message) {
        synchronized (receiver) {
            try {
                System.out.println(sender.name + " sends a message to " + receiver.name + ": " + message);
                receiver.receiveMessage(sender, message);
            } catch (Exception e) {
                System.err.println("Error handling chat between " + sender.name + " and " + receiver.name + ": " + e.getMessage());
            }
        }
    }

}
