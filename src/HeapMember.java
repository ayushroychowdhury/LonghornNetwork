
public abstract class HeapMember {

    private int value;
    private int index;
    private UniversityStudent student;

    // constructor

    public HeapMember(UniversityStudent input) {
        value = Integer.MAX_VALUE;
        index = -1;
        this.student = input;
    }

    public HeapMember(int x, int i) {
        value = x;
        index = i;
    }

    public HeapMember(int x, int i, UniversityStudent input) {
        value = x;
        index = i;
        student = input;
    }

    public int getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public void setValue(int x) {
        value = x;
    }

    public void setIndex(int i) {
        index = i;
    }

    public UniversityStudent getStudent() {
        return student;
    }

    public void reset(){
        value = Integer.MAX_VALUE;
        index = -1;
    }

}

