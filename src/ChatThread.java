/**
 * Simulates a chat operation.
 */
public class ChatThread implements Runnable {
    private final UniversityStudent sender;
    private final UniversityStudent receiver;
    private final String message;

    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public void run() {
        // Method signature only
        synchronized (this) {
            System.out.println(sender.name + " to " + receiver.name + ": " + message);
        }
    }
}

