import java.util.*;

/**
 * Implements the Gale-Shapley algorithm for assigning roommates.
 */
public class GaleShapley {
    private static Map<UniversityStudent, UniversityStudent> roommatePairs = new HashMap<>();

    /**
     * Assigns roommates to the given list of university students using the Gale-Shapley algorithm.
     *
     * @param students the list of university students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Method implementation
    }

    /**
     * Checks if two students are roommates.
     *
     * @param a the first student
     * @param b the second student
     * @return true if the students are roommates, false otherwise
     */
    public static boolean areRoommates(UniversityStudent a, UniversityStudent b) {
        return roommatePairs.get(a) == b;
    }
}