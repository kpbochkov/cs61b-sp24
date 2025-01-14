import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;
    private int size;

    public BSTMap() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public void put(final K key, final V value) {
        root = putHelper(root, key, value);
    }

    private Node putHelper(Node bst, K key, V value) {
        if (bst == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(bst.key);
        if (cmp < 0) {
            bst.left = putHelper(bst.left, key, value);
        } else if (cmp > 0) {
            bst.right = putHelper(bst.right, key, value);
        } else {
            bst.value = value;
        }
        return bst;
    }

    @Override
    public V get(final K key) {
        Node node = find(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(final K key) {
        return find(root, key) != null;
    }

    private Node find(Node root, K key) {
        if (root == null || key == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        } else if (cmp < 0) {
            return find(root.left, key);
        } else {
            return find(root.right, key);
        }
    }

    @Override
    public V remove(final K key) {
        Node nodeToDelete = find(root, key);
        if (nodeToDelete == null) {
            return null;
        }
        V returnValue = nodeToDelete.value;

        root = removeHelper(root, key);
        size--; // Decrement the size
        return returnValue;
    }

    private Node removeHelper(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeHelper(node.left, key);
        } else if (cmp > 0) {
            node.right = removeHelper(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node predecessorNode = findPredecessorNode(node.left);
                node.key = predecessorNode.key; // Replace node's key with predecessor's key
                node.value = predecessorNode.value;
                node.left = removePredecessorNode(node.left);
            }
        }
        return node;
    }

    private Node findPredecessorNode(Node node) {
        if (node.right == null) {
            return node;
        }
        return findPredecessorNode(node.right);
    }

    private Node removePredecessorNode(Node node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = removePredecessorNode(node.right);
        return node;
    }


    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
    }
}

