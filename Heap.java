import java.util.Comparator;

/*
 * Heap implementation. Heap will work for any type of elements. Heap accepts comparator of that element type.
 */
public class Heap<T> {
    private T[] arrayHeap;
    Comparator<? super T> comparator;
    public int length;
    private static final int DEFAULT_SIZE = 2000;

    public Heap(Comparator<? super T> comparator) {
        this(DEFAULT_SIZE, comparator);
    }

    private Heap(int size, Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.length = 0;

        arrayHeap = (T[]) new Object[size];
    }

    /*
     * Add element to the heap.
     * It will add element to the end of the tree and then calls  heapifyBottomUp method.
     */
    public void addElement(T elementToAdd) {
        arrayHeap[++length] = elementToAdd;
        heapifyBottomUp(length);
    }

    /*
     * This returns the minimum element of the heap i.e the first value of the heap.
     */
    public T peek() {
        if(length < 1) {
            return null;
        } else {
            return arrayHeap[1];
        }
    }

    /*
     * Return and remove the minimum element of the heap.
     * It will return the first element of the heap and replaces that first element with the last element,
     * after which it calls heapifyTopDown.
     */
    public T poll() {
        if(length < 1) {
            return null;
        }
        if(length == 1) {
            length--;
            return arrayHeap[1];

        }
        swapElements(length--, 1);
        heapifyTopDown(1);
        return arrayHeap[length+1];
    }

    /*
     * It Heapifies the heap elements,
     * by recuresively comparing given node with left child and right child and doing swaps if applicable.
     */
    private void heapifyTopDown(int index) {
        if(index >= length) return;
        T leftChild = index*2 <= length ? arrayHeap[index*2]: null;
        T rightChild = index*2 + 1 <= length ? arrayHeap[index*2 + 1]: null;

        T maxOrMin = arrayHeap[index];
        int maxOrMinIndex = -1;
        if(rightChild != null && comparator.compare(rightChild, maxOrMin) > 0) {
            maxOrMin = rightChild;
            maxOrMinIndex = index*2 + 1;
        }
        if(leftChild != null && comparator.compare(leftChild, maxOrMin) > 0) {
            maxOrMinIndex = index*2;
        }

        if(maxOrMinIndex != -1) {
            swapElements(index, maxOrMinIndex);
            heapifyTopDown(maxOrMinIndex);
        }
    }

    /*
     * It Heapifies the heap elements bottom up by comparing node value with the parent recursively and doing swap if applicable.
     */
    private void heapifyBottomUp(int index) {
        boolean swapped = true;
        while(swapped && index > 1) {
            swapped = false;
            T child = arrayHeap[index];
            T parent = arrayHeap[index/2];
            if (comparator.compare(child, parent) > 0) {
                swapElements(index, index/2);
                swapped = true;
            }
            index /= 2;
        }
    }

    private void swapElements(int index1, int index2) {
        T temp = arrayHeap[index1];
        arrayHeap[index1] = arrayHeap[index2];
        arrayHeap[index2] = temp;
    }
}
