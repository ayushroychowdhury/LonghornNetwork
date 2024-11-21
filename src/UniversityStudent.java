import java.util.*;

/**
 * Represents a University Student with additional properties such as friends and chat history
 */
public class UniversityStudent extends Student {
    private String roommate;
    private Set<String> friends;

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
}

