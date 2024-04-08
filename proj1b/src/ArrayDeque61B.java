import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private static int ARRAY_SIZE = 8;
    private static int EXPAND_FACTOR = 2;
    private int size;
    private int nextFirstIndex;
    private int nextLastIndex;
    // invariants : size should return always the number of elements in the deque
    //              the last item to add is always at position lastItemIndex
    //              the first item to add is always at position firstItemIndex
    //              start of the array is always at index nextFirstIndex + 1

    private T[] items;

    public ArrayDeque61B() {
        items = (T[]) new Object[ARRAY_SIZE];
        size = 0;
        nextFirstIndex = 0;
        nextLastIndex = 1;
    }

    @Override
    public void addFirst(final T x) {
        if (needResize()) {
            resizeArray();
        }
        items[nextFirstIndex] = x;
        nextFirstIndex = Math.floorMod(nextFirstIndex - 1, items.length);
        size++;
    }

    @Override
    public void addLast(final T x) {
        if (needResize()) {
            resizeArray();
        }
        items[nextLastIndex] = x;
        nextLastIndex = Math.floorMod(nextLastIndex + 1, items.length);
        size++;
    }

    @Override
    public List<T> toList() {
        if (isEmpty()) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>();
        int currentIndex = Math.floorMod(nextFirstIndex + 1, items.length);
        for (int i = 0; i < size; i++) {
            list.add(items[currentIndex]);
            currentIndex = Math.floorMod(currentIndex + 1, items.length);
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int deleteAtIndex = Math.floorMod(nextFirstIndex + 1, items.length);
        T itemToDelete = items[deleteAtIndex];
        items[deleteAtIndex] = null;
        nextFirstIndex = Math.floorMod(nextFirstIndex + 1, items.length);
        size--;

        return itemToDelete;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int deleteAtIndex = Math.floorMod(nextLastIndex - 1, items.length);
        T itemToDelete = items[deleteAtIndex];
        items[deleteAtIndex] = null;
        nextLastIndex = Math.floorMod(nextLastIndex - 1, items.length);
        size--;

        return itemToDelete;
    }

    @Override
    public T get(final int index) {
        if (isEmpty() || isOutOfBounds(index)) {
            return null;
        }
        int actualIndex = Math.floorMod(nextFirstIndex + 1 + index, items.length);
        return items[actualIndex];
    }

    @Override
    public T getRecursive(final int index) {
        return null;
    }

    private void resizeArray() {
        final T[] newArray = (T[]) new Object[ARRAY_SIZE * EXPAND_FACTOR];
        int iterationCounter = 0;
        for (int i = Math.floorMod(nextFirstIndex + 1, items.length); iterationCounter < size; i = Math.floorMod(i + 1, items.length)) {
            newArray[iterationCounter] = items[i];
            iterationCounter++;
        }
        nextFirstIndex = newArray.length - 1;
        nextLastIndex = size;
        items = newArray;
    }

    private boolean needResize() {
        return size == items.length;
    }

    private boolean isOutOfBounds(final int index) {
        return index < 0 || index > size;
    }

    public static void main(String[] args) {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
    }
}
//        int count = 0;
//        for (int i = nextFirstIndex + 1; i < items.length; i++) {
//            if (items[i] != null) {
//                list.add(items[i]);
//                count++;
//            }
//        }
//        int remainingIndexes = size - count;
//        for (int i = 0; i < remainingIndexes; i++) {
//            if (items[i] != null) {
//                list.add(items[i]);
//            }
//        }
