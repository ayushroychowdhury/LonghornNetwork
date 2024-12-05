
import java.util.*;

/** com.studentgraph.model.Student class */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;

    public abstract int calculateConnectionStrength(Student other);
}
