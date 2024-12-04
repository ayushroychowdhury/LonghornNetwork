import java.util.*;

public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented

    String roommate;

    /**
     * Constructor for the UniversityStudent class.
     * @param name name of the student
     * @param age age of the student
     * @param gender gender of the student
     * @param year year of the student
     * @param major major of the student
     * @param gpa gpa of the student
     * @param roommatePreferences roommate preferences of the student
     * @param previousInternships previous internships of the student
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
        roommate = null;
    }

    /**
     * Calculates the connection strength between two UniversityStudents.
     * @param other student to compare with
     * @return connection strength
     */
    public int calculateConnectionStrength(Student other) {
        if (other instanceof UniversityStudent) {
            UniversityStudent otherStudent = (UniversityStudent) other;
            int connectionStrength = 0;
            if(roommate != null && roommate.equals(otherStudent.getName())) {
                connectionStrength += 5;
            }
            for(String internship : previousInternships) {
                if(otherStudent.getPreviousInternships().contains(internship)) {
                    connectionStrength += 4;
                }
            }
            if(major.equals(otherStudent.getMajor())) {
                connectionStrength += 3;
            }
            if(this.age == otherStudent.getAge()) {
                connectionStrength += 2;
            }
            if(connectionStrength >= 10) {
                connectionStrength = 10;
            }
            return connectionStrength;
        }
        return 0;
    }

    /**
     * Returns the name of the student.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns age of the student.
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the gender of the student.
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the year of the student.
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the major of the student.
     * @return major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Returns the GPA of the student.
     * @return gpa
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Returns the list of roommate preferences.
     * @return roommate preference list
     */
    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }

    /**
     * Returns the list of previous internships.
     * @return previous internship list
     */
    public List<String> getPreviousInternships() {
        return previousInternships;
    }

    /**
     * Returns the name of the roommate.
     * @return roommate name
     */
    public String getRoommate() {
        return roommate;
    }

    /**
     * Sets the name of the roommate.
     * @param roommate name of the roommate
     */
    public void setRoommate(String roommate) {
        this.roommate = roommate;
    }

    /**
     * Returns the preference rank of the given name.
     * @param name name to check
     * @return preference rank
     */
    public int preferenceRank(String name) {
        for (int i = 0; i < roommatePreferences.size(); i++) {
            if (roommatePreferences.get(i).equals(name)) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Returns a string representation of the student.
     * @return string representation
     */
    public String toString() {
        String result = "Name: " + name + "\n";
        result += "Age: " + age + "\n";
        result += "Gender: " + gender + "\n";
        result += "Year: " + year + "\n";
        result += "Major: " + major + "\n";
        result += "GPA: " + gpa + "\n";
        if(roommate != null) {
            result += "Roommate: " + roommate + "\n";
        } else {
            result += "Roommate: None\n";
        }
        result += "Roommate Preferences: " + roommatePreferences + "\n";
        result += "Previous Internships: " + previousInternships + "\n\n";
        return result;
    }
}

