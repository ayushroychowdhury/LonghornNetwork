import java.io.*;
import java.util.*;

/**
 * Class to parse student data from file
 */
public class DataParser {
    /**
     * Parses a list of UniversityStudent objects from a file.
     * @param filename name of file to be read from
     * @return list of UniversityStudent objects created from filename
     * @throws IOException if any issues arise when parsing students from filename
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        String name = null;
        Integer age = null;
        String gender = null;
        Integer year = null;
        String major = null;
        Double gpa = null;
        List<String> roommatePreferences = new ArrayList<>();
        List<String> previousInternships = new ArrayList<>();

        int linecount = 0;
        boolean newStudent = false;

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (!newStudent && line.equals("Student:")){
                    linecount++;
                    newStudent = true;
                    continue;
                } else if (newStudent){
                    if (linecount < 9){
                        if (line.equals("")){
                            continue;
                        } else {
                            linecount++;
                            String[] parts = line.split(":", 2);
                            if (parts.length < 2){
                                throw new IOException("Missing second argument: " + line);
                            }
                            String key = parts[0].trim();
                            String value = parts[1].trim();
    
                            switch (key) {
                                case "Name":
                                    name = value;
                                    break;
                                case "Age":
                                    try {
                                        age = Integer.parseInt(value);
                                    } catch (NumberFormatException e) {
                                        System.err.println("Invalid age for student: " + value);
                                    }
                                    break;
                                case "Gender":
                                    gender = value;
                                    break;
                                case "Year":
                                    try {
                                        year = Integer.parseInt(value);
                                    } catch (NumberFormatException e) {
                                        System.err.println("Invalid year for student: " + value);
                                    }
                                    break;
                                case "Major":
                                    major = value;
                                    break;
                                case "GPA":
                                    try {
                                        gpa = Double.parseDouble(value);
                                    } catch (NumberFormatException e) {
                                        System.err.println("Invalid GPA for student: " + value);
                                    }
                                    break;
                                case "RoommatePreferences":
                                    roommatePreferences = new ArrayList<>(Arrays.asList(value.split(",\\s*")));
                                    break;
                                case "PreviousInternships":
                                    previousInternships = new ArrayList<>(Arrays.asList(value.split(",\\s*")));
                                    break;
                                default:
                                    System.err.println("Unknown field: " + key);
                                    break;
                            }
                            if (linecount == 9){
                                students.add(new UniversityStudent(name, age, gender, year, major, gpa, new ArrayList<>(roommatePreferences), new  ArrayList<>(previousInternships)));
                                newStudent = false;
                                linecount = 0;
                                name = null;
                                age = null;
                                gender = null;
                                year = null;
                                major = null;
                                gpa = null;
                                roommatePreferences.clear();
                                previousInternships.clear();
                            }
                        }
                    }
                }
            }
            return students;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return students;
    }
}
