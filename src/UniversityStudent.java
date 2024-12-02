import java.util.*;

public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented

    String roommate;

    /**
     * Constructor for the UniversityStudent class.
     * @param name
     * @param age
     * @param gender
     * @param year
     * @param major
     * @param gpa
     * @param roommatePreferences
     * @param previousInternships
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
     * @param other
     * @return
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
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns age of the student.
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the gender of the student.
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the year of the student.
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the major of the student.
     * @return
     */
    public String getMajor() {
        return major;
    }

    /**
     * Returns the GPA of the student.
     * @return
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Returns the list of roommate preferences.
     * @return
     */
    public List<String> getRoommatePreferences() {
        return roommatePreferences;
    }

    /**
     * Returns the list of previous internships.
     * @return
     */
    public List<String> getPreviousInternships() {
        return previousInternships;
    }

    /**
     * Returns the name of the roommate.
     * @return
     */
    public String getRoommate() {
        return roommate;
    }

    /**
     * Sets the name of the roommate.
     * @param roommate
     */
    public void setRoommate(String roommate) {
        this.roommate = roommate;
    }

    /**
     * Returns the preference rank of the given name.
     * @param name
     * @return
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
     * @return
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

