
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Extension of Student class
 */
public class UniversityStudent extends Student {
    private UniversityStudent roommate;
    private final List<UniversityStudent> friends;
    private final Map<String, List<String>> chatHistory;

    /**
     * Constructor for UniversityStudent
     */
    public UniversityStudent() {
        super();
        roommate = null;
        this.friends = new CopyOnWriteArrayList<>();
        this.chatHistory = new ConcurrentHashMap<>();
    }

    /**
     * Output int connection strength between this universityStudent and com.studentgraph.model.UniversityStudent s1
     *
     * @param s
     * @return int connectionstrength
     */
    @Override
    public int calculateConnectionStrength(Student s) {
        if (!(s instanceof UniversityStudent)) {
            return 0;
        }

        UniversityStudent otherStudent = (UniversityStudent) s;
        int connectionStrength = 0;

        if (this.getRoommate() != null && this.getRoommate().equals(otherStudent)) {
            connectionStrength += 5;
        }

        if (this.getInternships() != null && otherStudent.getInternships() != null) {
            for (String internship : this.getInternships()) {
                if (otherStudent.getInternships().contains(internship)) {
                    connectionStrength += 4;
                }
            }
        }

        if (this.getMajor() != null && this.getMajor().equals(otherStudent.getMajor())) {
            connectionStrength += 3;
        }

        if (this.getAge() == otherStudent.getAge()) {
            connectionStrength += 2;
        }

        return connectionStrength;
    }

    public String getMajor() {
        return major;
    }

    public List<String> getInternships() {
        return previousInternships;
    }

    public UniversityStudent getRoommate() {
        return roommate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public void setGender(String g) {
        this.gender = g;
    }

    public void setYear(int i) {
        this.year = i;
    }

    public void setMajor(String m) {
        this.major = m;
    }

    public void setGpa(double g) {
        this.gpa = g;
    }

    public void setRoommatePreferences(List<String> strings) {
        this.roommatePreferences = strings;
    }

    public void setPreviousInternships(List<String> strings) {
        this.previousInternships = strings;
    }

    public String getName() {
        return name;
    }

    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return name + "\n" + age + "\n" + gender + "\n" + year + "\n" + major + "\n" + gpa;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UniversityStudent that = (UniversityStudent) obj;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setRoommate(UniversityStudent roomie) {
        roommate = roomie;
    }

    public String getGender() {
        return gender;
    }

    public int getYear() {
        return year;
    }

    /**
     * Adds a friend to the student's friend list
     *
     * @param student The student added as a friend.
     */
    public void addFriend(UniversityStudent student) {
        if (student == null || student == this || friends.contains(student)) {
            return;
        }
        friends.add(student);
    }

    /**
     * Sends a message to another student and updates chat history
     *
     * @param receiver The student receiving the message.
     * @param message  The message content.
     */
    public void sendMessage(UniversityStudent receiver, String message) {
        if (receiver == null || message == null || receiver == this) {
            return;
        }
        chatHistory.computeIfAbsent(receiver.getName(), k -> new CopyOnWriteArrayList<>()).add("Me: " + message);
        receiver.receiveMessage(this, message);
    }

    /**
     * Receives a message from another student, updating the chat history.
     *
     * @param sender  The student who sent the message.
     * @param message The message content.
     */
    public void receiveMessage(UniversityStudent sender, String message) {
        if (sender == null || message == null || sender == this) {
            return;
        }

        chatHistory.computeIfAbsent(sender.getName(), k -> new CopyOnWriteArrayList<>()).add(sender.getName() + ": " + message);
    }

    /**
     * Retrieves the chat history with a specific friend.
     *
     * @param friendName The name of the friend.
     * @return A list of messages exchanged with the friend.
     */
    public List<String> getChatHistory(String friendName) {
        return chatHistory.getOrDefault(friendName, new ArrayList<>());
    }

    /**
     * Retrieves the list of friends.
     *
     * @return A list of friends.
     */
    public List<UniversityStudent> getFriends() {
        return new ArrayList<>(friends);
    }


}


