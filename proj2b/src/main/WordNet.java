package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {

    private Graph graph;
    private Map<String, List<Integer>> wordToSynsetIdMapping;
    private Map<Integer, Synset> idToSynsetMapping;

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

        Set<String> commonHyponyms = getHyponymsForSingleWord(words.get(0));

        for (int i = 1; i < words.size(); i++) {
            Set<String> hyponyms = getHyponymsForSingleWord(words.get(i));
            commonHyponyms.retainAll(hyponyms);
        }

        return commonHyponyms;
    }

    private Set<String> getHyponymsForSingleWord(String word) {
        List<Integer> integers = wordToSynsetIdMapping.get(word);
        if (integers == null) {
            return Collections.emptySet();
        }

        Set<Integer> hyponymsIds = graph.findReachableVertices(integers);
        Set<Synset> synsetsForGivenIds = getSynsetsForGivenIds(hyponymsIds);

        Set<String> hyponyms = new HashSet<>();
        for (Synset synset : synsetsForGivenIds) {
            final Set<String> words = synset.getWords();
            hyponyms.addAll(words);
        }
        return hyponyms;
    }

    private Set<Synset> getSynsetsForGivenIds(Set<Integer> ids) {
        Set<Synset> synsets = new HashSet<>();
        for (int id : ids) {
            synsets.add(idToSynsetMapping.get(id));
        }
        return synsets;
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
                Synset synset = idToSynsetMapping.get(Integer.parseInt(words[0]));
                graph.addVertex(synset);
                for (int i = 1; i < words.length; i++) {
                    Synset currentHyponym = idToSynsetMapping.get(Integer.parseInt(words[i]));
                    graph.addEdge(synset, currentHyponym);
                }
            }
        }
        file.close();
    }
    // test purposes
    // ToDo to delete this methods

    public List<Integer> getSynsetIdsOfAWord(String word) {
        return wordToSynsetIdMapping.getOrDefault(word, Collections.emptyList());
    }

}
