/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

 import java.util.*;

    /**
    * Chat class that represents a chat between two students.
    */
 public class Chat {
     // Student 1 and Student 2 in the chat
     private UniversityStudent student1;
     private UniversityStudent student2;
        // Messages in the chat
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

     /**
      * Check if two students are in a chat
      */
        public boolean contains(UniversityStudent student1, UniversityStudent student2) {
            return (this.student1.equals(student1) && this.student2.equals(student2)) || (this.student1.equals(student2) && this.student2.equals(student1));
        }

     /**
      * Get the messages of a chat
      */
        public ArrayList<String> getMessages() {
            return messages;
        }

     /**
      * Get students in the chat
      */
        public ArrayList<UniversityStudent> getStudents() {
            return new ArrayList<UniversityStudent>(Arrays.asList(student1, student2));
        }

 }