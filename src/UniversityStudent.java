/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */



 import java.util.*;
 /**
  * UniversityStudent class that extends Student. Represents a university student with additional methods.
  */
 public class UniversityStudent extends Student {
     // TODO: Constructor and additional methods to be implemented
     private ArrayList<UniversityStudent> friends;
     static private ArrayList<Chat> chats;
     private ArrayList<Integer> myChats;

     /**
      * Constructor for UniversityStudent class.
      * @param name The name of the student
      * @param age The age of the student (must be positive int)
      * @param gender The gender of the student
      * @param year The year of study of the student
      * @param major The major of the student
      * @param gpa The GPA of the student (must be between 0.0 and 4.0)
      * @param roommatePreferences The list of student names that this student prefers as roommates
      * @param previousInternships The list of companies that this student has interned at
      */
     public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                              List<String> roommatePreferences, List<String> previousInternships) {
         this.name = name;
         this.age = age;
         this.gender = gender;
         this.year = year;
         this.major = major;
         this.gpa = gpa;
         this.roommatePreferences = roommatePreferences;
         this.previousInternships = previousInternships;
         this.friends = new ArrayList<>();
     }
 
 
     /**
      * Calculates the connection strength between this student and another student based on their attributes.
      * @param other The other student
      * @return The connection strength integer value
      */
     @Override
     public int calculateConnectionStrength(Student other) {
         // TODO: Implement this method
     }
 
     /**
      * Adds a friend to this student's friends list.
      * @param friend The friend to add
      */
     public void addFriend(UniversityStudent friend) {
         friends.add(friend);
     }
 
     /**
      * Sends a friend request to another student and adds them to each other's friends list.
      */
     public void requestFriend(UniversityStudent friend) {
         friends.add(friend);
         friend.addFriend(this);
     }
 
 
     /**
      * Creates a new chat between this student and another student if it does not already exist.
      * @param other The other student
      */
     public void createChat(UniversityStudent other) {
         Chat newChat = new Chat(this, other);
         chats.add(newChat);
     }
 
     /**
      * Adds a message to the chat between this student and another student.
      * @param other The other student
      * @param message The message to add
      */
     public void sendMessage(UniversityStudent other, String message) {
     }
 
 }
 
 