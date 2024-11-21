/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

 import java.util.*;

 /**
  * StudentGraph class that represents an arraylist of students.
  */
 public class StudentGraph {
     ArrayList<LinkedList<UniversityStudent>> studentGraph;
 
     /**
      * Constructor for StudentGraph class. Creates a graph of students.
      */
     StudentGraph(List<UniversityStudent> students) {
         // Constructor

         studentGraph = new ArrayList<LinkedList<UniversityStudent>>();
        for (int i = 0; i < students.size(); i++) {
            studentGraph.add(new LinkedList<UniversityStudent>());
        }

     }
 
 
 }