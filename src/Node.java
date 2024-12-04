import java.util.*;

public class Node  extends HeapMember{
    UniversityStudent stu;
    ArrayList<UniversityStudent> adjList = new ArrayList<UniversityStudent>();
    ArrayList<Integer> weights = new ArrayList<Integer>();

    ArrayList<UniversityStudent> mstAdj = new ArrayList<UniversityStudent>();
    ArrayList<Integer> mstWeights = new ArrayList<Integer>();

    Node currMinEdge = null;
    Node forwardEdge = null;

    Node(UniversityStudent inputStu){
        super(inputStu);
        stu = inputStu;
    }

    public void addAdj(UniversityStudent student, Integer weight){
        adjList.add(student);
        weights.add(weight);
    }

    public void addMstAdj(UniversityStudent student, Integer weight){
        mstAdj.add(student);
        mstWeights.add(weight);
    }

    public Node copyNode(){
        Node copy = new Node(stu);
        for (int i = 0; i < adjList.size(); ++i){
            copy.addAdj(adjList.get(i), weights.get(i));
        }
        for (int i = 0; i < mstAdj.size(); ++i){
            copy.addMstAdj(mstAdj.get(i), mstWeights.get(i));
        }

        return copy;
    }
}
