/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ChatThread class that implements Runnable. Allows for sending messages between two students.
 */
public class ChatThread implements Runnable {
    // Lock for synchronization
    private final ReentrantLock lock = new ReentrantLock();

    // Chat details
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    /**
     * Constructor for ChatThread class.
     * @param sender The sender of the message
     * @param receiver The receiver of the message
     * @param message The message content
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Sends the message from the sender to the receiver.
     */
    @Override
    public void run() {
        // Send message
            if (sender.equals(receiver)) {
                return;
            }
            // Lock to prevent chats from being modified by multiple threads or duplicate chats being created
            lock.lock();
            try {
                // Check if chat already exists between sender and receiver, if so, send message
                for (Chat chat : sender.getChats()) {
                    if (chat.contains(sender, receiver)) {
                        chat.addMessage(message);
                        return;
                    }
                }

                // Create new chat between sender and receiver and add message
                Chat newChat = new Chat(sender, receiver);
                // Chats static, only one chat between two students must be created
                sender.addChat(newChat);
                newChat.addMessage(message);
            } finally {
                lock.unlock();
            }
        }

}


