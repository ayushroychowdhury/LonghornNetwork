import java.io.*;
import java.util.*;


/**
 * This is a simple class that simply reads an input file and parses the data to retrieve the students held inside of
 * it. Then returns that list of students to be used.
 */
public class DataParser {
    /**
     * This function reads an input file and parses the data for the students inside that we plan to include
     * in our project. Then returns a list of all of the students included in the input file
     * @param filename The name/path of the file that holds our data
     * @return The list of students that was parsed out of the input file
     * @throws IOException There is an exception incase there is an error in the data inside of
     * the file that we will be reading
     */

    private static boolean checkSep(String data){
        int count = 0;
        for (int i = 0; i < data.length() - 1; ++i){
            if (data.charAt(i) == ':'){
                ++count;
            }
        }
        if (count == 1){
            return true;
        } else {
            return false;
        }
    }

    private static void parseLine(ArrayList<UniversityStudent> stuList, String data) throws Exception{
        //[\\s:,]+
        String[] parts = data.split("[\\s,]+");
        try {
        switch (parts[0]){
            case "Student:": 
            UniversityStudent tmpStudent = new UniversityStudent();
            tmpStudent.roommatePreferences = new ArrayList<String>();
            tmpStudent.previousInternships = new ArrayList<String>();
            stuList.add(tmpStudent);                    
                break;
            case "Name:": stuList.get(stuList.size() - 1).name = parts[1];
                break;
            case "Age:": stuList.get(stuList.size() - 1).age = Integer.parseInt(parts[1]);
                break;
            case "Gender:": stuList.get(stuList.size() - 1).gender = parts[1];
                break;
            case "Year:": stuList.get(stuList.size() - 1).year = Integer.parseInt(parts[1]);
                break;
            case "Major:": 
                String totString = "";
                for (int i = 1; i < parts.length;++i){
                    totString += parts[i];
                    if (i != parts.length - 1){
                        totString += " ";
                    }
                }
                stuList.get(stuList.size() - 1).major = totString;
                break;
            case "GPA:": stuList.get(stuList.size() - 1).gpa = Double.parseDouble(parts[1]);
                break;
            case "RoommatePreferences:":
                for (int i = 1; i < parts.length; ++i){
                    List<String> tmpList = stuList.get(stuList.size() - 1).roommatePreferences;
                    tmpList.add(parts[i]);
                }
                break;
            case "PreviousInternships:":
                for (int i = 1; i < parts.length; ++i){
                    List<String> tmpList= stuList.get(stuList.size() - 1).previousInternships;
                    tmpList.add(parts[i]);
                }
                break;
            case "":
                break;
            default: 
                break;
        }
    } catch (Exception e){
        System.out.println("Error when parsing data, probably array out of bounds");
        e.printStackTrace();
    }

    }


    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        ArrayList<UniversityStudent> stuList = new ArrayList<UniversityStudent>();
        try {
            File myFile = new File(filename);
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()){
                String data = reader.nextLine();
                parseLine(stuList,data);
            }
        } catch (FileNotFoundException e){
            System.out.println("The file was not found!");
            e.printStackTrace();
        } catch (Exception e){
            throw new IOException("IO Error!");
        }

        return stuList;
    }
}
