import java.util.*;

/**
 * Represents a generic student with basic information and behaviors.
 * Designed to be extended by specific types of students.
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
     * Calculates the connection strength between this student and another student.
     *
     * @param other the other student
     * @return an integer representing the connection strength
     */
    public abstract int calculateConnectionStrength(Student other);
}
