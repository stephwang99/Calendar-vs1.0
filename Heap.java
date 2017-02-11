/**
 * Heap.java - Heap data structure, primarily used for heat sort
 *
 * @author Ryan Ly
 * @version 1.00 2017/01/07
 */
public class Heap {

    private Object[] heap = new Object[256];
    private int size;

    public Heap(){
        size = 0;
    }

    public Heap(Object cargo){
        heap[0] = cargo;
        size = 1;
    }

    //Takes an array of cargo, adding them one at a time to create a valid array
    public Heap(Object[] cargo){
        for(int i = 0;i < cargo.length;i++){
            add(cargo[i]);
        }
        size = cargo.length;
    }

    //Returns cargo at specified index
    public Object getCargo(int index){
        return heap[index];
    }

    public void setCargo(int index, Object cargo){
        heap[index] = cargo;
    }

    public int getSize(){
        return size;
    }

    public Object[] getHeap(){
        return heap;
    }

    //Left child index is defined as n * 2 + 1
    public int getLeft(int index){
        return index * 2 + 1;
    }
    //Right child index is defined as n * 2 + 2
    public int getRight(int index){
        return index * 2 + 2;
    }
    //Parent is defined as n / 2 - 1, rounding down
    public int getParent(int index){
        return index / 2 - 1;
    }

    //Adds the cargo to the end of the heap, then reheapify
    public void add(Object cargo){
        heap[size] = cargo;
        reheapify(0);
        size++;
    }
    //Reheapify for a min heap starting at 0
    //Ensures heapness by making all children are larger than parents
    private void reheapify(int index){
        //If in an index that exists in the heap and is not a leaf
        if(index >= 0 && getRight(index) <  size) {
            //Finds the smaller of the two children
            if (((Task) heap[getLeft(index)]).compareTo(heap[getRight(index)]) < 0) {
                //If that node is smaller than the parent, swap them
                if (((Task) heap[index]).compareTo(heap[getLeft(index)]) > 0) {
                    swap(index, getLeft(index));
                }
                //Same as above, but for the right child
            } else {
                if (((Task) heap[index]).compareTo(heap[getRight(index)]) > 0) {
                    swap(index, getRight(index));
                }
            }
            //Handles two nodes (one parent, one child)
        } else if(size == 2){
            //If the child is smaller than parent, swap them
            if(((Task)heap[0]).compareTo(heap[getLeft(0)]) > 0){
                swap(index, getLeft(index));
            }
        }
        //If not at a leaf or outside of heap, recurse with each child
        if((index != size - 1 || index != size -2) && index < size) {
            reheapify(getLeft(index));
            reheapify(getRight(index));
        }
    }

    //Swaps with last item, then reduces size by one to remove item
    public Object remove(){
        swap(0, size - 1);
        size--;
        reheapify(0);
        //Returns the item swapped
        return heap[size];
    }
    //Swaps the cargos of two indices
    private void swap(int index1, int index2){
        Object temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    //Heap sort
    public void sort(){
        int originalSize = size;
        for(int i = 0;i < originalSize;i++){
            //Remove first node, then reheapify
            //Repeats until all nodes have been removed
            remove();
        }
        //Sets size back to the original size
        size = originalSize;
    }
}
