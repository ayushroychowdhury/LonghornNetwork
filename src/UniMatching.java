import java.util.*;

public class UniMatching {
     private UniversityStudent stuRef;
     private LinkedList<String> prefList;
     private boolean isMatched = false;
     private int numProposed = 0;

     UniMatching(UniversityStudent input, List<UniversityStudent> students){
         stuRef = input;
         prefList = new LinkedList<String>(input.roommatePreferences);

         for (UniversityStudent stu : students){
            if (!checkIfPref(prefList, stu)){
                prefList.add(stu.name);
            }
         }
     }

     private boolean checkIfPref(LinkedList<String> prefList, UniversityStudent student){
        if (student.roommatePreferences.size() == 0 || student.name == stuRef.name){
            return true;
        }
        for (String name : prefList){
            if (name.equals(student.name)){
                return true;
            }
        }
        return false;
     }

     private int findRanking(String stu){
         int count = 0;
         for (String name : stuRef.roommatePreferences){
             if (name.equals(stu)){
                 return count;
             }
             ++count;
         }
         return Integer.MAX_VALUE;
     }

     public String propose(UniMatching other){
         ++numProposed;
        String resString = other.respond(this);

         if (!resString.equals("2")){
             isMatched = true;
             stuRef.setRoommate(other.getStu());
             prefList.removeFirst();
             return resString;
         } else {
             isMatched = false;
             prefList.removeFirst();
             return resString;
         }
     }

     public String respond(UniMatching other){
         if (!isMatched){
             isMatched = true;
             stuRef.setRoommate(other.getStu());
             return "1";
         } else {
             int currRanking = findRanking(stuRef.getRoommateName());
             int otherRanking = findRanking(other.getStu().name);

             if (otherRanking < currRanking){
                stuRef.getRoommate().unmatch();
                String saveString = stuRef.getRoommateName();
                stuRef.setRoommate(other.getStu());
                return saveString;
             } else {
                return "2";
             }
         }
     }

     public void unmatch(){
         isMatched = false;
         stuRef.unmatch();
     }

     public int numPrefs(){
         return prefList.size();
     }

     public UniversityStudent getStu(){
         return stuRef;
     }

     public int getNumProposed(){
         return numProposed;
     }

     public boolean isMatched(){
        return isMatched;
     }

     public void removePref(){
        prefList.removeFirst();
     }

     public String getFirstPref(){
        return prefList.getFirst();
     }

 }
