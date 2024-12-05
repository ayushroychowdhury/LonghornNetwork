import java.io.*;
import java.util.*;

/**
 * Utility class for parsing student data from input files.
 */
public class DataParser {

    /**
     * Parses student data from the specified file.
     *
     * @param filename the name of the file containing student data.
     * @return a list of {@link UniversityStudent} objects parsed from the file.
     * @throws IOException if an error occurs while reading the file.
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        UniversityStudent universityStudent = null;

        while ((line = br.readLine()) != null) {
            if (line.startsWith("Student:")) {
                if (universityStudent != null) {
                    students.add(universityStudent); // Add the previous student
                    // in case like in the referral_path_sample test, where roommate preferences and prev internships are left blank
                    if (universityStudent.roommatePreferences == null) {
                        universityStudent.roommatePreferences = new ArrayList<>();
                    }
                    if (universityStudent.previousInternships == null) {
                        universityStudent.previousInternships = new ArrayList<>();
                    }
                }
                universityStudent = new UniversityStudent(); // Start a new student
            } else if (line.startsWith("Name: ")) {
                universityStudent.name = line.substring(6);
            } else if (line.startsWith("Age: ")) {
                universityStudent.age = Integer.parseInt(line.substring(5));
            } else if (line.startsWith("Gender: ")) {
                universityStudent.gender = line.substring(8);
            } else if (line.startsWith("Year: ")) {
                universityStudent.year = Integer.parseInt(line.substring(6));
            } else if (line.startsWith("Major: ")) {
                universityStudent.major = line.substring(7);
            } else if (line.startsWith("GPA: ")) {
                universityStudent.gpa = Double.parseDouble(line.substring(5));
            } else if (line.startsWith("RoommatePreferences: ")) {
                universityStudent.roommatePreferences = List.of(line.substring(21).split(", "));
            } else if (line.startsWith("PreviousInternships: ")) {
                universityStudent.previousInternships = List.of(line.substring(21).split(", "));
            }
        }

        // add last student
        if (universityStudent != null) {
            // in case like in the referral_path_sample test, where roommate preferences and prev internships are left blank
            if (universityStudent.roommatePreferences == null) {
                universityStudent.roommatePreferences = new ArrayList<>();
            }
            if (universityStudent.previousInternships == null) {
                universityStudent.previousInternships = new ArrayList<>();
            }
            students.add(universityStudent); // Add the last student
        }

        br.close();
        return students;
    }
}
