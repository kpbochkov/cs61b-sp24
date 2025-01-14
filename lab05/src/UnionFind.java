import static java.lang.Math.abs;

public class UnionFind {

    private int[] disjointSet;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        disjointSet = new int[N];
        for (int i = 0; i < N; i++) {
            disjointSet[i] = -1;
        }
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1, v2);
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        return rootV1 == rootV2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        validate(v, 0);
        int root = v;
        while (parent(root) > -1) {
            root = parent(root);
        }

        // path compression
        int currentParent;
        while (v != root) {
            currentParent = parent(v);
            disjointSet[v] = root;
            v = currentParent;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        validate(v1, v2);
        if (connected(v1, v2)) return;

        int rootV1 = find(v1);
        int rootV2 = find(v2);

        int sizeV1 = sizeOf(v1);
        int sizeV2 = sizeOf(v2);

        if (sizeV1 <= sizeV2) {
            disjointSet[rootV1] = rootV2;
            disjointSet[rootV2] = -(sizeV1 + sizeV2);
        } else {
            disjointSet[rootV2] = rootV1;
            disjointSet[rootV1] = -(sizeV1 + sizeV2);
        }
    }

    // for test purpose
    public int[] getUnderlyingArray() {
        return disjointSet;
    }

    /* Returns the size of the set V belongs to. */
    private int sizeOf(int v) {
        return abs(disjointSet[find(v)]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    private int parent(int v) {
        int d = disjointSet[v];
        return d;
    }

    private void validate(int v1, int v2) {
        if (v1 >= disjointSet.length || v1 < 0 || v2 >= disjointSet.length || v2 < 0) {
            throw new IllegalArgumentException("Input data is not valid.");
        }
    }
}
