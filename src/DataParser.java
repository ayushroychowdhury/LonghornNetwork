/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */


import java.io.*;
import java.util.*;

/**
 * DataParser class that parses student data from a file.
 */
public class DataParser {

    /**
     * Parses the student data from the given file and returns a list of UniversityStudent objects.
     * @param filename The name of the file containing student data
     * @return List of UniversityStudent objects in file
     * @throws IOException If an I/O error occurs
     */
    public static ArrayList<UniversityStudent> parseStudents(String filename) throws IOException {

        // Open file for reading
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Create list to store students
        ArrayList<UniversityStudent> students = new ArrayList<UniversityStudent>();
        String line;

        // While file not at end, parse new student data
        while ((line = reader.readLine()) != null) {

            // Parse student data if next line is "Student:"
            if (line.equals("Student:")) {
                // Parse student data
                String name = parseLine(reader.readLine());
                int age = Integer.parseInt(parseLine(reader.readLine()));
                String gender = parseLine(reader.readLine());
                int year = Integer.parseInt(parseLine(reader.readLine()));
                String major = parseLine(reader.readLine());
                double gpa = Double.parseDouble(parseLine(reader.readLine()));

                // Parse roommates, only keep if not empty
                String[] roommates = parseLine(reader.readLine()).split(", ");
                if (roommates[0].equals("RoommatePreferences:")) {
                    roommates = new String[0];
                }

                // Parse internships, only keep if not empty
                String[] prevInternships = parseLine(reader.readLine()).split(", ");
                if (prevInternships[0].equals("PreviousInternships:")) {
                    prevInternships = new String[0];
                }

                // Add student to list
                students.add(new UniversityStudent(name, age, gender, year, major, gpa,
                        Arrays.asList(roommates), Arrays.asList(prevInternships)));
            }
        }

        // Close file
        reader.close();

        // Return list of students
        return students;
    }

    /**
     * Parses a line of text and returns the value after the colon.
     * @param line The line of text to parse
     * @return The value after the colon
     */
    private static String parseLine(String line) {

        // Check if line is null, if so, throw exception
        if (line == null) {
            throw new IllegalArgumentException("Invalid input file format.");
        }

        // Split line by colon to get actual data
        String[] parts = line.split(": ");

        // If line has two parts, return the second part, else check for other cases
        if (parts.length == 2) {
            return parts[1];
        } else if (parts.length == 1) { // If no colon
            // Split line by space to get data if colon missing
            String[] parts2 = line.split(" ");

            // If only one part, return empty string
            if (parts2.length == 1) {
                return "";
            }

            // If more than one part, return everything after first word
            String result = parts2[1];
            for (int i = 2; i < parts2.length; i++) {
                result = result.concat(" " + parts2[i]);
            }
            // Return result
            return result;
        } else {
            // If more than two parts, return everything after first word
            String result = parts[1];
            for (int i = 2; i < parts.length; i++) {
                result = result.concat(" " + parts[i]);
            }
            return result;
        }
    }
}
