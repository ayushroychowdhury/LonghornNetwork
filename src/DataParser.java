import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parses student data from an input file.
 */
public class DataParser {
    public static List<UniversityStudent> parseStudents(String fileName) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        // Variables to hold student data
        String name = null;
        int age = 0;
        String gender = null;
        int year = 0;
        String major = null;
        double gpa = 0.0;
        List<String> roommatePreferences = new ArrayList<>();
        List<String> previousInternships = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("Student:")) {
                // If data for a student, create the object
                if (name != null) {
                    UniversityStudent student = new UniversityStudent(
                            name, age, gender, year, major, gpa,
                            roommatePreferences, previousInternships);
                    students.add(student);
                    System.out.println("Parsed student: " + name);
                }
                // Reset variables for the new student
                name = null;
                age = 0;
                gender = null;
                year = 0;
                major = null;
                gpa = 0.0;
                roommatePreferences = new ArrayList<>();
                previousInternships = new ArrayList<>();
            } else {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch (key) {
                        case "Name":
                            name = value;
                            break;
                        case "Age":
                            age = Integer.parseInt(value);
                            break;
                        case "Gender":
                            gender = value;
                            break;
                        case "Year":
                            year = Integer.parseInt(value);
                            break;
                        case "Major":
                            major = value;
                            break;
                        case "GPA":
                            gpa = Double.parseDouble(value);
                            break;
                        case "RoommatePreferences":
                            roommatePreferences = Arrays.stream(value.split(","))
                                    .map(String::trim)
                                    .filter(s -> !s.isEmpty())
                                    .collect(Collectors.toList());
                            break;
                        case "PreviousInternships":
                            previousInternships = Arrays.stream(value.split(","))
                                    .map(String::trim)
                                    .filter(s -> !s.isEmpty())
                                    .collect(Collectors.toList());
                            break;
                    }
                }
            }
        }

        // After reading all lines, create the last student
        if (name != null) {
            UniversityStudent student = new UniversityStudent(
                    name, age, gender, year, major, gpa,
                    roommatePreferences, previousInternships);
            students.add(student);
            System.out.println("Parsed student: " + name);
        }

        br.close();
        return students;
    }
}