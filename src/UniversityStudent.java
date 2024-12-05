import java.util.*;
// TODO: Constructor and additional methods to be implemented
/**
 * Represents a university student.
 */
public class UniversityStudent extends Student {
    /**
     * Constructs a UniversityStudent with the specified attributes.
     *
     * @param name                 the name of the student
     * @param age                  the age of the student
     * @param gender               the gender of the student
     * @param year                 the year of the student
     * @param major                the major of the student
     * @param gpa                  the GPA of the student
     * @param roommatePreferences  the roommate preferences of the student
     * @param previousInternships  the previous internships of the student
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences;
        this.previousInternships = previousInternships;
    }

    /**
     * Calculates the connection strength between this student and another student.
     *
     * @param other the other student
     * @return the connection strength between the two students
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        int connectionStrength = 0;

        if (GaleShapley.areRoommates(this, (UniversityStudent) other)) {
            connectionStrength += 5;
        }
        for (String pastIntern : this.previousInternships) {
            if (other.previousInternships.contains(pastIntern)) {
                connectionStrength += 4;
            }
        }
        if (this.major.equals(other.major)) {
            connectionStrength += 3;
        }
        if (this.age == other.age) {
            connectionStrength += 2;
        }

        if (connectionStrength == 0) {
            return Integer.MIN_VALUE;
        }

        return connectionStrength;
    }


    /**
     * Returns the name of the student.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the age of the student.
     *
     * @return the age of the student
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the gender of the student.
     *
     * @return the gender of the student
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the year of the student.
     *
     * @return the year of the student
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the major of the student.
     *
     * @return the major of the student
     */
    public String getMajor() {
        return major;
    }

    /**
     * Returns the GPA of the student.
     *
     * @return the GPA of the student
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Returns the roommate preferences of the student.
     *
     * @return the roommate preferences
     */
    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }

    /**
     * Returns the previous internships of the student.
     *
     * @return the previous internships
     */
    public List<String> getPreviousInternships() {
        return previousInternships;
    }



    // Additional methods can be implemented here

}

