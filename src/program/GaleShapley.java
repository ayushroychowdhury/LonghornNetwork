package program;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * program.GaleShapley is a utility class that assigns roommates to students based on their preferences using the program.GaleShapley algorithm
 */
public class GaleShapley {
    private static Map<UniversityStudent, UniversityStudent> roommates = new HashMap<UniversityStudent, UniversityStudent>();

    /**
     * Assigns roommates to students based on their preferences using the Gale-Shapley algorithm
     *
     * @param students the list of students who have to be assigned
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        /* Create a map for proposals count */
        Map<UniversityStudent, Integer> proposalCount = new HashMap<>();
        for (UniversityStudent student : students) {
            proposalCount.put(student, 0);
        }

        /* Create a queue for free students */
        List<UniversityStudent> freeStudents = new LinkedList<>(students);

        while (!freeStudents.isEmpty()) {
            UniversityStudent proposer = freeStudents.getFirst();
            freeStudents.remove(proposer);
            List<String> preferences = proposer.getRoommatePreferences();
            int count = proposalCount.get(proposer);

            /* Ensure the count is within bounds of the preferences list */
            if (count >= preferences.size()) {
                continue;
            }

            /* Propose to the next preferred roommate */
            String preferredRoommateName = preferences.get(count);
            UniversityStudent preferredRoommate = UniversityStudent.getStudentFromString(preferredRoommateName, students);
            proposalCount.put(proposer, count + 1);

            if (!roommates.containsKey(preferredRoommate)) {
                /* The preferred roommate is free */
                roommates.put(proposer, preferredRoommate);
                roommates.put(preferredRoommate, proposer);

                /* The preferred roommate is no longer free */
                freeStudents.remove(preferredRoommate);
            } else {
                /* The preferred roommate is already paired */
                UniversityStudent currentRoommate = roommates.get(preferredRoommate);

                if (preferredRoommate.getRoommatePreferences().contains(proposer.getName()) &&
                        (preferredRoommate.getRoommatePreferences().indexOf(proposer.getName()) <
                        preferredRoommate.getRoommatePreferences().indexOf(currentRoommate.getName()))) {
                    /* The preferred roommate prefers the new proposer */
                    roommates.remove(currentRoommate);
                    roommates.remove(preferredRoommate);
                    roommates.put(preferredRoommate, proposer);
                    roommates.put(proposer, preferredRoommate);
                    freeStudents.add(currentRoommate);

                    /* The preferred roommate is no longer free */
                    freeStudents.remove(preferredRoommate);
                } else {
                    /* The preferred roommate prefers their current roommate */
                    freeStudents.addFirst(proposer);
                }
            }
        }
        System.out.println("Roommate assignment complete.");

        /* Print roommate assignments */
        for (UniversityStudent student : students) {
            UniversityStudent roommate = roommates.get(student);
            if (roommate != null) {
                System.out.println(student.getName() + " is roommates with " + roommate.getName());
            }
        }
    }

    /**
     * Checks if two students are roommates
     * @param firstStudent first student
     * @param secondStudent second student
     * @return true if the students are roommates, false otherwise
     */
    public static boolean isRoommates(UniversityStudent firstStudent, UniversityStudent secondStudent) {
        UniversityStudent roommate = roommates.get(firstStudent);
        return roommate != null && roommate.equals(secondStudent);
    }
}