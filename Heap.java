import java.util.Comparator;

public class Heap<T> {
    private T[] arrayHeap;
    Comparator<? super T> comparator;
    public int length;


    private static final int DefaultSize = 2000;

    public Heap(Comparator<? super T> comparator) {
        this(DefaultSize, comparator);
    }

    public Heap(int size, Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.length = 0;

        arrayHeap = (T[]) new Object[size];
    }

    public void addElement(T elementToAdd) {
        arrayHeap[++length] = elementToAdd;
        heapifyBottomUp(length);
    }

    public void decreaseKey(int index, T newElement) {
        arrayHeap[index] = newElement;
        heapifyBottomUp(index);
    }

    public T peek() {
        if(length < 1) {
            return null;
        } else {
            return arrayHeap[1];
        }
    }

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

    private void heapifyTopDown(int index) {
        // System.out.println(index);
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

    public void printHeap() {
        for(int i=1; i<=length; i++) {
            System.out.print(arrayHeap[i] + " ");
        }
        System.out.println();
    }

}
