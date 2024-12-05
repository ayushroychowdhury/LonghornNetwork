
import java.io.*;
import java.util.*;


/** Class that includes method to parse student information and create com.studentgraph.model.UniversityStudent objects */
public class DataParser {

    /** Read student information from a file and output list of com.studentgraph.model.UniversityStudent objects */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        ArrayList<UniversityStudent> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            UniversityStudent student = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Student:")) {
                    if (student != null) {
                        students.add(student);

                        /*System.out.println(student);
                        for (String s : student.getRoommatePreferences()) {
                            System.out.println(s);
                        }
                        System.out.println(""); */

                    }
                    student = new UniversityStudent();
                } else if (line.startsWith("Name:")) {
                    if (student != null) student.setName(getValue(line));
                } else if (line.startsWith("Age:")) {
                    if (student != null) student.setAge(parseIntOrDefault(getValue(line), -1));
                } else if (line.startsWith("Gender:")) {
                    if (student != null) student.setGender(getValue(line));
                } else if (line.startsWith("Year:")) {
                    if (student != null) student.setYear(parseIntOrDefault(getValue(line), -1));
                } else if (line.startsWith("Major:")) {
                    if (student != null) student.setMajor(getValue(line));
                } else if (line.startsWith("GPA:")) {
                    if (student != null) student.setGpa(parseDoubleOrDefault(getValue(line), -1.0));
                } else if (line.startsWith("RoommatePreferences:")) {
                    if (student != null) student.setRoommatePreferences(parseList(getValue(line)));
                } else if (line.startsWith("PreviousInternships:")) {
                    if (student != null) student.setPreviousInternships(parseList(getValue(line)));
                }
            }


            if (student != null) {

                students.add(student);

                /*
                System.out.println(student);
                for (String s : student.getRoommatePreferences()) {
                    System.out.println(s);
                }
                System.out.println(""); */
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found. Exiting...");
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error: IO exception. Exiting...");
            e.printStackTrace();
            System.exit(-1);
        }

        return students;
    }

    private static String getValue(String line) {
        String[] parts = line.split(":", 2);
        return (parts.length < 2) ? "" : parts[1].trim();
    }

    private static int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static List<String> parseList(String value) {
        if (value.isEmpty()) {
            return new ArrayList<>();
        }
        String[] items = value.split(",");
        List<String> result = new ArrayList<>();
        for (String item : items) {
            result.add(item.trim());
        }
        return result;
    }


}
