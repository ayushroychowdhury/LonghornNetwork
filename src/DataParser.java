import java.io.*;
import java.util.*;

public class DataParser {
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException ex) {
            // HANDLE EXCEPTION
            ex.printStackTrace();
            return students;
        }

        // ask questions about input
        /*
            Sample Input
            Student:
            Name: Alice
            Age: 20
            Gender: Female
            Year: 2
            Major: Computer Science
            GPA: 3.8
            RoommatePreferences: Bob, Charlie
            PreviousInternships: Google, Amazon
         */
        String line = br.readLine();
        String[] lineSep;
        while (line != null) {
            UniversityStudent st = new UniversityStudent();
            // name
            lineSep = br.readLine().split(" ");
            st.name = lineSep[1];
            // age
            lineSep = br.readLine().split(" ");
            st.age = Integer.valueOf(lineSep[1]);
            // gender
            lineSep = br.readLine().split(" ");
            st.gender = lineSep[1];
            // year
            lineSep = br.readLine().split(" ");
            st.year = Integer.valueOf(lineSep[1]);
            // major
            lineSep = br.readLine().split(" ");
            st.major = lineSep[1];
            // gpa
            lineSep = br.readLine().split(" ");
            st.gpa = Double.valueOf(lineSep[1]);
            // roomate preferences
            line = br.readLine();
            // previous internships
        }
        

        br.close();
        return students;
    }
}
