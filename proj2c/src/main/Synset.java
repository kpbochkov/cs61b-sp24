package main;

import java.util.*;

public class Synset {
    // Synset is a node-like data structure containing id of Synset and a list of words in the synset
    // for example "5,jump leap saltation" is a synset with Id 5 and the following words as list
    private Integer synsetId;
    private Set<String> words;

    public Synset(Integer synsetId) {
        this.synsetId = synsetId;
        this.words = new HashSet<>();
    }

    public Integer getSynsetId() {
        return synsetId;
    }

    public Set<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        this.words.add(word);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Synset synset = (Synset) o;
        return Objects.equals(synsetId, synset.synsetId) && Objects.equals(words, synset.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(synsetId, words);
    }
}
