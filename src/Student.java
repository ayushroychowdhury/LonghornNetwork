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
     * Returns the strength of the connection between two students
     * @param other Student
     * @return integer value of connection strength
     */
    public abstract int calculateConnectionStrength(Student other);
}
