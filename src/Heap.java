/*
 * Name: Adam Herring
 * EID: ah58665
 */


import java.util.ArrayList;

/**
 * This is a class that I mostly implemented in my algos class where I already wrote the
 * code for makeing a min heap
 */

public class Heap<HeapMemberGeneric extends HeapMember> {
    private ArrayList<HeapMemberGeneric> minHeap;

    public Heap() {
        minHeap = new ArrayList<HeapMemberGeneric>();
    }


    private void swap(int parent, int child){
        HeapMemberGeneric swapedHeapMember = minHeap.get(child);
        swapedHeapMember.setIndex(parent);
        minHeap.get(parent).setIndex(child);
        minHeap.set(child,minHeap.get(parent));
        minHeap.set(parent,swapedHeapMember);
    }

    private void heapifyUp(int i)
    {
        int parent;
        if (i == 0) {
            parent = 0;
        }else parent = (i-1)/2;

        if (minHeap.get(parent).getValue() > minHeap.get(i).getValue() && i > 0)
        {
            HeapMemberGeneric swapedHeapMember = minHeap.get(i);
            swapedHeapMember.setIndex(parent);
            minHeap.get(parent).setIndex(i);
            minHeap.set(i,minHeap.get(parent));
            minHeap.set(parent,swapedHeapMember);
            heapifyUp(parent);
        }
    }

    private void heapifyDown(int i)
    {
        int smallest = i;
        int l = (2*i) + 1;
        int r = (2*i) + 2;

        if (l < minHeap.size() && minHeap.get(l).getValue() < minHeap.get(smallest).getValue()){
            smallest = l;
        }

        if (r < minHeap.size() && minHeap.get(r).getValue() < minHeap.get(smallest).getValue()){
            smallest = r;
        }

        if (smallest != i){
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    /**
     * buildHeap(ArrayList<HeapMember> heap_members)
     * Given an ArrayList of Regions, build a min-heap keyed on each HeapMember's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param heap_members
     */
    public void buildHeap(ArrayList<HeapMemberGeneric> heap_members) {

        for(int i = 0; i < heap_members.size(); i++){
            heap_members.get(i).setIndex(i);
            minHeap.add(heap_members.get(i));
        }
        int firstIndex = (heap_members.size()/2)-1;

        for(int k = firstIndex; k > -1; k--){
            heapifyDown(k);
        }
    }

    /**
     * insertNode(HeapMemberGeneric in)
     * Insert a HeapMemberGeneric into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the HeapMemberGeneric to insert.
     */
    public void insertNode(HeapMemberGeneric in) {
        minHeap.add(in);
        in.setIndex(minHeap.size() - 1);
        heapifyUp(minHeap.size() - 1);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public HeapMemberGeneric findMin() {
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public HeapMemberGeneric extractMin() {
        HeapMemberGeneric min = minHeap.get(0);
        min.setIndex(-1);
        minHeap.set(0, minHeap.get(minHeap.size() - 1));
        minHeap.remove(minHeap.size() - 1);
        if (minHeap.size() > 0){
            minHeap.get(0).setIndex(0);
            heapifyDown(0);
        }
        return min;
    }

    /**
     * delete(HeapMemberGeneric gen)
     * Deletes an element in the min-heap given a member to delete.
     * Sets index to -1 to indicate that the HeapMemberGeneric is no longer in the heap.
     * Time Complexity - O(log(n))
     *
     * @param gen - the member to be deleted in the min-heap.
     */
    public void delete(HeapMemberGeneric gen) {
        int index = gen.getIndex();
        minHeap.set(index,minHeap.get(minHeap.size() - 1));
        minHeap.remove(minHeap.size() - 1);
        heapifyDown(index);
        gen.setIndex(-1);
    }

    /**
     * changeKey(HeapMemberGeneric r, int newKey)
     * Changes the attribute value of HeapMemberGeneric s to newKey and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the HeapMemberGeneric in the heap that needs to be updated.
     * @param newKey - the new value of HeapMemberGeneric r in the heap
     */
    public void changeKey(HeapMemberGeneric r, int newKey) {
        boolean upOrDown = (newKey > r.getValue()) ? true : false;
        r.setValue(newKey);
        if (upOrDown){
            heapifyDown(r.getIndex());
        } else {
            heapifyUp(r.getIndex());
        }
    }

    /*
     *  contains(HeapMemberGeneric m)
     *  Checks if the HeapMemberGeneric m is in the heap.
     *  HeapMemberGeneric m, must have been added to the heap previously.
     *  Time Complexity - O(1)
     *
     *  @param m - the HeapMemberGeneric to check if it is in the heap.
     *
     */

    public boolean contains(HeapMemberGeneric m) {
        if(m.getIndex() == -1) return false;
        return true;
    }

    public int getSize() {
        return minHeap.size();
    }


    /* Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */

    public ArrayList<HeapMemberGeneric> toArrayList() {
        return minHeap;
    }

    public void clear(){
        minHeap.clear();
    }

}

