
/**
 * This is the class that handles the thread responsible for the chat system that the project will have.
 * This means that what I intend to happen related to the chat system needs to be done in this class.
 */

public class ChatThread implements Runnable {
    UniversityStudent sender;
    UniversityStudent receiver;
    String message;

    private synchronized void sendMessage(){
        sender.addSentMessage(message,receiver);
        receiver.addReceiveMessage(message, sender);
    }

    /**
     * This is the constructor for creating the chat thread itself. It takes the two students that we 
     * are handling the communication between and the message that we intend to send between them
     * @param sender The student who will be sending the message
     * @param receiver The student who will be receiving the message
     * @param message The message itself
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }


    /**
     * This is the run function which actually activates the thread itself
     */
    @Override
    public void run() {
        // Method signature only
        sendMessage();
    }
}
