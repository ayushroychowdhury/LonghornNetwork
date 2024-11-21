import java.util.*;

/**
 * Represents a student with basic properties
 */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;

    /**
     * calculates the connection strength between two students
     * @param other student to calculate connection strength with
     * @return connection strength between two students
     */
    public abstract int calculateConnectionStrength(Student other);

    /**
     * Constructor for Student
     * @param name name of the student
     * @param age age of the student
     * @param gender gender of the student
     * @param year year of the student
     * @param major major of the student
     * @param gpa gpa of the student
     * @param roommatePreferences list of roommate preferences
     * @param previousInternships list of previous internships
     */
    public Student(String name, int age, String gender, int year, String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
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
