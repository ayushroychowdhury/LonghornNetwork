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
     * Calculates the connection strength between this student and another student.
     *
     * @param other the other {@link Student}.
     * @return an integer representing the connection strength.
     */
    public abstract int calculateConnectionStrength(Student other);
}
