/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */


 import java.util.*;


 /**
  * PodFormation class that forms pods of students.
  */
 public class PodFormation {
        // graph of students
        private StudentGraph studentGraph;
        // list of students (because graph doesn't explicitly contain start nodes of edges)
        private ArrayList<UniversityStudent> students;
        // MST graph
        private ArrayList<LinkedList<Edge>> MST = new ArrayList<LinkedList<Edge>>();

     /**
      * Constructor for PodFormation class.
      * @param graph The graph of students
      */
     public PodFormation(StudentGraph graph, ArrayList<UniversityStudent> students) {
         // Constructor
         this.studentGraph = graph;
         this.students = students;
         // Initialize MST
         for (int i = 0; i < students.size(); i++) {
             MST.add(new LinkedList<Edge>());
         }


     }
 
     /**
      * Forms pods of the given size by generating an MST using Prim's algorithm and then dividing it into pods.
      * @param podSize Number of students in each pod
      */
     public void formPods(int podSize) {
         // Generate MST
         generateMST();

         // Divide MST into pods
         divideIntoPods(podSize);
     }
 
     /**
      * Generates a minimum spanning tree using Prim's algorithm.
      */
     public void generateMST() {
         // Edges which extend out of current MST
         ArrayList<Edge> searchEdges = new ArrayList<Edge>();
         // Students in the MST
         ArrayList<UniversityStudent> MSTStudents = new ArrayList<UniversityStudent>();

         // Loop over each student so that multiple MST's can be formed
         // Each iteration will form a new MST (or skip if current student is connected to one)
         for (int i = 0; i < students.size(); i++) {

             // If student isn't in MST, add them and their edges to searchEdges
             if (!MSTStudents.contains(students.get(i))) {
                 MSTStudents.add(students.get(i));
                 for (Edge edge : StudentGraph.studentGraph.get(i)) {
                     searchEdges.add(edge);
                 }
             }

             // While there are still edges to search, find the minimum link and add it to the MST
             while (searchEdges.size() > 0) {
                 // Find the minimum link
                 Edge minEdge = findMinLink(searchEdges, MSTStudents);

                 // If no minimum link is found, break, current MST complete
                 if (minEdge == null) {
                     break;
                 }

                 // Add the minimum link to the MST
                 MSTStudents.add(minEdge.getStudent());
                 // Add 2 edges to MST, one for each students' linked list
                 MST.get(students.indexOf(minEdge.getSource())).add(minEdge);
                 MST.get(students.indexOf(minEdge.getStudent())).add(new Edge(minEdge.getSource(), minEdge.getStudent()));
                 // Add all edges from the new student to searchEdges
                 for (Edge edge : StudentGraph.studentGraph.get(students.indexOf(minEdge.getStudent()))) {
                     searchEdges.add(edge);
                 }
             }
         }

     }
 
     /**
      * Finds the minimum link for next iteration of Prim's algorithm.
      * @param searchEdges Edges to search
      *                    (edges that extend out of the current MST)
      * @param MSTStudents Students in the MST
      * @return The minimum link edge
      */
     private Edge findMinLink(ArrayList<Edge> searchEdges, ArrayList<UniversityStudent> MSTStudents) {

         // If no edges to search, return null
         if (searchEdges.size() == 0) {
             return null;
         }

            // Find the minimum link
         int min = Integer.MAX_VALUE;
         Edge minEdge = null;

         // Loop over each edge in searchEdges
         for (int i = searchEdges.size()-1; i >= 0; i--) {
             // If the student is already in the MST, remove the edge (Cycle found)
             if (MSTStudents.contains(searchEdges.get(i).getStudent())){
                 searchEdges.remove(i);
                 continue;
             }
                // If the connection strength is less than the current minimum, update the minimum
             if (15-searchEdges.get(i).getConnectionStrength() < min) {
                 min = 15-searchEdges.get(i).getConnectionStrength();
                 minEdge = searchEdges.get(i);
             }
         }

            // Remove the minimum link from searchEdges
         searchEdges.remove(minEdge);
         return minEdge;
     }
 
 
     /**
      * Divides the MST into pods of the given size.
      */
     private void divideIntoPods(int podSize) {
         // Create new Graph
         ArrayList<LinkedList<Edge>> newMST = new ArrayList<LinkedList<Edge>>();
         // Create list of students assigned pods
         ArrayList<UniversityStudent> podStudents = new ArrayList<UniversityStudent>();

         // Initialize newMST
         for (int i = 0; i < students.size(); i++){
             newMST.add(new LinkedList<Edge>());
         }

         // Loop until all students are assigned to a pod
         while (podStudents.size() < students.size()) {
                // Create list of possible edges to add to pod (adjacent and free)
             ArrayList<Edge> currentPossibleEdges = new ArrayList<Edge>();
                // Current pod size
             int currentPodSize = 0;

                // Loop over each student to find any free one
                for (int i = 0; i < students.size(); i++) {
                    // If student isn't in a pod, add them to a pod
                    if (!podStudents.contains(students.get(i))) {
                        // Add student to pod
                        podStudents.add(students.get(i));
                        currentPodSize++;

                        // Add possible edges from current student to currentPossibleEdges
                        for (Edge edge : MST.get(i)) {
                            if (!podStudents.contains(edge.getStudent())) {
                                currentPossibleEdges.add(edge);
                            }
                        }

                        // While the pod isn't full, add students connected to pod to the pod
                        while (currentPodSize < podSize){
                            // Find the best student match to the pod
                            Edge minEdge = findMinLink(currentPossibleEdges, podStudents);
                            // If no student is found, break
                            if (minEdge == null) {
                                break;
                            }

                            // Add best student to pod graph
                            podStudents.add(minEdge.getStudent());
                            newMST.get(students.indexOf(minEdge.getSource())).add(minEdge);
                            newMST.get(students.indexOf(minEdge.getStudent())).add(new Edge(minEdge.getSource(), minEdge.getStudent()));
                            currentPodSize++;

                            // Add possible edges from new student to currentPossibleEdges
                            for (Edge edge : MST.get(students.indexOf(minEdge.getStudent()))) {
                                if (!podStudents.contains(edge.getStudent())) {
                                    currentPossibleEdges.add(edge);
                                }
                            }
                        }
                        // Once pod is full, break out of this loop to check if all students are in a pod
                        break;
                    }
                }

         }

            // Update real MST to be pods
         MST = newMST;
     }

        /**
        * Prints the MST.
        */
     public void printMST(){
         // Loop over each student and print their connections
         for (int i = 0; i < MST.size(); i++) {
             // Print student name
             System.out.print(students.get(i).getName() + ": ");
                // Print each connection and strength
             for (int j = 0; j < MST.get(i).size(); j++) {
                 System.out.print(MST.get(i).get(j).getStudent().getName() + " "
                 + MST.get(i).get(j).getConnectionStrength() + " ");
             }
             System.out.println();
         }
     }
 
 
 }
 