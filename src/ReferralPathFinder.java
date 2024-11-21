/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */



 import java.util.*;

 /**
  * ReferralPathFinder class that finds the strongest connection to a student who interned at a target company.
  */
 public class ReferralPathFinder {
     /**
      * Constructor for ReferralPathFinder class.
      * @param graph
      */
     public ReferralPathFinder(StudentGraph graph) {
         // Constructor
     }
 
     /**
      * Finds strongest connection to a student who interned at target company using Dijkstra's algorithm.
      * @param start
      * @param targetCompany
      * @return List of students representing the referral path
      */
     public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
         // Method signature only
         return new ArrayList<>();
     }
 
     /**
      * Finds the shortest path from the start student to all other students using Dijkstra's algorithm.
      * @param start
      */
     private void dijkstra(UniversityStudent start) {
         // Method signature only
     }
 
     /**
      * Finds the student with the smallest distance from the start student that has not been visited.
      * @return The student with the smallest distance from the start student that has not been visited
      */
     private UniversityStudent findMinDistance() {
         // Method signature only
     }
 
     /**
      * Relaxes the edge between two students.
      * @param u
      * @param v
      */
     private void relax(UniversityStudent u, UniversityStudent v) {
         // Method signature only
     }
 }
 