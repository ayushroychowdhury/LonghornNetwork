/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */



 import javax.lang.model.element.ModuleElement;
 import java.util.*;

 /**
  * ReferralPathFinder class that finds the strongest connection to a student who interned at a target company.
  */
 public class ReferralPathFinder {
     // List of nodes in the graph
    private ArrayList<DijkstraNode> nodes = new ArrayList<DijkstraNode>();
    // List of students
    private ArrayList<UniversityStudent> students;
    // List of visited and unvisited nodes
    private ArrayList<DijkstraNode> visited = new ArrayList<DijkstraNode>();
    private ArrayList<DijkstraNode> unvisited = new ArrayList<DijkstraNode>();


     /**
      * Constructor for ReferralPathFinder class.
      * @param graph
      */
     ReferralPathFinder(StudentGraph graph, ArrayList<UniversityStudent> students) {
         // Create and add dijkstra nodes for each student
         for (int i = 0; i < students.size(); i++) {
             this.nodes.add(new DijkstraNode(i));
         }

         // Connect all nodes
         for (DijkstraNode node : this.nodes) {
             node.connectNodes(this.nodes, students);
         }

         // Set students
         this.students = students;

         // Add all nodes to unvisited
         for (DijkstraNode node : this.nodes) {
             unvisited.add(node);
         }
     }
 
     /**
      * Finds strongest connection to a student who interned at target company using Dijkstra's algorithm.
      * @param start
      * @param targetCompany
      * @return List of students representing the referral path
      */
     public ArrayList<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
         // set the distance of the start node to 0
         int start_index = students.indexOf(start);
         // Final path
        ArrayList<UniversityStudent> path = new ArrayList<UniversityStudent>();


//        System.out.println("Current graph: ");
//        for (DijkstraNode node : nodes) {
//            System.out.print(students.get(node.getStudentIndex()).getName() + " " );
//            for (DijkstraNode edge : node.getEdges()) {
//                System.out.print(students.get(edge.getStudentIndex()).getName());
//            }
//            System.out.println();
//        }

        // Run dijkstra's algorithm
        dijkstra(start);

        // Print distances to start after dijkstra
         System.out.println();
        System.out.println("Paths to internship seeker: ");
        for (DijkstraNode node : nodes) {
            System.out.println(students.get(node.getStudentIndex()).getName() + " " + node.getDistance());
        }

        // Find the nearest student who interned at the target company
        int min_dist = -1;
        int min_index = -1;

        // Loop over each student and find the one with the smallest distance that interned at the target company
        for(int i = 0; i < nodes.size(); i++){
            //System.out.println(students.get(nodes.get(i).getStudentIndex()).getName());

            // If the student has a smaller distance and interned at the target company, update values
            if ((min_dist == -1 || nodes.get(i).getDistance() < min_dist)
                    && students.get(nodes.get(i).getStudentIndex()).previousInternships.contains(targetCompany)){
                min_dist = nodes.get(i).getDistance();
                min_index = i;
            }
        }

        // If no path was found, return empty path
        if (min_dist == -1){
            System.out.println("No path found");
            return path;
        }

        // Reconstruct the path
        DijkstraNode current = nodes.get(min_index);
        // Going from the end to the start, add each student to the path until start reached
        while(!current.equals(nodes.get(start_index))) {
            path.add(0, students.get(current.getStudentIndex()));
            current = nodes.get(current.getParentIndex());
        }
        // Add the start student to the path
        path.add(0, students.get(start_index));

         return path;
     }
 
     /**
      * Finds the shortest path from the start student to all other students using Dijkstra's algorithm.
      * @param start
      */
     private void dijkstra(UniversityStudent start) {
         // Set the distance of the start student to 0
         this.nodes.get(students.indexOf(start)).setDistance(0);
         // Visit the start student
         visit(this.nodes.get(students.indexOf(start)));
         // Update the amountVisited
         int amountVisited = 1;

         // While there are still unvisited nodes
         while (amountVisited < students.size()) {
             // Find the node with the smallest distance that has not been visited
             DijkstraNode min = findMinDistance();
             // visit node
             amountVisited++;
             // If no node was found, break, path not possible
             if (min == null) {
                 break;
             }
             // Relax all edges from the node
             for (DijkstraNode edge : min.getEdges()) {
                 //System.out.println("relaxing edge to node: " + students.get(edge.getStudentIndex()).getName());
                 relax(min, edge);
             }
         }
     }
 
     /**
      * Finds the student with the smallest distance from the start student that has not been visited.
      * @return The student with the smallest distance from the start student that has not been visited
      */
     private DijkstraNode findMinDistance() {
         // Initialize variables
         int minDist = -1;
         DijkstraNode bestNode = null;

         // Loop over all unvisited nodes and find the one with the smallest distance
         for (DijkstraNode node : unvisited) {
             //System.out.println("tracking node: " + students.get(node.getStudentIndex()).getName() + " " + node.getDistance());
             // If the node hasn't been visited and has a smaller distance than current, update values
             if (node.getDistance() != -1 && (minDist == -1 || node.getDistance() < minDist)) {
                    minDist = node.getDistance();
                    bestNode = node;
             }
         }

         //System.out.println("best node: " + students.get(bestNode.getStudentIndex()).getName());
        // If a node was found, remove it from unvisited and visit it, return
         if (bestNode != null) {
             unvisited.remove(bestNode);
             visit(bestNode);

             return bestNode;
         }
         return null;
     }
 
     /**
      * Relaxes the edge between two students.
      * @param u
      * @param v
      */
     private void relax(DijkstraNode u, DijkstraNode v) {
         // If the node has been visited, return
         if (v.isVisited()) {
             return;
         }

         // Do nothing if u and v have no connection
         if (students.get(u.getStudentIndex()).calculateConnectionStrength(students.get(v.getStudentIndex())) == 0){
             return;
         }

         // Calculate the distance between the two students (add 20 and subtract the connection strength)
         int dist = u.getDistance() + 20 - students.get(u.getStudentIndex()).calculateConnectionStrength(students.get(v.getStudentIndex()));
         // If the distance is smaller than the current distance, update the distance and parent index
         if (v.getDistance() == -1 || dist < v.getDistance()) {
             v.setDistance(dist);
             v.setParentIndex(u.getStudentIndex());
         }
     }

        /**
        * Visits a node and marks it as visited.
        * @param node
        */
     private void visit(DijkstraNode node){
         visited.add(node);
         node.visit();
     }
 }
 