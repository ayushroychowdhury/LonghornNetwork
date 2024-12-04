import java.util.*;

/**
 * An extension of the student class. Basically just a special student. Will have to
 * flesh out the details later.
 */
public class UniversityStudent extends Student {
    private class messagePair {
        String message;
        UniversityStudent sendStu;

        messagePair(){
            message = null;
            sendStu = null;
        }

        messagePair(String message, UniversityStudent send){
            this.message = message;
            this.sendStu = send;
        }

        public void changePair(String message, UniversityStudent send){
            this.message = message;
            this.sendStu = send;
        }
    }


    // TODO: Constructor and additional methods to be implemented
    private UniversityStudent roommate = null;
    private ArrayList<UniversityStudent> podMembers = new ArrayList<UniversityStudent>();
    private ArrayList<UniversityStudent> friends = new ArrayList<UniversityStudent>();
    private ArrayList<UniversityStudent> requests = new ArrayList<UniversityStudent>();
    private LinkedList<messagePair> messageSent = new LinkedList<messagePair>();
    private LinkedList<messagePair> messageReceived = new LinkedList<messagePair>();
    
    /**
     * This is the defined method that will calculate the strength between two students takes 
     * a student as input to compare against and returns how strong thier connection is.
     */
    public void setRoommate(UniversityStudent newRoommate){
        roommate = newRoommate;
    }

    public String getRoommateName(){
        if (roommate == null){
            return "";
        } else {
            return roommate.name;
        }
    }

    public UniversityStudent getRoommate(){
        return roommate;
    }

    public void unmatch(){
        roommate = null;
    }

    public void addPodMember(UniversityStudent student){
        podMembers.add(student);
    }

    public int calculateConnectionStrength(Student other){
        int strength = 0;
        if (roommate == other){
            strength += 5;
        }
        for (int i = 0; i < this.previousInternships.size(); ++i){
            for (int j = 0; j < other.previousInternships.size(); ++j){
                if (this.previousInternships.get(i).equals(other.previousInternships.get(j))){
                    strength += 4;
                }
            }
        }
        if (this.major.equals(other.major)){
            strength += 3;
        }
        if (this.age == other.age){
            strength += 2;
        }
        return strength;
    }

    public void addFriend(String name){
        for (UniversityStudent friend : requests){
            if (friend.name == name){
                System.out.println("\nFriend request sent from " + name + " to " + this.name + " accepted");
                friends.add(friend);
                requests.remove(friend);
                return;
            }
        }
        System.out.println("\nUnable to find friend request");
    }

    public void declineRequest(UniversityStudent student){
        for (UniversityStudent req : requests){
            if (req.name == student.name){
                System.out.println("\nFriend request sent from " + student.name + " to " + this.name + " declined");
                requests.remove(req);
                return;
            }
        }
        System.out.println("\nUnable to find friend request");
    }

    public void addRequest(UniversityStudent student){
        requests.add(student);
        System.out.println("\nFriend request sent from " + student.name + " to " + this.name);
    }

    public void addSentMessage(String mess, UniversityStudent receive){
        messageSent.add(new messagePair(mess,receive));
        System.out.println("\n" + this.name + " sent a message to " + receive.name);
    }

    public void addReceiveMessage(String mess, UniversityStudent sent){
        messageReceived.add(new messagePair(mess,sent));
        System.out.println("\n" + this.name + " received a message from " + sent.name);
    }
}

