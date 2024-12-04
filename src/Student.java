import java.util.*;

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
     * Constructor for the Student class.
     * @param other The student to compare to.
     * @return The connection strength between the two students.
     */
    public abstract int calculateConnectionStrength(Student other);
}
