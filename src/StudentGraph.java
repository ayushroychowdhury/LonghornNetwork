import java.util.*;

/**
 * This is the custom graph class that I will implement myself. This is an extremelyy bare bones class
 * right now, but will be fleshed out later
 */
public class StudentGraph {
    ArrayList<Node> adjList = new ArrayList<Node>();
    /**
     * This is the constructor for creating the graph. It takes a list of students and will
     * somehow create a graph connecting all of the student together. Have yet to really 
     * finish this part at all yet.
     * @param students The list of students that the graph will be made out of
     */
    public StudentGraph(List<UniversityStudent> students){
        for (int i = 0; i < students.size(); ++i){
            adjList.add(new Node(students.get(i)));
            for (int j = 0; j < students.size(); ++j){
                if (!students.get(i).name.equals(students.get(j).name)){
                    int connStrength = students.get(i).calculateConnectionStrength(students.get(j));
                    if (connStrength > 0){
                        adjList.get(i).addAdj(students.get(j), connStrength);
                    }
                }
            }
        }
    } 
}
