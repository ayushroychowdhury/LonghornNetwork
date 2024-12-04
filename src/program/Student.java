package program;

import java.util.*;

import static program.UniversityStudent.getStudentFromString;

/**
 * A class that represents a student and stores information about that student
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
     * Calculates the connection strength between this student and another student based on parameters
     * @param other The other student with which the connection strength is calculated
     * @return The connection strength as integer
     */
    public abstract int calculateConnectionStrength(Student other);

    /**
     * Gets the name of the student
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the age of the student
     * @return the age of the student
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the major of the student
     * @return the major of the student
     */
    public String getMajor() {
        return major;
    }

    /**
     * Gets the GPA of the student
     * @return the GPA of the student
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Gets the previous internships of the student
     * @return the previous internships of the student in a list
     */
    public List<String> getPreviousInternships() {
        return previousInternships;
    }

    /**
     * Checks if one student is equal to another student
     * @return true if the students are equal, false otherwise (2 students are equal if they have the same name)
     */
    public boolean equals(Student other) {
        return this.getName().equals(other.getName());
    }

    /**
     * Gets the age of the student
     * @return the age of the student
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the roommate preferences of the student
     * @return List of strings containing the roommate preferences of the student
     */
    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }


    /**
     * Represents a student as a string
     * @return a string representation of the student
     */
    public String toString() {
        String representation = "";
        representation += "Name: " + name + "\n";
        representation += "Age: " + age + "\n";
        representation += "Gender:" + gender + "\n";
        representation += "Year: " + year + "\n";
        representation += "Major: " + major + "\n";
        representation += "GPA: " + gpa + "\n";
        representation += "Roommate Preferences: " + roommatePreferences + "\n";
        representation += "Previous Internships: " + previousInternships + "\n";
        return representation;
    }

    /**
     * Gets the year of the student
     * @return the year of the student
     */
    public int getYear() {
        return year;
    }
}