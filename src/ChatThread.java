public class ChatThread implements Runnable {
    UniversityStudent sender;
    UniversityStudent receiver;
    String message;
    /**
     * Constructor for ChatThread
     * @param sender
     * @param receiver
     * @param message
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Method signature for run
     */
    @Override
    public void run() {
        // Method signature only
        updateChatHistory();
    }

    /**
     * Updates the chat history of the sender and receiver
     */
    private synchronized void updateChatHistory() {
        // Updates the chat history of the sender and receiver
        Main.chatHistory.add(sender.name + " sent a message to " + receiver.name + ": " + message);
    }
}
