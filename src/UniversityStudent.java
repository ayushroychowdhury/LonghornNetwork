import java.util.*;
// TODO: Constructor and additional methods to be implemented
/**
 * Represents a university student.
 */
public class UniversityStudent extends Student {
    /**
     * Constructs a UniversityStudent with the specified attributes.
     *
     * @param name                 the name of the student
     * @param age                  the age of the student
     * @param gender               the gender of the student
     * @param year                 the year of the student
     * @param major                the major of the student
     * @param gpa                  the GPA of the student
     * @param roommatePreferences  the roommate preferences of the student
     * @param previousInternships  the previous internships of the student
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
     * Calculates the connection strength between this student and another student.
     *
     * @param other the other student
     * @return the connection strength between the two students
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        int strength = 0;
        if (this.major.equals(other.major)) {
            strength += 10;
        }
        if (this.year == other.year) {
            strength += 5;
        }
        if (this.gender.equals(other.gender)) {
            strength += 3;
        }
        return strength;
    }

    // Additional methods can be implemented here
}

