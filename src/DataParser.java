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

                try {
                    if (line.startsWith("Name:")) {
                        name = parseField(line, "Name:");
                    } else if (line.startsWith("Age:")) {
                        age = Integer.parseInt(parseField(line, "Age:"));
                    } else if (line.startsWith("Gender:")) {
                        gender = parseField(line, "Gender:");
                    } else if (line.startsWith("Year:")) {
                        year = Integer.parseInt(parseField(line, "Year:"));
                    } else if (line.startsWith("Major:")) {
                        major = parseField(line, "Major:");
                    } else if (line.startsWith("GPA:")) {
                        gpa = Double.parseDouble(parseField(line, "GPA:"));
                    } else if (line.startsWith("RoommatePreferences:")) {
                        roommatePreferences = parseListField(line, "RoommatePreferences:");
                    } else if (line.startsWith("PreviousInternships:")) {
                        previousInternships = parseListField(line, "PreviousInternships:");
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
                } catch (Exception e) {
                    // Log and continue processing the next student
                    System.err.println("Error processing line: " + line + " - " + e.getMessage());
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

    /**
     * Parses a single field from a line.
     *
     * @param line  the line containing the field
     * @param prefix the prefix of the field (e.g., "Name:")
     * @return the value of the field as a string
     * @throws IllegalArgumentException if the line is incorrectly formatted
     */
    private static String parseField(String line, String prefix) {
        if (!line.startsWith(prefix)) {
            throw new IllegalArgumentException("Invalid field format: " + line);
        }
        return line.substring(prefix.length()).trim();
    }

    /**
     * Parses a comma-separated list field from a line.
     *
     * @param line  the line containing the list field
     * @param prefix the prefix of the field (e.g., "RoommatePreferences:")
     * @return a list of values
     */
    private static List<String> parseListField(String line, String prefix) {
        String value = parseField(line, prefix);
        if (value.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(value.split(",\\s*"));
    }
}
