import java.util.*;

/**
 * Given a target company, djikstra's will be run to find the shortest path from a student to that company
 */
public class ReferralPathFinder {
    private static final int infinity = Integer.MAX_VALUE;
    private Map<String, UniversityStudent> nameStudentMap;
    private StudentGraph graph;
    /**
     * Constructor
     * @param graph StudentGraph
     */
    public ReferralPathFinder(StudentGraph graph, Map<String, UniversityStudent> nameStudentMap) {
        this.graph = graph;
        this.nameStudentMap = nameStudentMap;
    }

    /**
     * Runs djikstra's to find the shortest referral path (if one exists)
     * @param start student
     * @param targetCompany company
     * @return shortest path to company (excluding the given start node)
     */
    public Map<String, Object> findReferralPath(UniversityStudent start, String targetCompany) {
        Map<String, Object> results = new HashMap<>();
        List<UniversityStudent> path = new ArrayList<>();
        List<String> endNodes = new ArrayList<>(); // list of target nodes (for djikstra's)
        for (String student : graph.getNodes()) {
            UniversityStudent stud = nameStudentMap.get(student);
            boolean isTargetNode = false;
            for (String internship : stud.previousInternships) {
                if (internship.equals(targetCompany)) {
                    isTargetNode = true;
                    break;
                }
            }
            if (isTargetNode) {
                // if the student himself/herself worked at the company, they are the path
                if (start.name.equals(student)) {
                //     path.add(start);
                //     return path;
                }
                else {
                    endNodes.add(student);
                }
            }
            
        }

        if (endNodes.size() == 0) {
            results.put("weight", -1);
            results.put("path", path);
            return results;
        }

        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> dist = new HashMap<>();
        Map<String, Integer> actualDist = new HashMap<>();
        List<String> Q = new LinkedList<>();
        // int maxWeight = 0;
        for (String student : graph.getNodes()) {
            parent.put(student, null);
            dist.put(student, infinity);
            actualDist.put(student, infinity);
            Q.add(student);
            // for (StudentPair st : graph.getNeighbors(nameStudentMap.get(student))) {
            //     if (st.getWeight() > maxWeight) {
            //         maxWeight = st.getWeight();
            //     }
            // }
        }
        actualDist.put(start.name, 0);
        dist.put(start.name, 0);
        
        // dijkstra(graph, Q, parent, dist, maxWeight + 1);
        dijkstra(graph, Q, parent, dist, actualDist, 10);

        // find shortest path length to student
        int strongestConnection = Integer.MAX_VALUE;
        String maxStud = null;
        for (String endNode : endNodes) {
            int resultDistance = dist.get(endNode);
            if (resultDistance < strongestConnection) {
                strongestConnection = resultDistance;
                maxStud = endNode;
            }
        }
        
        // check if none are reachable
        if (strongestConnection == Integer.MAX_VALUE) {
            results.put("weight", -1);
            results.put("path", path);
            return results;
        }

        // trace back
        String maxStudCpy = maxStud;
        while (maxStudCpy != null) {
            path.add(nameStudentMap.get(maxStudCpy));
            maxStudCpy = parent.get(maxStudCpy);
        }

        Collections.reverse(path);
        results.put("weight", actualDist.get(maxStud));
        results.put("path", path);
        return results;
    }

    private void dijkstra(StudentGraph graph, List<String> Q, Map<String, String> parent, Map<String, Integer> dist, Map<String, Integer> actualDist, int reductionFactor) {
        while (!Q.isEmpty()) {
            // find min dist student/node
            String minStud = Q.get(0);
            for (String stud : Q) {
                if (dist.get(stud) < dist.get(minStud)) { 
                    minStud = stud;
                }
            }
            Q.remove(minStud);

            if (dist.get(minStud) == infinity)
                // remaining nodes are disconnected
                return;
            
            List<StudentPair> adjStudents = graph.getNeighbors(nameStudentMap.get(minStud));
            for (StudentPair edge : adjStudents) {
                if (Q.contains(edge.getStudent().name)) {
                    int newWeight = dist.get(minStud) + edge.getWeight();
                    if (Math.max(reductionFactor - newWeight, 0) < dist.get(edge.getStudent().name)) {
                        dist.put(edge.getStudent().name, Math.max(reductionFactor - newWeight, 0));
                        actualDist.put(edge.getStudent().name, actualDist.get(minStud) + edge.getWeight());
                        parent.put(edge.getStudent().name, minStud);
                    }
                }
            }
        }
    }
}
