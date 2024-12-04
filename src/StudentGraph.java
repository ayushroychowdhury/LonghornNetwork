import java.util.*;

/**
 * This is the custom graph class that I will implement myself. This is an extremelyy bare bones class
 * right now, but will be fleshed out later
 */
public class StudentGraph {
    ArrayList<Node> adjList = new ArrayList<Node>();
    HashMap<String, Node> stuToNode = new HashMap<String, Node>();
    /**
     * This is the constructor for creating the graph. It takes a list of students and will
     * somehow create a graph connecting all of the student together. Have yet to really 
     * finish this part at all yet.
     * @param students The list of students that the graph will be made out of
     */
    public StudentGraph(List<UniversityStudent> students){
        for (int i = 0; i < students.size(); ++i){
            adjList.add(new Node(students.get(i)));
            stuToNode.put(students.get(i).name, adjList.get(adjList.size() - 1));
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

    public StudentGraph deepCopy(){
        List<UniversityStudent> tmp = new ArrayList<UniversityStudent>();
        StudentGraph copy = new StudentGraph(tmp);
        for (int i = 0; i < adjList.size(); ++i){
            copy.adjList.add(adjList.get(i).copyNode());
        }
        copy.stuToNode = stuToNode;
        return copy;
    }

    public void resetGraph(){
        for (int i = 0; i < adjList.size(); ++i){
            adjList.get(i).reset();
            adjList.get(i).currMinEdge = null;
        }
    }

    public UniversityStudent getStudent(String name){
        Node stuNode = stuToNode.get(name);
        if (stuNode == null){
            return null;
        }
        return stuNode.stu;
    }

    public void printGraph(){
        for (int i = 0; i < adjList.size(); ++i){
            Node currNode = adjList.get(i);
            System.out.print(currNode.stu.name);
            for (int j = 0; j < currNode.adjList.size(); ++j){
                System.out.print("->[" + currNode.adjList.get(j).name + "," + currNode.weights.get(j) + "]");
            }
            System.out.println("");
        }

    }
}
