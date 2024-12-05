import java.util.logging.Logger;

/**
 * Simulates chatting between students
 */
public class ChatThread implements Runnable {
    private static final Logger logger = Logger.getLogger(ChatThread.class.getName());
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
        sender.sendMessage(receiver, message);
        logger.info(sender.getName() + " sent a message to " + receiver.getName() + ": " + message);

    }
}
