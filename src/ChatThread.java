import java.util.*;

/**
 * Represents a chat thread between two university students.
 */
public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;
    private static Map<UniversityStudent, List<String>> chatHistory = new HashMap<>();

    /**
     * Constructs a ChatThread with the specified sender, receiver, and message.
     *
     * @param sender   the sender of the message
     * @param receiver the receiver of the message
     * @param message  the message to be sent
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Runs the chat thread.
     */
    @Override
    public void run() {
        synchronized (chatHistory) {
            chatHistory.putIfAbsent(sender, new ArrayList<>());
            chatHistory.putIfAbsent(receiver, new ArrayList<>());

            chatHistory.get(sender).add("To " + receiver.getName() + ": " + message);
            chatHistory.get(receiver).add("From " + sender.getName() + ": " + message);
        }
    }

    /**
     * Retrieves the chat history for a given student.
     *
     * @param student the student whose chat history is to be retrieved
     * @return a list of messages for the specified student
     */
    public static List<String> getChatHistory(UniversityStudent student) {
        return chatHistory.getOrDefault(student, Collections.emptyList());
    }
}
