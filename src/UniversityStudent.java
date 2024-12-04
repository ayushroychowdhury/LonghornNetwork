import java.util.*;

/**
 * An extension of the student class. Basically just a special student. Will have to
 * flesh out the details later.
 */
public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented
    private UniversityStudent roommate = null;
    private ArrayList<UniversityStudent> podMembers = new ArrayList<UniversityStudent>();
    /**
     * This is the defined method that will calculate the strength between two students takes 
     * a student as input to compare against and returns how strong thier connection is.
     */
    public void setRoommate(UniversityStudent newRoommate){
        roommate = newRoommate;
    }

    public String getRoommateName(){
        if (roommate == null){
            return "";
        } else {
            return roommate.name;
        }
    }

    public UniversityStudent getRoommate(){
        return roommate;
    }

    public void unmatch(){
        roommate = null;
    }

    public void addPodMember(UniversityStudent student){
        podMembers.add(student);
    }

    public int calculateConnectionStrength(Student other){
        int strength = 0;
        if (roommate == other){
            strength += 5;
        }
        for (int i = 0; i < this.previousInternships.size(); ++i){
            for (int j = 0; j < other.previousInternships.size(); ++j){
                if (this.previousInternships.get(i).equals(other.previousInternships.get(j))){
                    strength += 4;
                }
            }
        }
        if (this.major.equals(other.major)){
            strength += 3;
        }
        if (this.age == other.age){
            strength += 2;
        }
        return strength;
    }
}

