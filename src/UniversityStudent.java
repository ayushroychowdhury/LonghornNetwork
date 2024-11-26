import java.util.*;

public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented

    String roommate;

    /**
     * Constructor for the UniversityStudent class.
     * @param name
     * @param age
     * @param gender
     * @param year
     * @param major
     * @param gpa
     * @param roommatePreferences
     * @param previousInternships
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences;
        this.previousInternships = previousInternships;
        roommate = "";
    }

    /**
     * Calculates the connection strength between two UniversityStudents.
     * @param other
     * @return
     */
    public int calculateConnectionStrength(Student other) {
        if (other instanceof UniversityStudent) {
            UniversityStudent otherStudent = (UniversityStudent) other;
            int connectionStrength = 0;
            
            return connectionStrength;
        }
        return 0;
    }

    /**
     * Returns the name of the roommate.
     * @return
     */
    public String getRoommate() {
        return roommate;
    }

    /**
     * Sets the name of the roommate.
     * @param roommate
     */
    public void setRoommate(String roommate) {
        this.roommate = roommate;
    }

    public String toString() {
        String result = "Name: " + name + "\n";
        result += "Age: " + age + "\n";
        result += "Gender: " + gender + "\n";
        result += "Year: " + year + "\n";
        result += "Major: " + major + "\n";
        result += "GPA: " + gpa + "\n";
        result += "Roommate Preferences: " + roommatePreferences + "\n";
        result += "Previous Internships: " + previousInternships + "\n";
        return result;
    }
}

