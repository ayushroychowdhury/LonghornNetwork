package program;

import java.util.*;

/**
 * program.GaleShapley is a utility class that assigns roommates to students based on their preferences using the program.GaleShapley algorithm
 */
public class GaleShapley {
    private static Map<UniversityStudent, UniversityStudent> roommates = new HashMap<UniversityStudent, UniversityStudent>();
    /**
     * Assigns roommates to students based on their preferences using the program.GaleShapley algorithm
     * @param students the list of students which have to be assigned
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
            freeStudents.removeFirst();
            List<String> preferences = proposer.roommatePreferences;
            int count = proposalCount.get(proposer);

            /* Propose to the next preferred roommate */
            String preferredRoommateName = preferences.get(count);
            UniversityStudent preferredRoommate = UniversityStudent.getStudentFromString(preferredRoommateName, students);
            proposalCount.put(proposer, count + 1);
            if (preferredRoommate == null) {
                /* Preferred student is not part of the student list -> skip this student */
                freeStudents.addFirst(proposer);
                continue;
            }

            if (roommates.get(preferredRoommate) == proposer){
                /* Do nothing, proposer is already paired with this student */
            }else if (!roommates.containsKey(preferredRoommate)) {
                /* The preferred roommate is free */
                roommates.put(proposer, preferredRoommate);
                roommates.put(preferredRoommate, proposer);
            } else {
                /* The preferred roommate is already paired */
                UniversityStudent currentRoommate = roommates.get(preferredRoommate);

                if (preferredRoommate.getRoommatePreferences().indexOf(proposer.getName()) < preferredRoommate.getRoommatePreferences().indexOf(currentRoommate.getName())) {
                    /* The preferred roommate prefers the new proposer */
                    roommates.put(preferredRoommate, proposer);
                    roommates.put(proposer, preferredRoommate);
                    freeStudents.addFirst(currentRoommate);
                } else {
                    /* The preferred roommate prefers their current roommate */
                    freeStudents.addFirst(proposer);
                }
            }
        }
        System.out.println("Roommate assignment complete.");
    }

    /**
     * Checks if two students are roommates
     * @param firstStudent first student
     * @param secondStudent second student
     * @return true if the students are roommates, false otherwise
     */
    public static boolean isRoommates(UniversityStudent firstStudent, UniversityStudent secondStudent) {
        return roommates.get(firstStudent).equals(secondStudent);
    }
}