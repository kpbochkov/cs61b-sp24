package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /* Instance Variables */

    private Collection<Node>[] buckets;
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private double loadFactor;
    private int tableSize = 0; // number of buckets
    private int size;// number of elements in the hashmap
    private HashSet<K> keySet;

    /**
     * Constructors
     */
    public MyHashMap() {
        this(INITIAL_CAPACITY, LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor      maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.size = 0;
        this.tableSize = initialCapacity;
        this.loadFactor = loadFactor;
        keySet = new HashSet<>();
        buckets = createTable();
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * Note that that this is referring to the hash table bucket itself,
     * not the hash map itself.
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    @Override
    public void put(final K key, final V value) {
        if (key == null) {
            return;
        }
        if ((double) (size() + 1) / tableSize > loadFactor) {
            buckets = resize();
        }
        int index = findBucketIndex(key);

        for (Node node : buckets[index]) {
            if (key.equals(node.key)) {
                node.value = value;
                return;
            }
        }

        Node newNode = new Node(key, value);
        buckets[index].add(newNode);
        size++;
    }

    @Override
    public V get(final K key) {
        if (key == null) {
            return null;
        }
        int index = findBucketIndex(key);

        for (Node node : buckets[index]) {
            if (key.equals(node.key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(final K key) {
        int index = findBucketIndex(key);
        for (Node node : buckets[index]) {
            if (key.equals(node.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        buckets = createTable();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(final K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /* ------------------------------- Private methods ------------------------------- */

    private Collection<Node>[] resize() {
        tableSize = tableSize * 2;
        Collection<Node>[] newBuckets = createTable();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int index = findBucketIndex(node.key);
                newBuckets[index].add(node);
            }
        }
        return newBuckets;
    }

    private Collection<Node>[] createTable() {
        Collection<Node>[] newCollection = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            newCollection[i] = createBucket();
        }
        return newCollection;
    }

    private int findBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), tableSize);
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {

        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }
}
