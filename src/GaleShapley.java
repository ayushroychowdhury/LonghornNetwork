import java.util.*;

/**
 * This is the class/function that will handle the gale shapely algorithm for matching roommates together
 */
public class GaleShapley {

    public static void assignRoommates(List<UniversityStudent> students) {
        HashMap<String, UniMatching> stuMap = new HashMap<String, UniMatching>();
        Queue<UniMatching> stuQ = new LinkedList<UniMatching>();
        for (int i = 0; i < students.size(); ++i){
            if (students.get(i).roommatePreferences.size() > 0){
                UniMatching tempMatch = new UniMatching(students.get(i), students);
                stuMap.put(students.get(i).name, tempMatch);
                stuQ.add(tempMatch);
            }
        }

        System.out.println("Roommate Assignments:");
        while (stuQ.size() > 0){
            UniMatching stu = stuQ.remove();
            while (!stu.isMatched() && stu.getNumProposed() < students.size() - 1){
                    UniMatching pref = stuMap.get(stu.getFirstPref());
                    if (pref != null){
                        String resString = stu.propose(pref);   
                        if (!resString.equals("1") && !resString.equals("2")){
                            stuQ.add(stuMap.get(resString));
                        }
                        if (!resString.equals("2")){
                            System.out.println(stu.getStu().name + " is roommates with " + pref.getStu().name);
                        }  
                    }
            }
        }
        System.out.println("");
    }
}
