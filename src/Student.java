import java.util.*;

/**
 * This is a class that holds all of the data of a student such as name, age, ect.
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
     * to be defined in the child class. Will calculate the strength of connection between two different students
     * @param other The other student
     * @return How strong their connection is
     */
    public abstract int calculateConnectionStrength(Student other);
}
