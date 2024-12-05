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
     // List of friends
     private ArrayList<UniversityStudent> friends;
        // roommate
     private UniversityStudent roommate;
     // List of all chats (security can be implemented later s.t. only friends can access chats)
     static private ArrayList<Chat> chats = new ArrayList<>();

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
         // Add all connection strengths
         int connectionStrength = 0;
         if (this.roommate.equals(other)){
                connectionStrength += 5;
         }
         for (String internship : this.previousInternships) {
             if (other.previousInternships.contains(internship)) {
                 connectionStrength += 4;
             }
         }
        if (this.major.equals(other.major)){
            connectionStrength += 3;
        }
        if (this.age == other.age) {
            connectionStrength += 2;
        }
        return connectionStrength;

     }

     /**
      * Returns the name of the student.
      * @return The name of the student
      */
        public String getName() {
            return name;
        }


     /**
      * Sets roommate of student
      * @param roommate The roommate to set
      */
     public void setRoommate(UniversityStudent roommate) {
         this.roommate = roommate;
     }

    /**
    * Returns the roommate of this student.
    * @return The roommate of this student
    */
    public UniversityStudent getRoommate() {
        return roommate;
    }


     /**
      * Returns the list of roommate preferences for this student.
      * @return The list of preferred roommates
      */
        public List<UniversityStudent> getRoommatePreferences(List<UniversityStudent> students) {
            // Create list of UniversityStudent objects from list of names
            List<UniversityStudent> roommatePreferences = new ArrayList<>();
            for (String name : this.roommatePreferences) {
                // For each student in list of students, add to roommatePreferences if name matches
                for (UniversityStudent student : students) {
                    // If name matches, add to roommatePreferences
                    if (student.getName().equals(name)) {
                        roommatePreferences.add(student);
                    }
                }
            }
            return roommatePreferences;
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
         // If friend is already in friends list, return
         if (friends.contains(friend)) {
             return;
         }
         friends.add(friend);
         friend.addFriend(this);
     }
 
 
     /**
      * Creates a new chat between this student and another student if it does not already exist.
      * @param other The other student
      */
     public void createChat(UniversityStudent other) {
            // Create chat, and add to chats
         Chat newChat = new Chat(this, other);
         chats.add(newChat);
     }
 
     /**
      * Adds a message to the chat between this student and another student.
      * @param other The other student
      * @param message The message to add
      */
     public void sendMessage(UniversityStudent other, String message) {
         return;
     }

     /**
      * Returns the list of chats of this student.
      */
        public ArrayList<Chat> getChats() {
            return chats;
        }



     /**
      * Adds a chat to the list of chats of this student.
      */
        public void addChat(Chat chat) {
            chats.add(chat);
        }


     /**
      * Prints the Student data
        */
        public void printStudent() {
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Year: " + year);
            System.out.println("Major: " + major);
            System.out.println("GPA: " + gpa);
            System.out.println("Roommate Preferences: " + roommatePreferences);
            System.out.println("Previous Internships: " + previousInternships);
            System.out.println("Roommate: " + roommate.getName());
            System.out.print("Friends: ");
            for (UniversityStudent friend : friends) {
                System.out.print(friend.getName() + " ");
            }
            System.out.println();

        }

     /**
      * Returns all chats
      */
     public void printChats() {
         System.out.println("Chats: ");
         for (Chat chat : chats) {
             System.out.println(chat.getStudents().get(0).getName() + " " + chat.getStudents().get(1).getName());
             System.out.println(chat.getMessages());
         }
     }

 }
 
 