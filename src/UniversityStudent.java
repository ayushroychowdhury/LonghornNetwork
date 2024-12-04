import java.util.*;

/**
 * The concrete class of a UniversityStudent that can be instantiated to make Student objects.
 */
public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented

    /**
     * Constructs a UniversityStudent with the given parameters.
     * @param name UniversityStudent's name
     * @param age UniversityStudent's age
     * @param gender UniversityStudent's gender
     * @param year UniversityStudent's year (1..4)
     * @param major UniversityStudent's major
     * @param gpa UniversityStudent's GPA
     * @param roommatePreferences UniversityStudent's roommate preferences
     * @param previousInternships UniversityStudent's previous internships
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePreferences;
        this.previousInternships = previousInternships;
        this.roommate = null;
    }

    public List<UniversityStudent> getPreferredRoommates() {
        List<UniversityStudent> roommates = new LinkedList<>();
        for (String s : roommatePreferences) {
            roommates.add(Main.nameMap.get(s));
        }
        return roommates;
    }

    /**
     * Returns the student and its data
     * @return the student and its data
     */
    @Override
    public String toString() {
        return name + "\n" + age + "\n" + gender + "\n" + year + "\n" + major + "\n" + gpa + "\n" + roommatePreferences + "\n" + previousInternships;
    }

    /**
     * @param other the other student to calculate the connection strength to
     * @return the connection strength between the two students
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        // Roommate: Add 5 if they are roommates.
        // Shared Internships: Add 4 for each shared internship.
        // Chat History: Add 3 if they have interacted or are friends.
        // Same Major: Add 2 if they share the same major.
        // Same Gender: Add 1 if they are the same age. ???
        int connectionStrength = 0;
        if (other.name.equals(roommate)) {
            connectionStrength += 5;
        }
        for (String company : previousInternships) {
            if (other.previousInternships.contains(company)) {
                connectionStrength += 4;
            }
        }
        // interacted can be one-way but friends must be two-way
//        if (Main.friends.containsKey(name) && Main.friends.containsKey(other.name) && (Main.interacted.containsKey(name) || Main.interacted.containsKey(other.name))) {
//            if (Main.interacted.get(name).contains(other.name) || Main.friends.get(name).contains(other.name) && Main.friends.get(other.name).contains(name)) {
//                connectionStrength += 3;
//            }
//        }

        if (major.equals(other.major)) {
            connectionStrength += 3;
        }
        if (age == other.age) {
            connectionStrength += 2;
        }

        return connectionStrength;
    }
}

