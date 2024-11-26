import java.io.*;
import java.util.*;

/**
 * Utility class for parsing student data from files
 */
public class DataParser {
    /**
     * Parses the students from the given file and returns a list of UniversityStudent objects.
     * @param filename The name of the file containing the data
     * @return List of UniversityStudent objects
     * @throws IOException If an error occurs while reading the file
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        /* Arraylist that will later contain all the parsed students */
        ArrayList<UniversityStudent> students = new ArrayList<UniversityStudent>();

        /* Opening the file */
        File file = new File(filename);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + filename);
        }
        BufferedReader br = new BufferedReader(fr);

        /* Reading the file line for line */
        String line = br.readLine();
        ArrayList<String> studentData = new ArrayList<String>();

        while(line != null){
            if (line.isEmpty() || line.isBlank()){
                /* Add student with studentData to students list and clear student data */
                UniversityStudent newStudent = UniversityStudent.createStudentFromData(studentData);
                if (newStudent != null){
                    students.add(newStudent);
                }else {
                    throw new IOException("Invalid student data in file: " + filename);
                }
                studentData.clear();
            }else if (line.equals("Student:")){
                /* Do nothing */
                studentData.clear();
            }else{
                /* Add line to studentData */
                studentData.add(line);
            }
            line = br.readLine();
        }
        if (!studentData.isEmpty()){
            /* Add last student with studentData to students list */
            UniversityStudent newStudent = UniversityStudent.createStudentFromData(studentData);
            if (newStudent != null){
                students.add(newStudent);
            }else {
                throw new IOException("Invalid student data in file: " + filename);
            }
        }
        return students;
    }
}