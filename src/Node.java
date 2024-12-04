import java.util.*;

public class Node {
    UniversityStudent stu;
    ArrayList<UniversityStudent> adjList = new ArrayList<UniversityStudent>();
    ArrayList<Integer> weights = new ArrayList<Integer>();

    Node(UniversityStudent inputStu){
        stu = inputStu;
    }

    public void addAdj(UniversityStudent student, Integer weight){
        adjList.add(student);
        weights.add(weight);
    }
}
