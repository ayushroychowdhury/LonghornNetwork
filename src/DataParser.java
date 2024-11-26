import java.io.*;
import java.util.*;

public class DataParser {
    /**
     * Parses the students from the given file.
     * @param filename
     * @return
     * @throws IOException
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        List<UniversityStudent> students = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.equals("Student:")) {
                String name = scanner.nextLine().substring(6);
                int age = Integer.parseInt(scanner.nextLine().substring(5));
                String gender = scanner.nextLine().substring(8);
                int year = Integer.parseInt(scanner.nextLine().substring(6));
                String major = scanner.nextLine().substring(7);
                double gpa = Double.parseDouble(scanner.nextLine().substring(5));
                List<String> roommatePreferences = new ArrayList<>();
                String preferences = scanner.nextLine().substring(20);
                if (!preferences.equals("")) {
                    roommatePreferences = parseList(preferences);
                }
                List<String> previousInternships = new ArrayList<>();
                String internships = scanner.nextLine().substring(21);
                if (!internships.equals("")) {
                    previousInternships = parseList(internships);
                }
                students.add(new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences, previousInternships));
            }
        }
        scanner.close();
        return students;
    }

    private static List<String> parseList(String line) {
        List<String> list = new ArrayList<>();
        String[] elements = line.split(",");
        for (String element : elements) {
            list.add(element.trim());
        }
        return list;
    }
}
