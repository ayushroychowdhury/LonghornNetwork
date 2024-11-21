/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

 import java.util.*;

 public class Chat {
     private UniversityStudent student1;
     private UniversityStudent student2;
     private ArrayList<String> messages;
 
     /**
      * Constructor for Chat class.
      * @param student1 The first student in the chat
      * @param student2 The second student in the chat
      */
     public Chat(UniversityStudent student1, UniversityStudent student2) {
         this.student1 = student1;
         this.student2 = student2;
         this.messages = new ArrayList<>();
     }
 
     /**
      * Adds a message to the chat.
      * @param message The message to add
      */
     public void addMessage(String message) {
         messages.add(message);
     }
 }