/**
 * Represents a thread for handling chat interactions between two students.
 */
public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    /**
     * Constructs a ChatThread with a sender, receiver, and message.
     *
     * @param sender   The student sending the message.
     * @param receiver The student receiving the message.
     * @param message  The content of the message.
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Executes the chat logic when the thread is run.
     */
    @Override
    public void run() {
        UniversityStudent first, second;
        if (sender.getName().compareTo(receiver.getName()) < 0) {
            first = sender;
            second = receiver;
        } else {
            first = receiver;
            second = sender;
        }

        synchronized (first) {
            synchronized (second) {
                sender.sendMessage(receiver, message);
                System.out.println(sender.getName() + " sent a message to " + receiver.getName() + ": " + message);
            }
        }
    }
}