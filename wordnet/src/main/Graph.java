package main;


import java.util.*;

public class Graph {

    private Map<Integer, Set<Synset>> adjVertices = new HashMap<>();

    public void addVertex(Synset synset) {
        adjVertices.putIfAbsent(synset.getSynsetId(), new HashSet<>());
    }


    public void addEdge(Synset syn1From, Synset syn2IdTo) {
        adjVertices.get(syn1From.getSynsetId()).add(syn2IdTo);
    }


    public Set<Synset> getAdjVertices(int vertex) {
        return adjVertices.getOrDefault(vertex, Collections.emptySet());
    }

    public Set<Integer> findReachableVertices(List<Integer> sources) {
        Set<Integer> visited = new HashSet<>();
        for (int source : sources) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);
            visited.add(source);

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                for (Synset synset : getAdjVertices(vertex)) {
                    int neighbor = synset.getSynsetId();
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return visited;
    }

}


// DFS
//public Set<Synset> getAdjVertices(int vertex) {
//    return adjVertices.getOrDefault(vertex, Collections.emptySet());
//}
//
//public Set<Integer> getReachableVerticesDFS(int start) {
//    Set<Integer> visited = new HashSet<>();
//    dfs(start, visited);
//    return visited;
//}
//
//private void dfs(int vertex, Set<Integer> visited) {
//    visited.add(vertex);
//    for (Synset synset : getAdjVertices(vertex)) {
//        int neighbor = synset.getId();
//        if (!visited.contains(neighbor)) {
//            dfs(neighbor, visited);
//        }
//    }
//}