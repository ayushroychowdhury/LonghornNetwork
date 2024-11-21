import java.util.*;

public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented
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
    }

    /**
     * Calculates the connection strength between two UniversityStudents.
     * @param other
     * @return
     */
    public int calculateConnectionStrength(Student other) {
        return -1;
    }
}

