import java.util.List;

/**
 * Represents a student.
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
     * @return the connection strength between the two students
     */
    public abstract int calculateConnectionStrength(Student other);
}