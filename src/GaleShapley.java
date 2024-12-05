/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

 import java.util.*;

 /**
  * GaleShapley class that assigns roommates to students using the Gale-Shapley algorithm.
  */
 public class GaleShapley {
     /**
      * Assigns roommates to the given list of students using modified Gale-Shapley algorithm for single group.
      * Does not return anything, but updates the students' roommate fields.
      * @param students The list of students to assign roommates to
      */
     public static void assignRoommates(List<UniversityStudent> students) {
         // Map of students to their preference queues
         Map<UniversityStudent, Queue<UniversityStudent>> proposals = new HashMap<>();

         // Queue of unmatched students
         Queue<UniversityStudent> unmatchedStudents = new LinkedList<>();

        // Add each student to queue and initialize proposals map
         for (UniversityStudent student : students) {
            unmatchedStudents.add(student);
            proposals.put(student, new LinkedList<UniversityStudent>(student.getRoommatePreferences(students)));
             //student.setRoommate(null);
         }

         // While there are unmatched students
         while(!unmatchedStudents.isEmpty()) {

                // Get proposer and preference queue
             UniversityStudent proposer = unmatchedStudents.poll();
             Queue<UniversityStudent> preferenceQueue = proposals.get(proposer);

             // Keep proposing until a match is found or all preferences are exhausted
             while (!preferenceQueue.isEmpty()) {

                 // Get next preferred student
                 UniversityStudent target = preferenceQueue.poll();

                 // If target is unmatched, match them
                 if (target.getRoommate() == null) {
                     proposer.setRoommate(target);
                     target.setRoommate(proposer);
                     break;
                 } else {
                    // If target prefers proposer to current roommate, match them

                     UniversityStudent currentRoommate = target.getRoommate();

                     if (target.getRoommatePreferences(students).indexOf(currentRoommate.getName()) >
                             target.getRoommatePreferences(students).indexOf(proposer.getName())) {
                         // Set new roommate relationships
                         target.setRoommate(proposer);
                         proposer.setRoommate(target);

                         // Add current roommate back to unmatched queue
                         unmatchedStudents.add(currentRoommate);
                         break;
                     }
                 }
             }
            // If proposer is still unmatched, set them to themselves
             if (proposer.getRoommate() == null) {
                 // System.out.println(proposer.getName() + "reached end without a match");
                 proposer.setRoommate(proposer);
             }
         }
     }
 }
 