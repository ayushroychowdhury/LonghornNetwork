import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Parse UniversityStudent from a file
 *
 * @author Cooper Nelson
 */
public class DataParser {

    /**
     * Read from an input file and create a list of UniveristyStudent objects
     *
     * @param filename String
     * @return List of UniversityStudent
     * @throws IOException When reading a file
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<String> fileLines = Files.readAllLines(Path.of(filename));
        List<UniversityStudent> sturdentsArray = new ArrayList<UniversityStudent>();
        if (fileLines.isEmpty()) return sturdentsArray;
        ListIterator<String> iterator = fileLines.listIterator();
        while (iterator.hasNext()) {
            String line = iterator.next().trim();
            if (line.isEmpty()) continue;
            String[] infoComponent = line.split(":");
            if (infoComponent.length == 0) continue;
            if (infoComponent[0].trim().equals("Student") && infoComponent.length == 1) {
                String name = new String("NoName");
                int age = 0;
                String gender = new String("NoGender");
                int year = 0;
                String major = new String("NoMajor");
                double gpa = 0.0;
                List<String> roommatePreferences = new ArrayList<String>();
                List<String> previousInternships = new ArrayList<String>();
                while (iterator.hasNext()) {
                    String line2 = iterator.next().trim();
                    if (line2.isEmpty()) break;
                    String[] data = line2.split(":");
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim();
                    }
                    try {
                        if (data.length == 2) {
                            if (data[0].equals("Name")) {
                                name = data[1];
                            } else if (data[0].equals("Age")) {
                                int parsedAge = Integer.parseInt(data[1]);
                                if (parsedAge < 0) throw new Exception("Invalid age");
                                age = parsedAge;
                            } else if (data[0].equals("Gender")) {
                                gender = data[1];
                            } else if (data[0].equals("Year")) {
                                int parsedYear = Integer.parseInt(data[1]);
                                if (parsedYear < 0) throw new Exception("Invalid year");
                                year = parsedYear;
                            } else if (data[0].equals("Major")) {
                                major = data[1];
                            } else if (data[0].equals("GPA")) {
                                double parsedGPA = Double.parseDouble(data[1]);
                                if (parsedGPA < 0) throw new Exception("Invalid GPA");
                                gpa = parsedGPA;
                            } else if (data[0].equals("RoommatePreferences")) {
                                String[] roommatePreferences2 = data[1].split(",");
                                for (String s : roommatePreferences2) {
                                    roommatePreferences.add(s.trim());
                                }
                            } else if (data[0].equals("PreviousInternships")) {
                                String[] previousInternships2 = data[1].split(",");
                                for (String s : previousInternships2) {
                                    previousInternships.add(s.trim());
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing line: " + line2);
                        System.out.println("    " + e);
                    }
                }
                sturdentsArray.add(new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences, previousInternships));
            }
        }
        return sturdentsArray;
    }
}