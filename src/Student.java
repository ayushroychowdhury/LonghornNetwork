import java.util.Objects;

/**
 * Abstract class representing a student.
 */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;

    public Student(String name, int age, String gender, int year, String major, double gpa) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
    }

    /**
     * Calculates the connection strength between this student and another student.
     *
     * @param other The other student to compare with.
     * @return The calculated connection strength.
     */
    public abstract int calculateConnectionStrength(Student other);

    public String getName() {
        return name;
    }

    // Additional getters and setters can be added as needed.

    @Override
    public String toString() {
        return name;
    }

    // Ensure uniqueness based on name, as names are unique.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}