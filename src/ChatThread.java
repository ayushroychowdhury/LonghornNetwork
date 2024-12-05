
/**
 * Creates a chat thread between two students, enabling them to talk with eachother
 */
public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;
    /**
     * Constructor
     * @param sender
     * @param receiver
     * @param message
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    /**
     * Establishes a chat connection
     */
    public void run() {
        sendMessage();
    }

    private synchronized void sendMessage() {
        String output = "Message from " + sender.name + " to " + receiver.name + ": " + message;
        Main.chatLog.add(output);
        sender.chatLog.get(receiver.name).add(sender.name + ": " + message);
        receiver.chatLog.get(sender.name).add(sender.name + ": " + message);
        System.out.println(output);
    }
}
