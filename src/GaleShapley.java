import java.util.*;


public class GaleShapley {
    /**
     * Runs GaleShapley to find a reasonable roomate matching
     * Roomate matchings may not always be stable
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        List<UniversityStudent> queue = new LinkedList<>();
        // index along preference list of proposals
        // QUESTION: how do we handle the cases where a student is rejected by every roommate
        Map<String, Integer> preferenceIndeces = new HashMap<>();

        Map<String, Map<String, Integer>> inversePreferenceList = new HashMap<>();

        // maps student names to objects
        Map<String, UniversityStudent> nameStudentMap = new HashMap<>();
        for (UniversityStudent student : students) {
            nameStudentMap.put(student.name, student);
        }

        // initialization step
        for (UniversityStudent student : students) {
            queue.add(student);
            preferenceIndeces.put(student.name, 0);
            inversePreferenceList.put(student.name, new HashMap<String, Integer>());
            for (int i = 0; i < student.roommatePreferences.size(); ++i) {
                inversePreferenceList.get(student.name).put(student.roommatePreferences.get(i), i);
            }
        }

        while (!queue.isEmpty()) {
            UniversityStudent student = queue.remove(0);

            // Check if student is already matched
            if (!student.roommate.equals("")) {
                continue;
            }

            // Check if student has proposed and been rejected by every single potential match
            if (preferenceIndeces.get(student.name) >= student.roommatePreferences.size()) {
                continue;
            }

            while (preferenceIndeces.get(student.name) < student.roommatePreferences.size()) {
                String otherStudentName = student.roommatePreferences.get(preferenceIndeces.get(student.name));
                preferenceIndeces.put(student.name, preferenceIndeces.get(student.name) + 1);

                UniversityStudent otherStudent = nameStudentMap.get(otherStudentName);
                if (otherStudent == null)
                    // student name not initialized
                    continue;

                if (otherStudent.roommate.equals("")) {
                    // match roommates
                    student.roommate = otherStudent.name;
                    otherStudent.roommate = student.name;
                    break;
                }
                else {
                    String otherStudentCurrRoommateName = otherStudent.roommate;
                    Integer otherPreference = inversePreferenceList.get(otherStudentName).get(otherStudentCurrRoommateName);
                    Integer currPreference = inversePreferenceList.get(otherStudentName).get(student.name);
                    boolean swap;
                    if (otherPreference == null)
                    {
                        if (currPreference == null)
                            swap = false;
                        else
                            swap = true;
                    }
                    else if (currPreference == null)
                        swap = false;
                    else
                        swap = currPreference > otherPreference;
                    if (swap) {
                        // this preferred to other
                        otherStudent.roommate = student.name;
                        student.roommate = otherStudent.name;
                        UniversityStudent otherStudentCurrRoommate = nameStudentMap.get(otherStudentCurrRoommateName);
                        otherStudentCurrRoommate.roommate = "";
                        queue.add(otherStudentCurrRoommate);
                        break;
                    }
                }
            }
        }

    }
}
