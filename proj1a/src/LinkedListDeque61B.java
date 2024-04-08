import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(final T x) {
        Node lastFirst = sentinel.next;
        sentinel.next = new Node(x, sentinel, lastFirst);
        lastFirst.prev = sentinel.next;
        size++;
    }

    @Override
    public void addLast(final T x) {
        Node previousLast = sentinel.prev;
        sentinel.prev = new Node(x, previousLast, sentinel);
        previousLast.next = sentinel.prev;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
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
        Node firstNode = sentinel.next;
        if (firstNode != null) {
            Node newFirst = firstNode.next;
            sentinel.next = newFirst;
            newFirst.prev = sentinel;
            size--;
            return firstNode.item;
        }
        return null;
    }

    @Override
    public T removeLast() {
        Node lastNode = sentinel.prev;
        if (lastNode != null) {
            Node newLast = lastNode.prev;
            sentinel.prev = newLast;
            newLast.next = sentinel;
            size--;
            return lastNode.item;
        }
        return null;
    }

    @Override
    public T get(final int index) {
        Node p = sentinel.next;
        int currIndex = 0;
        while (p != sentinel) {
            if (currIndex == index) {
                return p.item;
            }
            currIndex++;
            p = p.next;
        }
        return null;
    }

    @Override
    public T getRecursive(final int index) {
        return getRecursive(index, sentinel.next, 0);
    }

    private T getRecursive(final int index, Node node, int currIndex) {
        if (currIndex > index) {
            return null;
        }
        if (currIndex == index) {
            return node.item;
        }
        currIndex++;
        return getRecursive(index, node.next, currIndex);
    }

    private class Node {

        T item;
        Node prev;
        Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
