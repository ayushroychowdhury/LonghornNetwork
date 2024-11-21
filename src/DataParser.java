import java.io.*;
import java.util.*;

/**
 * Reads in the Student data from the input file and parses them into a List
 */
public class DataParser {
    /**
     * Parses the Students that are input
     * @param filename the file to read Students from
     * @return the list of UniversityStudents
     * @throws IOException throws an IOException if reading the file fails
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> result = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            // Parse the student
            while (sc.hasNext()) {
                sc.nextLine(); // skip "Student:"
                String name = sc.nextLine().split(": ")[1];
                int age = Integer.parseInt(sc.nextLine().split(": ")[1]);
                String gender = sc.nextLine().split(": ")[1];
                int year = Integer.parseInt(sc.nextLine().split(": ")[1]);
                String major = sc.nextLine().split(": ")[1];
                double GPA = Double.parseDouble(sc.nextLine().split(": ")[1]);
                List<String> roommatePreferences;
                List<String> previousInternships;

                try {
                    roommatePreferences = Arrays.asList(sc.nextLine().split(": ")[1].split(", "));
                } catch (Exception e) {
                    roommatePreferences = new ArrayList<>();
                }
                try {
                    previousInternships = Arrays.asList(sc.nextLine().split(": ")[1].split(", "));
                } catch (Exception e) {
                    previousInternships = new ArrayList<>();
                }

                if (age<0) throw new IllegalArgumentException("Age cannot be negative");
                if (year<0) throw new IllegalArgumentException("Year cannot be negative");
                if (GPA<0) throw new IllegalArgumentException("GPA cannot be negative");

                UniversityStudent s = new UniversityStudent(name, age, gender, year, major, GPA, roommatePreferences, previousInternships);
                result.add(s);

                if (sc.hasNextLine()) sc.nextLine(); // skip the blank line
            }

            return result;
        } catch (IOException e) {
            System.out.println("Could not find input file: " + filename);
            throw e;
        } catch (Exception e) {
            System.out.println("Input file is not correctly formatted.");
            throw e;
        }

        // return new ArrayList<>();
    }
}
