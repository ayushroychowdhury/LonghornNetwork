import java.util.*;

/**
 * A class that represents a university student and extends the Student class
 */
public class UniversityStudent extends Student {

    /**
     * Constructor for a university student object
     * @param name the name of the student
     * @param age the age of the student
     * @param gender the gender of the student
     * @param year the year of the student
     * @param major the major of the student
     * @param gpa the GPA of the student
     * @param roommatePreferences the list of roommate preferences of the student
     * @param previousInternships the list of previous internships of the student
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
     * Calculates the connection strength between this student and another student based on parameters
     * @param other The other student with which the connection strength is calculated
     * @return The connection strength as integer
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        /* Every student starts with a score of 100 */
        int connectionStrength = 0;

        /* The closer in age, the more connected*/
        if (this.getAge() == other.getAge()) {
            connectionStrength += 2;
        }

        /* More connected if same major */
        if (this.getMajor().equals(other.getMajor())) {
            connectionStrength += 3;
        }

        /* More connected if the students are roommates */
        if (other instanceof UniversityStudent) {
            if (GaleShapley.isRoommates(this, (UniversityStudent) other)) {
                connectionStrength += 5;
            }
        }

        /* More connected if the students had the same previous internships */
        List<String> thisInternships = this.getPreviousInternships();
        List<String> otherInternships = other.getPreviousInternships();
        for (String internship : thisInternships) {
            if (otherInternships.contains(internship)) {
                connectionStrength += 4;
            }
        }

        return connectionStrength;
    }


    /**
     * Extracts student data from a list of strings and creates a UniversityStudent object
     * @param studentData the list of strings containing the student data in the format: Name, Age, Gender, Year, Major, GPA, RoommatePreferences, PreviousInternships
     * @return a UniversityStudent object created from the student data, or null if the data is invalid
     */
    public static UniversityStudent createStudentFromData(ArrayList<String> studentData) {
        /* Check for proper length */
        if (studentData.size() != 8) {
            return null;
        }

        /* Get name */
        String name = null;
        String contentLine = studentData.get(0);
        if (contentLine != null){
            if (contentLine.startsWith("Name:")) {
                if (contentLine.length() > 5) {
                    name = contentLine.substring(5).trim();
                }
            }
        }
        if (name == null || name.isBlank() || name.isEmpty()) {
            return null;
        }

        /* Get age */
        int age = -1;
        contentLine = studentData.get(1);
        if (contentLine != null){
            if (contentLine.startsWith("Age:")) {
                if (contentLine.length() > 4) {
                    try{
                        age = Integer.parseInt(contentLine.substring(4).trim());
                    }catch (NumberFormatException e){
                        return null;
                    }
                }
            }
        }
        if (age == -1) {
            return null;
        }

        /* Get gender */
        String gender = null;
        contentLine = studentData.get(2);
        if (contentLine != null){
            if (contentLine.startsWith("Gender:")) {
                if (contentLine.length() > 7) {
                    gender = contentLine.substring(7).trim();
                }
            }
        }
        if (gender == null || gender.isBlank() || gender.isEmpty()) {
            return null;
        }

        /* Get year */
        int year = -1;
        contentLine = studentData.get(3);
        if (contentLine != null){
            if (contentLine.startsWith("Year:")) {
                if (contentLine.length() > 5) {
                    try{
                        year = Integer.parseInt(contentLine.substring(5).trim());
                    }catch (NumberFormatException e){
                        return null;
                    }
                }
            }
        }
        if (year == -1) {
            return null;
        }

        /* Get major */
        String major = null;
        contentLine = studentData.get(4);
        if (contentLine != null){
            if (contentLine.startsWith("Major:")) {
                if (contentLine.length() > 6) {
                    major = contentLine.substring(6).trim();
                }
            }
        }
        if (major == null || major.isBlank() || major.isEmpty()) {
            return null;
        }

        /* Get GPA */
        double gpa = -1.0;
        contentLine = studentData.get(5);
        if (contentLine != null){
            if (contentLine.startsWith("GPA:")) {
                if (contentLine.length() > 4) {
                    try{
                        gpa = Double.parseDouble(contentLine.substring(4).trim());
                    }catch (NumberFormatException e){
                        return null;
                    }
                }
            }
        }
        if (gpa < 0 || gpa > 4) {
            return null;
        }

        /* Get roommate preferences */
        ArrayList<String> roommatePreferences= new ArrayList<String>();
        contentLine = studentData.get(6);
        if (contentLine != null){
            if (contentLine.startsWith("RoommatePreferences:")) {
                if (contentLine.length() > 20) {
                    String roommatePrefString = contentLine.substring(20).trim();
                    String[] roommatePrefArray = roommatePrefString.split(",");
                    for (String pref : roommatePrefArray) {
                        roommatePreferences.add(pref.trim());
                    }
                }
            }
        }
        if (roommatePreferences.isEmpty()) {
            return null;
        }

        /* Get previous internships */
        ArrayList<String> previousInternships = new ArrayList<String>();
        contentLine = studentData.get(7);
        if (contentLine != null){
            if (contentLine.startsWith("PreviousInternships:")) {
                if (contentLine.length() > 20) {
                    String previousInternString = contentLine.substring(20).trim();
                    String[] previousInternArray = previousInternString.split(",");
                    for (String internship : previousInternArray) {
                        previousInternships.add(internship.trim());
                    }
                }
            }
        }
        if (previousInternships.isEmpty()) {
            return null;
        }

        /* Create new UniversityStudent with the extracted data and return it */
        return new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences, previousInternships);
    }

    /**
     * Finds UniversityStudent object from name
     * @param name Name of the university student
     * @param students List of university students
     * @return UniversityStudent object with the given name or null if not found
     */
    public static UniversityStudent getStudentFromString(String name, List<UniversityStudent> students){
        for(UniversityStudent student : students){
            if(student.getName().equals(name)){
                return student;
            }
        }
        return null;
    }
}