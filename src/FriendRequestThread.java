

/**
 * This the thread that will handle friend requests. We will spawn the thread
 * when a user attempts to add someone as their friend. Then it should run in the background to achieve 
 * this functionality.
 */

public class FriendRequestThread implements Runnable {
    UniversityStudent sender;
    UniversityStudent receiver;
    int response;

    private synchronized void sendRequest(){
        receiver.addRequest(sender);
    }

    private synchronized void respond(){
        if (response == 1){
            receiver.addFriend(sender.name);
        } else {
            receiver.declineRequest(sender);
        }
    }

    /**
     * This the constructor for the thread where we input the student who is sending the 
     * friend request and the student they are sending it to.
     * @param sender The student sending the friend request
     * @param receiver The student receiving the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver, int response) {
        // Constructor
        this.sender = sender;
        this.receiver = receiver;
        this.response = response;
    }


    /**
     * This is the run function which will actually launch the thread itself. When we are 
     * ready call this function to start the friend request action
     */
    @Override
    public void run() {
        // Method signature only
        if (response == 0){
            sendRequest();
        } else {
            respond();
        }

    }
}
