import java.util.*;

/**
 * Represents a University Student
 */
public class UniversityStudent extends Student {

    /**
     * Calculate the strength between another Student object
     * @param other Student
     * @return connection strength between another Student object, the higher the value the more connection.
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        return 0;
    }
    // TODO: Constructor and additional methods to be implemented

    /**
     * Construct a UniversityStudent object
     * @param name String
     * @param age String
     * @param gender String
     * @param year int
     * @param major String
     * @param gpa double
     * @param roommatePreferences  List of String
     * @param previousInternships  List of String
     */
    public UniversityStudent(String name, int age, String gender, int year,
                             String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences;
        this.previousInternships = previousInternships;
    }
}

