import java.util.HashMap;
import java.util.List;
import java.util.HashSet;

/**
 * Extends from Student
 * Implements the calculateConnectionStrength method
 */
public class UniversityStudent extends Student {

    public int calculateConnectionStrength(Student other) {
        int strength = 0;

        // Add 5 if roomate
        if (name.equals(other.roommate)){
            strength += 5;
        }

        // Add 4 for each shared internship
        for (String internship : previousInternships) {
            if (other.previousInternships.contains(internship)) {
                strength += 4;
            }
        }

        // Add 3 if interacted/friends
        // boolean friends = false;
        // for (String friend : other.friends) {
        //     if (name.equals(friend)) {
        //         strength += 3;
        //         friends = true;
        //         break;
        //     }
        // }
        // if (!friends) {
        //     if (other.chatLog.size() > 0)
        //     {
        //         strength += 3;
        //     }
        // }

        // Add 2 if same major
        if (major.equals(other.major)) {
            strength += 3;
        }

        // Add 1 if same age
        if (age == other.age) {
            strength += 2;
        }

        return strength;
    }

    public String toString() {
        String s = "Student:";
        s += "\nName: " + name;
        s += "\nAge: " + age;
        s += "\nGender: " + gender;
        s += "\nYear: " + year;
        s += "\nMajor: " + major;
        s += "\nRoomate: " + roommate;

        s += "\nRoomatePreferences: ";
        for (int i = 0, n = roommatePreferences.size(); i < n; ++i) {
            s += roommatePreferences.get(i);
            if (i < n - 1) {
                s += ", ";
            }
        }

        s += "\nPreviousInternships: ";
        for (int i = 0, n = previousInternships.size(); i < n; ++i) {
            s += previousInternships.get(i);
            if (i < n - 1) {
                s += ", ";
            }
        }

        friends = new HashSet<>();
        chatLog = new HashMap<String, List<String>>();

        return s;
    }
}