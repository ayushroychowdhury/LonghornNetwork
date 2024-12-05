import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents a university student with specific attributes and methods.
 */
public class UniversityStudent extends Student {
    private List<String> roommatePreferences;
    private List<String> previousInternships;
    private Student roommate;
    private Set<UniversityStudent> friendList;
    private ConcurrentMap<UniversityStudent, List<String>> chatHistory;
    private int podID;

    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                             List<String> roommatePreferences, List<String> previousInternships) {
        super(name, age, gender, year, major, gpa);
        this.roommatePreferences = roommatePreferences != null ? roommatePreferences : new ArrayList<>();
        this.previousInternships = previousInternships != null ? previousInternships : new ArrayList<>();
        this.friendList = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.chatHistory = new ConcurrentHashMap<>();
        this.podID = -1; // Indicates no pod assigned yet.
    }

    // Getter methods for inherited fields
    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public int getYear() {
        return this.year;
    }

    public String getMajor() {
        return this.major;
    }

    public double getGpa() {
        return this.gpa;
    }

    @Override
    public int calculateConnectionStrength(Student other) {
        int strength = 0;
        if (!(other instanceof UniversityStudent)) {
            return strength;
        }
        UniversityStudent otherStudent = (UniversityStudent) other;

        // Roommate bonus
        if (this.roommate != null && this.roommate.equals(otherStudent)) {
            strength += 5;
        }

        // Shared internships
        Set<String> sharedInternships = new HashSet<>(this.previousInternships);
        sharedInternships.retainAll(otherStudent.previousInternships);
        strength += 4 * sharedInternships.size();

        // Same major
        if (this.major.equalsIgnoreCase(otherStudent.major)) {
            strength += 3;
        }

        // Same age
        if (this.age == otherStudent.age) {
            strength += 2;
        }

        return strength;
    }

    // Getter and setter for roommate preferences
    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }

    public void setRoommatePreferences(List<String> roommatePreferences) {
        this.roommatePreferences = roommatePreferences != null ? roommatePreferences : new ArrayList<>();
    }

    // Getter and setter for previous internships
    public List<String> getPreviousInternships() {
        return previousInternships;
    }

    public void setPreviousInternships(List<String> previousInternships) {
        this.previousInternships = previousInternships != null ? previousInternships : new ArrayList<>();
    }

    // Roommate management
    public Student getRoommate() {
        return roommate;
    }

    public void setRoommate(Student roommate) {
        this.roommate = roommate;
    }

    // Friend management methods
    public void addFriend(UniversityStudent friend) {
        friendList.add(friend);
    }

    public Set<UniversityStudent> getFriendList() {
        return friendList;
    }

    // Chat management methods
    public void sendMessage(UniversityStudent recipient, String message) {
        chatHistory.computeIfAbsent(recipient, k -> Collections.synchronizedList(new ArrayList<>())).add("To " + recipient.getName() + ": " + message);
        recipient.receiveMessage(this, message);
    }

    public void receiveMessage(UniversityStudent sender, String message) {
        chatHistory.computeIfAbsent(sender, k -> Collections.synchronizedList(new ArrayList<>())).add("From " + sender.getName() + ": " + message);
    }

    public Map<UniversityStudent, List<String>> getChatHistory() {
        return chatHistory;
    }

    // Pod ID management
    public int getPodID() {
        return podID;
    }

    public void setPodID(int podID) {
        this.podID = podID;
    }

    @Override
    public String toString() {
        return name;
    }
}