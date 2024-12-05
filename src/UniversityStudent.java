import java.util.*;

/**
 * Represents a university student with additional attributes and methods.
 */
public class UniversityStudent extends Student {
    public List<String> roommatePreferences;
    public List<String> previousInternships;
    // TODO: Constructor and additional methods to be implemented
    // ctor

    // Other fields inherited from Student

    @Override
    public int calculateConnectionStrength(Student other) {
        if (!(other instanceof UniversityStudent)) {
            return 0; // Ensure compatibility
        }

        UniversityStudent otherStudent = (UniversityStudent) other;
        int connectionStrength = 0;

        // Roommate: Add 5 if they are roommates
        if (roommatePreferences != null && roommatePreferences.contains(otherStudent.name)) {
            connectionStrength += 5;
        }

        // Shared Internships: Add 4 for each shared internship
        if (previousInternships != null && otherStudent.previousInternships != null) {
            for (String internship : previousInternships) {
                if (otherStudent.previousInternships.contains(internship)) {
                    connectionStrength += 4;
                }
            }
        }

        // Same Major: Add 3 if they share the same major
        if (this.major != null && this.major.equals(otherStudent.major)) {
            connectionStrength += 3;
        }

        // Same Age: Add 2 if they are the same age
        if (this.age == otherStudent.age) {
            connectionStrength += 2;
        }

        return connectionStrength;
    }
}
