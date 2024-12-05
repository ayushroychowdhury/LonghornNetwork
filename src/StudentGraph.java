/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */

 import java.lang.reflect.Array;
 import java.util.*;

 /**
  * StudentGraph class that represents an arraylist of students.
  */
 public class StudentGraph {
     // Data structure to represent the graph of students
     public static ArrayList<LinkedList<Edge>> studentGraph;
 
     /**
      * Constructor for StudentGraph class. Creates a graph of students.
      */
     StudentGraph(List<UniversityStudent> students) {

         // Initialize graph of students
         studentGraph = new ArrayList<LinkedList<Edge>>();

         // Initialize linked lists for each student
        for (int i = 0; i < students.size(); i++) {
            studentGraph.add(new LinkedList<Edge>());
        }

        // Add edges to the graph if there is a connection between students
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < students.size(); j++) {
                if (i != j) {
                    if (students.get(i).calculateConnectionStrength(students.get(j)) > 0) {
                        studentGraph.get(i).add(new Edge (students.get(j), students.get(i)));
                    }
                }
            }
        }
     }

        /**
        * Method to print the graph of students.
        */
     public void printGraph(){
         // Print initial student and the students they are connected to
            for (int i = 0; i < studentGraph.size(); i++) {
                // Print source student
                if (studentGraph.get(i).size() > 0) {
                    System.out.print(studentGraph.get(i).get(0).getSource().getName() + ": ");
                }
                // Print connected students and corresponding connection strength
                for (int j = 0; j < studentGraph.get(i).size(); j++) {
                    System.out.print(studentGraph.get(i).get(j).getStudent().getName() + " "
                    + studentGraph.get(i).get(j).getConnectionStrength() + " ");
                }
                System.out.println();
            }
     }

 }