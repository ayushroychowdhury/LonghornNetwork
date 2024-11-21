import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reference for a Chat
 * @author Yahir Lopez
 */
public class ChatThread implements Runnable {

    /**
     * Map to store Chat History
     */
    Map<ChatPair, Chat> chatHistory = new HashMap<ChatPair, Chat>();

    /**
     * Construct a ChatThread
     * @param sender UniversityStudent
     * @param receiver UniversityStudent
     * @param message Message to send
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
    }

    /**
     * Main method for ChatThread to run.
     */
    @Override
    public void run() {
        // Method signature only
    }

    /**
     * Reference for a Pair of UniversityStudents who have a chat history
     */
    private class ChatPair {
        /**
         * UniversityStudent who sent messages
         */
        private UniversityStudent sender;
        /**
         * UniversityStudent who recieved messages
         */
        private UniversityStudent receiver;

        /**
         * Construct a ChatPair
         * @param sender UniversityStudent
         * @param receiver UniversityStudent
         */
        public ChatPair(UniversityStudent sender, UniversityStudent receiver) {
            this.sender = sender;
            this.receiver = receiver;
        }
    }

    /**
     * Reference for a chat History between a ChatPair
     */
    private class Chat {
        /**
         * History of messages in Chat
         */
        private List<String> chatMessages;

        /**
         * Construct a Chat
         */
        public Chat() {
            this.chatMessages = new ArrayList<String>();
        }

        /**
         * Add a message to the Chat
         * @param message String
         */
        public void addMessage(String message) {
            chatMessages.add(message);
        }
    }
}
