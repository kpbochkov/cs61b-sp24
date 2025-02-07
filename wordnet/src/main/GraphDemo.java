package main;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphDemo {

    private Digraph digraph;
    private DirectedDFS dfs;

    public Set<Integer> findHyponyms(In in, List<Integer> s) {
        digraph = new Digraph(in);
        dfs = new DirectedDFS(digraph, s);
        Set<Integer> edges = new HashSet<>();
        for (int v = 0; v < digraph.V(); v++) {
            if (dfs.marked(v)) {
                edges.add(v);
            }
        }
        return edges;
    }
}
