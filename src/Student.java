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
    protected String roommate = "";
    protected Set<String> friends;
    protected Map<String, List<String>> chatLog;

    /**
     * Returns the strength of the connection between two students
     * @param other Student
     * @return integer value of connection strength
     */
    public abstract int calculateConnectionStrength(Student other);
}
