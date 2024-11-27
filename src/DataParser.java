import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Parse UniversityStudent from a file
 * @author Yahir Lopez
 */
public class DataParser {

    /**
     * Read from an input file and create a list of UniveristyStudent objects
     * @param filename String
     * @return List of UniversityStudent
     * @throws IOException When reading a file
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<UniversityStudent> uniStudentsList= new ArrayList<UniversityStudent>();

        if (lines.isEmpty()) return uniStudentsList;

        ListIterator<String> iterator = lines.listIterator();

        while (iterator.hasNext()) {
            String line = iterator.next().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(":");

            if (parts.length == 0) continue;

            if (parts[0].trim().equals("Student") && parts.length == 1) {
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

                    String[] parts2 = line2.split(":");

                    for (int i = 0; i < parts2.length; i++) {
                        parts2[i] = parts2[i].trim();
                    }

                    try {
                        if (parts2.length == 2) {
                            switch (parts2[0]) {
                                case "Name":
                                    name = parts2[1];
                                    break;
                                case "Age": {
                                    int parsedAge = Integer.parseInt(parts2[1]);
                                    if (parsedAge < 0) throw new Exception("Invalid age");
                                    age = parsedAge;
                                    break;
                                }
                                case "Gender":
                                    gender = parts2[1];
                                    break;
                                case "Year":{
                                    int parsedYear = Integer.parseInt(parts2[1]);
                                    if (parsedYear < 0) throw new Exception("Invalid year");
                                    year = parsedYear;
                                    break;
                                }
                                case "Major":
                                    major = parts2[1];
                                    break;
                                case "GPA":
                                    double parsedGPA = Double.parseDouble(parts2[1]);
                                    if (parsedGPA < 0) throw new Exception("Invalid GPA");
                                    gpa = parsedGPA;
                                    break;
                                case "RoommatePreferences":
                                    String[] roommatePreferences2 = parts2[1].split(",");
                                    for (String s : roommatePreferences2){
                                        roommatePreferences.add(s.trim());
                                    }
                                    break;
                                case "PreviousInternships":
                                    String[] previousInternships2 = parts2[1].split(",");
                                    for (String s : previousInternships2){
                                        previousInternships.add(s.trim());
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Error parsing line: " + line2);
                        System.out.println("    "+e);
                    }
                }

                if (name.equals("NoName")){
                    System.out.println("No Name found");
                }
                if (age == 0) {
                    System.out.println("No Age found");
                }
                if (gender.equals("NoGender")) {
                    System.out.println("No Gender found");
                }
                if (year == 0) {
                    System.out.println("No Year found");
                }
                if (major.equals("NoMajor")) {
                    System.out.println("No Major found");
                }
                if (gpa == 0) {
                    System.out.println("No GPA found");
                }

                uniStudentsList.add(new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences, previousInternships));
            }
        }
        return uniStudentsList;
    }
}
