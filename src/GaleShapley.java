import java.util.*;

/**
 * Class used to match students to their roommate based on the Gale Shapley algorithm
 */
public class GaleShapley {
    /**
     * Given a list of University Students, assigns roommate to each student
     * based on their preference list via Gale Shapley algorithm
     * @param students list of University Students
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Step 1: Initialize data structures
        Map<String, UniversityStudent> studentMap = new HashMap<>();
        for (UniversityStudent student : students) {
            studentMap.put(student.getName(), student);
        }

        // Queue for unpaired students
        Queue<UniversityStudent> unpairedStudents = new LinkedList<>();
        for (UniversityStudent student : students) {
            unpairedStudents.add(student);
        }

        // Step 2: Gale-Shapley algorithm for stable roommate matching
        while (!unpairedStudents.isEmpty()) {
            UniversityStudent proposer = unpairedStudents.poll();
            List<String> preferences = proposer.getRoommatePreferences();

            for (String preferredRoommateName : preferences) {
                UniversityStudent preferredRoommate = studentMap.get(preferredRoommateName);
                if (preferredRoommate != null) {
                    String currentRoommate = preferredRoommate.getRoommate();
                    if (currentRoommate == null) {
                        // If the preferred roommate is not yet paired, accept the proposal
                        proposer.setRoommate(preferredRoommateName);
                        preferredRoommate.setRoommate(proposer.getName());
                        break;
                    } else {
                        // If the preferred roommate is already paired, check if they prefer the proposer
                        UniversityStudent currentRoommateStudent = studentMap.get(currentRoommate);
                        if (currentRoommateStudent != null) {
                            List<String> preferredList = currentRoommateStudent.getRoommatePreferences();
                            if (preferredList.indexOf(proposer.getName()) < preferredList.indexOf(currentRoommate)) {
                                // If the preferred roommate prefers the proposer, switch roommates
                                currentRoommateStudent.setRoommate(null); // Unpair the current roommate
                                unpairedStudents.add(currentRoommateStudent); // Re-add the replaced roommate to the queue

                                proposer.setRoommate(preferredRoommateName);
                                preferredRoommate.setRoommate(proposer.getName());
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Step 3: Output the final roommate assignments
        System.out.println("Roommate Assignment:");
        for (UniversityStudent student : students) {
            String roommate = student.getRoommate();
            if (roommate != null) {
                System.out.println(student.getName() + " is roommates with " + roommate);
            }
        }
        for (UniversityStudent student : students) {
            if (student.getRoommate() == null) {
                System.out.println(student.getName() + " does not have a roommate.");
            }
        }
    }
}
