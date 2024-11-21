

/**
 * This the thread that will handle friend requests. We will spawn the thread
 * when a user attempts to add someone as their friend. Then it should run in the background to achieve 
 * this functionality.
 */

public class FriendRequestThread implements Runnable {

    /**
     * This the constructor for the thread where we input the student who is sending the 
     * friend request and the student they are sending it to.
     * @param sender The student sending the friend request
     * @param receiver The student receiving the friend request
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor
    }


    /**
     * This is the run function which will actually launch the thread itself. When we are 
     * ready call this function to start the friend request action
     */
    @Override
    public void run() {
        // Method signature only
    }
}
