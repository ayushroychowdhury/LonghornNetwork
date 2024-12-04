import java.io.*;
import java.util.*;

public class DataParser {
    /**
     * Parses the students from the given file.
     * @param filename name of the file
     * @return list of students
     * @throws IOException if an error occurs while reading the file
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.equals("Student:")) {
                    String name = scanner.nextLine().split(":")[1].trim();
                    int age = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
                    String gender = scanner.nextLine().split(":")[1].trim();
                    int year = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
                    String major = scanner.nextLine().split(":")[1].trim();
                    double gpa = Double.parseDouble(scanner.nextLine().split(":")[1].trim());
                    List<String> roommatePreferences = new ArrayList<>();
                    String preferences = scanner.nextLine().split(":")[1].trim();
                    if (!preferences.equals("")) {
                        roommatePreferences = parseList(preferences);
                    }
                    List<String> previousInternships = new ArrayList<>();
                    String internships = scanner.nextLine().split(":")[1].trim();
                    if (!internships.equals("")) {
                        previousInternships = parseList(internships);
                    }
                    students.add(new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences, previousInternships));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Could not find file: " + filename);
        } catch (Exception e) {
            System.out.println("An error occurred while parsing the file.");
        }
        return students;
    }
    
    /**
     * Parses a list of elements from a comma-separated string.
     * @param line
     * @return
     */
    private static List<String> parseList(String line) {
        List<String> list = new ArrayList<>();
        String[] elements = line.split(",");
        for (String element : elements) {
            list.add(element.trim());
        }
        return list;
    }
}
