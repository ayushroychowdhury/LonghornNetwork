import java.util.*;

/**
 * Represents a Student
 * @author Yahir Lopez
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
     * Calculate the connection strength between Student objects
     * @param other Student
     * @return connection strength, 0 = low/ no connection, etc.
     */
    public abstract int calculateConnectionStrength(Student other);
}
