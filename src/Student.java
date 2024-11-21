import java.util.*;

/**
 * The abstract class for the Student. Defines several fields and an abstract method.
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
    protected String roommate;


    /**
     * @param other the other student to calculate the connection strength to
     * @return the connection strength between the two students
     */
    public abstract int calculateConnectionStrength(Student other);
}
