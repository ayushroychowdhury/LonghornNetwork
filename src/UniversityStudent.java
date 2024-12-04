import java.util.*;

/**
 * Represents a specific type of student with attributes and behaviors tailored
 * to university students.
 */
public class UniversityStudent extends Student {
    private Map<UniversityStudent, List<String>> chatHistory = new HashMap<>();
    private List<UniversityStudent> friends = new ArrayList<>();

    /**
     * Constructor to initialize a UniversityStudent with all necessary attributes.
     *
     * @param name                 the name of the student
     * @param age                  the age of the student
     * @param gender               the gender of the student
     * @param year                 the academic year of the student
     * @param major                the major of the student
     * @param gpa                  the GPA of the student
     * @param roommatePreferences  the list of roommate preferences
     * @param previousInternships  the list of previous internships
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                             List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences != null ? new ArrayList<>(roommatePreferences) : new ArrayList<>();
        this.previousInternships = previousInternships != null ? new ArrayList<>(previousInternships) : new ArrayList<>();
    }

    /**
     * Calculates the connection strength between this student and another student.
     *
     * The connection strength is based on:
     * 1. shared major 20
     * 2. shared internship 10
     * 3. roommate preference 50 if the other student is in the preference list
     * 4. GPA similarity 10 if within 0.5
     *
     * @param other the other student
     * @return an integer representing the connection strength
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        if (!(other instanceof UniversityStudent)) {
            return 0;
        }

        UniversityStudent otherStudent = (UniversityStudent) other;
        int connectionStrength = 0;

        if (this.major.equalsIgnoreCase(otherStudent.major)) {
            connectionStrength += 20;
        }

        for (String internship : this.previousInternships) {
            if (otherStudent.previousInternships.contains(internship)) {
                connectionStrength += 10;
            }
        }

        if (this.roommatePreferences.contains(otherStudent.name)) {
            connectionStrength += 50;
        }

        if (Math.abs(this.gpa - otherStudent.gpa) <= 0.5) {
            connectionStrength += 10;
        }

        return connectionStrength;
    }

    /**
     * Returns a string representation of the UniversityStudent.
     *
     * @return a string containing the student's details.
     */
    @Override
    public String toString() {
        return "UniversityStudent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                ", gpa=" + gpa +
                ", roommatePreferences=" + roommatePreferences +
                ", previousInternships=" + previousInternships +
                '}';
    }

    /**
     * this method receives a message from the sender and store the message in the corresponding attribute
     * @param sender the sender
     * @param message the message
     */
    public void receiveMessage(UniversityStudent sender, String message) {
        if (sender == null || message == null || message.isEmpty()) {
            System.out.println("Invalid message or sender.");
            return;
        }
        System.out.println("Message received from " + sender.name + ": " + message);
        chatHistory.computeIfAbsent(sender, k -> new ArrayList<>()).add(message);
    }

    /**
     * add friend
     * @param friend the friend
     */
    public void addFriend(UniversityStudent friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    /**
     * check if they are friends
     * @param friend friend
     * @return friend check result
     */
    public boolean isFriend(UniversityStudent friend) {
        return friends.contains(friend);
    }

    /**
     * gets message history
     * @param sender
     * @return message
     */
    public List<String> getChatHistoryWith(UniversityStudent sender) {
        return chatHistory.getOrDefault(sender, Collections.emptyList());
    }


}
