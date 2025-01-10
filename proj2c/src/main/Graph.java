package main;


import java.util.*;

public class Graph {
    private Map<Integer, Set<Integer>> adjVertices = new HashMap<>();

    public void addVertex(int synsetId) {
        adjVertices.putIfAbsent(synsetId, new HashSet<>());
    }

    public void addEdge(int fromSynsetId, int toSynsetId) {
        adjVertices.get(fromSynsetId).add(toSynsetId);
    }

    public Graph reverse() {
        Graph reversedGraph = new Graph();
        for (int vertex : adjVertices.keySet()) {
            reversedGraph.addVertex(vertex);
        }
        for (int vertex : adjVertices.keySet()) {
            for (int neighbor : adjVertices.get(vertex)) {
                reversedGraph.addVertex(neighbor);
                reversedGraph.addEdge(neighbor, vertex);
            }
        }
        return reversedGraph;
    }

    public Set<Integer> findReachableVertices(List<Integer> startVertexIds) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>(startVertexIds);

        while (!queue.isEmpty()) {
            Integer current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                for (Integer neighbor : adjVertices.getOrDefault(current, Collections.emptySet())) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
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