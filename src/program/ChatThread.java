package program;

/**
 * program.ChatThread class that implements the Runnable interface and manages threads for chat messages
 */
public class ChatThread implements Runnable {

    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    /**
     * Constructor for a program.ChatThread, which represents a chat message between two students
     * @param sender the student who sent the message
     * @param receiver the student who received the message
     * @param message the message content
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Run method for the program.ChatThread, which starts the thread and sends the message from the sender to the receiver
     */
    @Override
    public void run() {
        ChatManager.handleChatMessage(sender, receiver, message);
    }
}