package program;

/**
 * ChatHistoryEntry is a class that represents a chat history entry between two students
 */
public class ChatHistoryEntry {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    /**
     * Creates a new chat history entry
     * @param sender the student who sent the message
     * @param receiver the student who received the message
     * @param message the message content
     */
    public ChatHistoryEntry(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Gets the sender of the chat message
     * @return the sender of the chat message
     */
    public UniversityStudent getSender() {
        return sender;
    }

    /**
     * Gets the receiver of the chat message
     * @return the receiver of the chat message
     */
    public UniversityStudent getReceiver() {
        return receiver;
    }

    /**
     * Gets the message content
     * @return the message content
     */
    public String getMessage() {
        return message;
    }
}
