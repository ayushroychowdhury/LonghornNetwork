import java.util.*;

/**
 * Assigns roommates to students using the GaleShapley algorithm
 */
public class GaleShapley {
    /**
     * Assigns roommates to students
     * @param students A list of students
     */
    public static String assignRoommates(List<UniversityStudent> students) {
        String result = "";

        Map<UniversityStudent, Queue<UniversityStudent>> proposals = new HashMap<>();
        Queue<UniversityStudent> unmatchedStudents = new LinkedList<>();

        //
        for (UniversityStudent student : students) {
            unmatchedStudents.add(student);
            proposals.put(student, new LinkedList<>(student.getPreferredRoommates()));
        }

        while (!unmatchedStudents.isEmpty()) {
            UniversityStudent proposer = unmatchedStudents.poll();
            Queue<UniversityStudent> preferenceQueue = proposals.get(proposer);

            // keep proposing until finding a suitable roommate or preference list is empty
            while (!preferenceQueue.isEmpty()) {
                UniversityStudent potentialRoommate = preferenceQueue.poll();

                if (potentialRoommate.roommate == null) {
                    // System.out.println(proposer.name + " matched with " + potentialRoommate.name);
                    proposer.roommate = potentialRoommate.name;
                    potentialRoommate.roommate = proposer.name;
                    // ADD
                    unmatchedStudents.remove(potentialRoommate);
                    break;
                } else {
                    UniversityStudent currentRoommate = Main.nameMap.get(potentialRoommate.roommate);

                    // check if potential prefers proposer
                    if (potentialRoommate.getPreferredRoommates().indexOf(proposer) < potentialRoommate.getPreferredRoommates().indexOf(currentRoommate)) {
                        // ADD
                        if (!unmatchedStudents.contains(potentialRoommate)) continue;
                        // replace
                        // System.out.println(proposer.name + " steals " + potentialRoommate.name + " from " + currentRoommate.name);
                        currentRoommate.roommate = null;
                        unmatchedStudents.add(currentRoommate);
                        proposer.roommate = potentialRoommate.name;
                        potentialRoommate.roommate = proposer.name;
                        // ADD
                        unmatchedStudents.remove(potentialRoommate);
                        break;
                    }
                }


            }
        }


        for (Student s : students) {
            if (s.roommate != null) {
                System.out.println(s.name + " is roommates with " + s.roommate + ".");
                result += (s.name + " is roommates with " + s.roommate + ".\n");
            } else {
                System.out.println(s.name + " does not have a roommate.");
                result += (s.name + " does not have a roommate." + "\n");
            }
        }

        return result;

    }
}
