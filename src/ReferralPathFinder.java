//import java.util.*;
//
///**
// * Finds referral paths using Dijkstra's algorithm.
// */
//public class ReferralPathFinder {
//    private final StudentGraph graph;
//
//    public ReferralPathFinder(StudentGraph graph) {
//        this.graph = graph;
//    }
//
//    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
//        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
//        Map<UniversityStudent, Integer> distances = new HashMap<>();
//        PriorityQueue<UniversityStudent> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
//        Set<UniversityStudent> visited = new HashSet<>();
//
//        // Initialize distances
//        for (UniversityStudent student : graph.getStudents()) {
//            distances.put(student, Integer.MAX_VALUE);
//        }
//        distances.put(start, 0);
//        pq.add(start);
//
//        while (!pq.isEmpty()) {
//            UniversityStudent current = pq.poll();
//            // skip already visited nodes
//            if (visited.contains(current)) continue;
//            visited.add(current);
//
//            // DEBUGGING
//            System.out.println("Visiting: " + current.name + ", Current Weight: " + distances.get(current));
//            // Check if current student has the target company
//            if (!current.equals(start) && current.previousInternships.contains(targetCompany)) {
//                // build path
//                List<UniversityStudent> path = new ArrayList<>();
//                for (UniversityStudent at = current; at != null; at = previous.get(at)) {
//                    path.add(0, at);
//                }
//                System.out.println("Found referral path for student " + start.name + " with weight: " + distances.get(current));
//                return path;
//            }
//
//            // Update neighbors
//            for (Map.Entry<UniversityStudent, Integer> neighbor : graph.getNeighbors(current).entrySet()) {
////                int invertedWeight = 10 - neighbor.getValue(); // Invert the weight
//                int edgeWeight = neighbor.getValue();
//                int newDist = distances.get(current) + edgeWeight;
//                System.out.println("Checking neighbor: " + neighbor.getKey().name + ", New Distance: " + newDist);
//                if (newDist < distances.get(neighbor.getKey()) && !visited.contains(neighbor.getKey())) {
//                    distances.put(neighbor.getKey(), newDist);
//                    previous.put(neighbor.getKey(), current);
//                    pq.add(neighbor.getKey());
//                }
//            }
//        }
//        System.out.println("No referral path found for student " + start.name);
//        return Collections.emptyList();
//    }
//}

import java.util.*;

public class ReferralPathFinder {
    private final StudentGraph graph;

    public ReferralPathFinder(StudentGraph graph) {
        this.graph = graph;
    }

    /**
     * Finds referral paths to students who interned at the target company.
     *
     * @param start         the starting student.
     * @param targetCompany the target company.
     * @return a map of students who have the target company in their internships,
     *         with their shortest paths.
     */
    public Map<UniversityStudent, List<UniversityStudent>> findReferralPath(UniversityStudent start, String targetCompany) {
        Map<UniversityStudent, Integer> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        PriorityQueue<UniversityStudent> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Map<UniversityStudent, List<UniversityStudent>> results = new HashMap<>();

        // Initialize distances for all students in the adjacency list
        for (UniversityStudent student : graph.getStudents()) { // Use getStudents from StudentGraph
            distances.put(student, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            UniversityStudent current = pq.poll();

            // Skip already processed nodes
            if (results.containsKey(current)) continue;
            // DEBUGGING
//            System.out.println("Visiting: " + current.name + ", Current Weight: " + distances.get(current));

            // Traverse neighbors and update distances
            for (Map.Entry<UniversityStudent, Integer> neighbor : graph.getNeighbors(current).entrySet()) {
                int invertedWeight = 20 - neighbor.getValue();  // Inverted weight for comparison
                int newDist = 20 - distances.get(current) + invertedWeight;
//                int newDist = distances.get(current) + neighbor.getValue();
                // DEBUGGING
//                System.out.println("Checking neighbor: " + neighbor.getKey().name + ", New Distance: " + newDist);

                if (newDist < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newDist);
                    previous.put(neighbor.getKey(), current);
                    pq.add(neighbor.getKey());
                }
            }

            // After processing all neighbors, check if current node matches the target company
            if (!current.equals(start) && current.previousInternships.contains(targetCompany)) {
//                List<UniversityStudent> path = buildPath(current, previous);
//                int originalWeight = calculateOriginalWeight(path);
                results.put(current, buildPath(current, previous));
//                System.out.println("Found referral path for student " + current.name + " with weight: " + originalWeight);
            }
        }

        // display results
        for (Map.Entry<UniversityStudent, List<UniversityStudent>> entry : results.entrySet()) {
            UniversityStudent target = entry.getKey();
            List<UniversityStudent> path = entry.getValue();
            int originalWeight = calculateOriginalWeight(path);
            System.out.println("Found referral path for student " + target.name + " with weight: " + originalWeight);
        }


        // If no matching students were found, print a message
        if (results.isEmpty()) {
            System.out.println("No referral paths found for " + start.name + " to company " + targetCompany + ".");
        }

        return results;
    }

    /**
     * Builds the path to a given target student using the previous map.
     *
     * @param target   the target student.
     * @param previous the map of previous students in the path.
     * @return the list representing the path.
     */
    private List<UniversityStudent> buildPath(UniversityStudent target, Map<UniversityStudent, UniversityStudent> previous) {
        List<UniversityStudent> path = new ArrayList<>();
        for (UniversityStudent at = target; at != null; at = previous.get(at)) {
            path.add(0, at);
        }
        return path;
    }

    /**
     * Calculates the original weight of a given path (sum of edge weights).
     *
     * @param path the path as a list of students.
     * @return the total original weight of the path.
     */
    private int calculateOriginalWeight(List<UniversityStudent> path) {
        int totalWeight = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            UniversityStudent from = path.get(i);
            UniversityStudent to = path.get(i + 1);
            totalWeight += graph.getNeighbors(from).get(to); // Sum the original edge weights
        }
        return totalWeight;
    }
}

