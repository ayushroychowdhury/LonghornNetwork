import java.util.*;

/**
 * Represents a University Student with additional properties such as friends and chat history
 */
public class UniversityStudent extends Student {
    private String roommate;
    private Set<String> friends;
    private Set<ChatPair> chatHistory;

    /**
     * Creates a UniversityStudent object with the given properties
     * @param name name of student
     * @param age age of student
     * @param gender gender of student
     * @param year what year the student is
     * @param major what major the student is
     * @param gpa what the student's GPA is
     * @param roommatePreferences ranking of roommate preferences
     * @param previousInternships past internships
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
        super(name, age, gender, year, major, gpa, roommatePreferences, previousInternships);
        friends = new HashSet<>();
        chatHistory = new HashSet<>();
    }

    /**
     * Calculate connection strength between two students
     * @param other student to calculate connection strength with
     * @return connection strength
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        if (!(other instanceof UniversityStudent otherStudent)) {
            return 0; // Return 0 if the other student is not a UniversityStudent
        }

        int connectionStrength = 0;

        // Roommate: Add 5 if they are roommates
        if (this.roommate != null && this.roommate.equals(otherStudent.name)) {
            connectionStrength += 5;
        }

        // Shared Internships: Add 4 for each shared internship
        for (String internship : this.previousInternships) {
            if (otherStudent.previousInternships.contains(internship)) {
                connectionStrength += 4;
            }
        }

        // Same Major: Add 3 if they share the same major
        if (this.major.equals(otherStudent.major)) {
            connectionStrength += 3;
        }

        // Same Age: Add 2 if they are the same age
        if (this.age == otherStudent.age) {
            connectionStrength += 2;
        }

        return connectionStrength;
    }

    /**
     * Override equals to compare UniversityStudent objects based on the name field
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // Ensure same class
        UniversityStudent that = (UniversityStudent) obj;
        return Objects.equals(name, that.name); // Compare by 'name'
    }

    /**
     * Override hashCode to generate hash based on the name field
     */
    @Override
    public int hashCode() {
        return Objects.hash(name); // Use 'name' to compute the hash
    }

    /**
     * @return name of student
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return age of student
     */
    public int getAge(){
        return this.age;
    }

    /**
     * @return gender of student
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * @return year of student
     */
    public int getYear(){
        return this.year;
    }

    /**
     * @return major of student
     */
    public String getMajor(){
        return this.major;
    }

    /**
     * @return gpa of student
     */
    public double getGPA(){
        return this.gpa;
    }

    /**
     * @return student's ranked
     */
    public List<String> getRoommatePreferences(){
        return this.roommatePreferences;
    }

    /**
     * @return student's previous internships
     */
    public List<String> getPreviousInternships(){
        return this.previousInternships;
    }

    /**
     * @return get roommate
     */
    public String getRoommate() {
        return roommate;
    }

    /**
     * set roommate
     */
    public void setRoommate(String roommate) {
        this.roommate = roommate;
    }

    /**
     * Adds friend to the student's friend list
     * @param friend friend to be added
     */
    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Get list of friends
     * @return friends of student
     */
    public Set<String> getFriends() {
        return friends;
    }

    /**
     * Adds a message to the chat history with the specified friend
     * @param friend the friend to whom the message is sent
     * @param message the message to be added
     */
    public void addChatHistory(String friend, String message) {
        ChatPair pair = new ChatPair(this.getName(), friend);
        pair.addMessage(message);
        chatHistory.add(pair);
    }

    /**
     * Gets the chat history with a particular friend
     * @param friend the friend whose chat history is requested
     * @return list of messages with that friend
     */
    public List<String> getChatHistory(String friend) {
        for (ChatPair pair : chatHistory) {
            if (pair.contains(this.getName(), friend)) {
                return pair.getMessages();
            }
        }
        return new ArrayList<>(); // No messages if no history found
    }

    // Other getters and methods...

    /**
     * Inner class to store the chat history between two students
     */
    private static class ChatPair {
        private String student1;
        private String student2;
        private List<String> messages;

        public ChatPair(String student1, String student2) {
            if (student1.compareTo(student2) < 0) {
                this.student1 = student1;
                this.student2 = student2;
            } else {
                this.student1 = student2;
                this.student2 = student1;
            }
            this.messages = new ArrayList<>();
        }

        public void addMessage(String message) {
            messages.add(message);
        }

        public List<String> getMessages() {
            return messages;
        }

        public boolean contains(String student1, String student2) {
            return (this.student1.equals(student1) && this.student2.equals(student2)) ||
                    (this.student1.equals(student2) && this.student2.equals(student1));
        }
    }
}

