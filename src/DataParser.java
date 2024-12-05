import java.io.*;
import java.util.*;

/**
 * Processes an input text file
 */
public class DataParser {
    /**
     * Parses students from an input text file
     * @param filename valid path to a text file
     * @return List of UniversityStudent objects
     * @throws IOException
     */
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

        try {
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
            String line;
            String[] lineSep;
            do {
                // remove leading whitespace until "Student:" is reached
                do {
                    line = br.readLine();
                    if (line == null)
                        break;
                } while (line.strip() == "");
                if (line == null)
                    break;
                
                UniversityStudent st = new UniversityStudent();

                // name
                lineSep = br.readLine().split(":");
                if (lineSep.length != 2) {
                    System.out.println("Invalid Name");
                }
                else
                    st.name = lineSep[1].strip();

                // age
                lineSep = br.readLine().split(":");
                if (lineSep.length != 2) {
                    System.out.println("Invalid Age");
                }
                else
                    st.age = Integer.valueOf(lineSep[1].strip());

                // gender
                lineSep = br.readLine().split(":");
                if (lineSep.length != 2) {
                    System.out.println("Invalid Gender");
                }
                else
                    st.gender = lineSep[1].strip();

                // year
                lineSep = br.readLine().split(":");
                if (lineSep.length != 2) {
                    System.out.println("Invalid Year");
                }
                else
                    st.year = Integer.valueOf(lineSep[1].strip());

                // major
                lineSep = br.readLine().split(":");
                if (lineSep.length != 2) {
                    System.out.println("Invalid Major");
                }
                else
                    st.major = lineSep[1].strip();

                // gpa
                lineSep = br.readLine().split(":");
                if (lineSep.length != 2) {
                    System.out.println("Invalid GPA");
                }
                else
                    st.gpa = Double.valueOf(lineSep[1].strip());

                // roomate preferences
                st.roommatePreferences = new ArrayList<>();
                lineSep = br.readLine().split(":");
                if (lineSep.length < 2) {
                    System.out.println("No Roomate Preferences");
                }
                else {
                    String[] rms = lineSep[1].split(",");
                    for (String rm : rms) {
                        st.roommatePreferences.add(rm.strip());
                    }
                }

                // previous internships
                st.previousInternships = new ArrayList<>();
                lineSep = br.readLine().split(":");
                if (lineSep.length < 2) {
                    System.out.println("No Previous Internships");
                }
                else {
                    String[] pis = lineSep[1].split(",");
                    for (String pi : pis) {
                        st.previousInternships.add(pi.strip());
                    }
                }

                if (st.age < 0)
                    throw new IllegalArgumentException("Invalid Age");
                if (st.year < 0)
                    throw new IllegalArgumentException("Invalid Year");
                if (st.gpa < 0)
                    throw new IllegalArgumentException("Invalid GPA");

                students.add(st);
            } while (line != null);
        }
        catch (Exception e) {
            System.out.println("File not properly formatted.");
        }
        

        br.close();
        return students;
    }
}
