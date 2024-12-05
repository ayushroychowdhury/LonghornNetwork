package program;

import java.util.ArrayList;
import java.util.List;

/**
 * program.ChatManager class is responsible for handling chat messages between students.
 */
public class ChatManager {

    private static List<ChatHistoryEntry> chatHistory = new ArrayList<ChatHistoryEntry>();
    /**
     * Handles a chat message between two students
     * @param sender the student who sent the message
     * @param receiver the student who received the message
     * @param message the message content
     */
    public static void handleChatMessage(UniversityStudent sender, UniversityStudent receiver, String message) {
        /* Check if students are valid */
        if (sender == null || receiver == null) {
            System.out.println("Invalid chat message.");
            return;
        }

        /* You can't send a message to yourself */
        if (sender.equals(receiver)) {
            System.out.println("You cannot send a chat message to yourself.");
            return;
        }

        /* Check if students are friends */
        if (!FriendManager.areFriends(sender, receiver)) {
            System.out.println(sender.getName() + " is not friends with " + receiver.getName() + ".");
            return;
        }

        /* Send chat message */
        System.out.println(sender.getName() + " sent a chat message to " + receiver.getName() + ": " + message);
        addHistory(sender, receiver, message);
    }

    /**
     * Adds a chat message to the chat history
     * @param message the message to add to the chat history
     */
    private synchronized static void addHistory(UniversityStudent sender, UniversityStudent receiver, String message) {
        chatHistory.add(new ChatHistoryEntry(sender, receiver, message));
    }

    /**
     * Gets the chat history
     * @return the chat history
     */
    public static List<ChatHistoryEntry> getChatHistory() {
        return chatHistory;
    }

    /**
     * Clears the chat history
     */
    public static void clear() {
        chatHistory.clear();
    }
}
