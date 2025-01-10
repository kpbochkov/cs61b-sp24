package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private final Graph graph;
    private final Map<String, List<Integer>> wordToSynsetIdMapping;
    private final Map<Integer, Synset> idToSynsetMapping;

    public WordNet(String synsetsFile, String hyponymsFile) {
        graph = new Graph();
        wordToSynsetIdMapping = new HashMap<>();
        idToSynsetMapping = new HashMap<>();
        generateSynsetsMappings(synsetsFile);
        createGraph(hyponymsFile);
    }

    public Set<String> getHyponyms(List<String> words) {
        if (words.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> commonHyponyms = new TreeSet<>(getHyponymsForSingleWord(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            Set<String> hyponyms = getHyponymsForSingleWord(words.get(i));
            commonHyponyms.retainAll(hyponyms);
        }

        return commonHyponyms;
    }

    public Set<String> getAncestors(List<String> words) {
        if (words.isEmpty()) {
            return Collections.emptySet();
        }

        Graph reversedGraph = graph.reverse();
        Set<String> commonAncestors = new TreeSet<>(getAncestorsForSingleWord(words.get(0), reversedGraph));

        for (int i = 1; i < words.size(); i++) {
            Set<String> ancestors = getAncestorsForSingleWord(words.get(i), reversedGraph);
            commonAncestors.retainAll(ancestors);
        }

        return commonAncestors;
    }

    private Set<String> getHyponymsForSingleWord(String word) {
        List<Integer> synsetIds = wordToSynsetIdMapping.get(word);
        if (synsetIds == null) {
            return Collections.emptySet();
        }

        Set<Integer> hyponymIds = graph.findReachableVertices(synsetIds);
        return getWordsForGivenIds(hyponymIds);
    }

    private Set<String> getAncestorsForSingleWord(String word, final Graph reversedGraph) {
        List<Integer> synsetIds = wordToSynsetIdMapping.get(word);
        if (synsetIds == null) {
            return Collections.emptySet();
        }


        Set<Integer> ancestorIds = reversedGraph.findReachableVertices(synsetIds);
        return getWordsForGivenIds(ancestorIds);
    }

    private Set<String> getWordsForGivenIds(Set<Integer> ids) {
        Set<String> words = new HashSet<>();
        for (int id : ids) {
            Synset synset = idToSynsetMapping.get(id);
            if (synset != null) {
                words.addAll(synset.getWords());
            }
        }
        return words;
    }

    private void generateSynsetsMappings(String synsetsFile) {
        In file = new In(synsetsFile);
        while (!file.isEmpty()) {
            String line = file.readLine();
            String[] words = line.split(",");
            if (words.length >= 2) {
                int synsetId = Integer.parseInt(words[0]);
                String[] currentWords = words[1].split(" ");
                Synset synset = idToSynsetMapping.computeIfAbsent(synsetId, Synset::new);

                for (String word : currentWords) {
                    synset.addWord(word);
                    wordToSynsetIdMapping.computeIfAbsent(word, k -> new ArrayList<>()).add(synsetId);
                }
            }
        }
        file.close();
    }

    private void createGraph(String hyponymsFile) {
        In file = new In(hyponymsFile);
        while (!file.isEmpty()) {
            String line = file.readLine();
            String[] words = line.split(",");
            if (words.length > 1) {
                int synsetId = Integer.parseInt(words[0]);
                graph.addVertex(synsetId);
                for (int i = 1; i < words.length; i++) {
                    int hyponymId = Integer.parseInt(words[i]);
                    graph.addVertex(hyponymId);
                    graph.addEdge(synsetId, hyponymId);
                }
            }
        }
        file.close();
    }
}
