import java.util.HashSet;

/**
 * Simulates a chat message sent from one student to another using a thread
 */
public class ChatThread implements Runnable {
    private final UniversityStudent sender;
    private final UniversityStudent receiver;
    private final String message;

    /**
     * Chat thread constructor.
     * @param sender The sender student
     * @param receiver The receiver student
     * @param message The message that is being sent
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * What to do when the thread is run
     */
    @Override
    public void run() {
        // TODO
        System.out.println(sender.name + " says to " + receiver.name + ": " + message);

        updateInteractions();
    }

    /**
     * Thread-safe method to update map of interactions
     */
    private synchronized void updateInteractions() {
        if (!Main.interacted.containsKey(sender.name)) Main.interacted.put(sender.name, new HashSet<String>());
        if (!Main.interacted.containsKey(receiver.name)) Main.interacted.put(receiver.name, new HashSet<String>());
        Main.interacted.get(sender.name).add(receiver.name);
        Main.interacted.get(receiver.name).add(sender.name);
        Main.chatHistory.add(sender.name + " says to " + receiver.name + ": " + message);
    }
}
