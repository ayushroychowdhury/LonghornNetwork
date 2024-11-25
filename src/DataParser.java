import java.io.*;
import java.util.*;

public class DataParser {

    /**
     * Parses a file containing student data and creates a list of UniversityStudent objects.
     *
     * @param filename the name of the file containing student data
     * @return a list of UniversityStudent objects
     * @throws IOException if an error occurs while reading the file
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String name = null, gender = null, major = null;
            int age = 0, year = 0;
            double gpa = 0.0;
            List<String> roommatePreferences = new ArrayList<>();
            List<String> previousInternships = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Name:")) {
                    name = line.substring(6).trim();
                } else if (line.startsWith("Age:")) {
                    age = Integer.parseInt(line.substring(5).trim());
                } else if (line.startsWith("Gender:")) {
                    gender = line.substring(8).trim();
                } else if (line.startsWith("Year:")) {
                    year = Integer.parseInt(line.substring(6).trim());
                } else if (line.startsWith("Major:")) {
                    major = line.substring(7).trim();
                } else if (line.startsWith("GPA:")) {
                    gpa = Double.parseDouble(line.substring(5).trim());
                } else if (line.startsWith("RoommatePreferences:")) {
                    String[] preferences = line.substring(20).trim().split(",\\s*");
                    roommatePreferences = Arrays.asList(preferences);
                } else if (line.startsWith("PreviousInternships:")) {
                    String[] internships = line.substring(21).trim().split(",\\s*");
                    previousInternships = Arrays.asList(internships);
                } else if (line.isEmpty()) {
                    // End of a student's data, create and add a UniversityStudent object
                    if (name != null) {
                        students.add(new UniversityStudent(name, age, gender, year, major, gpa,
                                new ArrayList<>(roommatePreferences),
                                new ArrayList<>(previousInternships)));
                    }
                    // Reset fields for the next student
                    name = null;
                    gender = null;
                    major = null;
                    age = 0;
                    year = 0;
                    gpa = 0.0;
                    roommatePreferences = new ArrayList<>();
                    previousInternships = new ArrayList<>();
                }
            }

            // Add the last student if the file doesn't end with a blank line
            if (name != null) {
                students.add(new UniversityStudent(name, age, gender, year, major, gpa,
                        new ArrayList<>(roommatePreferences),
                        new ArrayList<>(previousInternships)));
            }
        }

        return students;
    }
}
