import java.util.*;


public class MessagePair {
    String message;
    UniversityStudent sendStu;

    MessagePair(){
        message = null;
        sendStu = null;
    }

    MessagePair(String message, UniversityStudent send){
        this.message = message;
        this.sendStu = send;
    }

    public void changePair(String message, UniversityStudent send){
        this.message = message;
        this.sendStu = send;
    }
}
