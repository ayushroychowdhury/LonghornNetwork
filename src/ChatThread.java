import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reference for a Chat
 * @author Yahir Lopez
 */
public class ChatThread implements Runnable {

    /**
     * Map to store Chat History
     */
    private static Map<ChatPair, Chat> chatHistory = new HashMap<ChatPair, Chat>();
    private ChatPair chatPair;
    private String message;
    private static final Lock lock = new ReentrantLock();


    /**
     * Construct a ChatThread
     * @param sender UniversityStudent
     * @param receiver UniversityStudent
     * @param message Message to send
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.chatPair = new ChatPair(sender, receiver);
        this.message = message;
    }

    /**
     * Main method for ChatThread to run.
     */
    @Override
    public void run() {
        if (!FriendRequestThread.areFriends(chatPair.sender, chatPair.receiver)) {
            System.out.println(chatPair.sender.getName() + " is not friends with " + chatPair.receiver.getName());
            return;
        }

        lock.lock();

        if (!chatHistory.containsKey(chatPair)) {
            chatHistory.put(chatPair, new Chat());
        }
        chatHistory.get(chatPair).addMessage(message);

        lock.unlock();

        System.out.println(chatPair.sender.getName() + " sent [" + message + "]" + " to "+chatPair.receiver.getName());

    }

    public static List<String> getChatHistory(UniversityStudent sender, UniversityStudent receiver) {
        lock.lock();

        List<String> r = new ArrayList<>();

        for (ChatPair chatpair : chatHistory.keySet()) {
            if (chatpair.sender.equals(sender) && chatpair.receiver.equals(receiver)) {
                Chat chatMessages = chatHistory.get(chatpair);
                for (String message : chatMessages.chatMessages) {
                    r.add(message);
                }
            }
        }

        lock.unlock();

        return r;
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
         * UniversityStudent who received messages
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
