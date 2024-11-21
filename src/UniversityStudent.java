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

    /**
     * Getter for UniversityStudent Object's name
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for UniversityStudent Object's age
     * @return Age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Getter for UniversityStudent Object's Gender
     * @return Gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Getter for UniversityStudent Object's year
     * @return Year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Getter for UniversityStudent Object's Major
     * @return Major
     */
    public String getMajor() {
        return this.major;
    }

    /**
     * Getter for UniversityStudent Object's GPA
     * @return GPA
     */
    public double getGPA() {
        return this.gpa;
    }

    /**
     * Getter for UniversityStudent Object's roommate preferences
     * @return RoommatePreferences List
     */
    public List<String> getRoommatePreferences() {
        return this.roommatePreferences;
    }

    /**
     * Getter for UniversityStudent Object's previous internships
     * @return PreviousInternships List
     */
    public List<String> getPreviousInternships() {
        return this.previousInternships;
    }
}

