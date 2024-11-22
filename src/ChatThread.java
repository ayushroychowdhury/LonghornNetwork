import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class to simulate messages between two students
 */
public class ChatThread implements Runnable {

    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;
    private static final Lock lock = new ReentrantLock(); // Lock to ensure thread-safe chat history update

    /**
     * Constructor for ChatThread
     * @param sender student sending the message
     * @param receiver student receiving the message
     * @param message message to be sent
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public void run() {
        lock.lock(); // Acquire the lock to ensure thread-safe access to chat history

        try {
            // Add message to both sender and receiver's chat history
            System.out.println(sender.getName() + " sent a message to " + receiver.getName() + ": " + message);
            // Assuming a shared chat history or separate histories
            sender.addChatHistory(receiver.getName(), message);
            receiver.addChatHistory(sender.getName(), message);
        } finally {
            lock.unlock(); // Release the lock
        }
    }
}
