package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        final List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength).stream().sorted().toList();
        if (words.isEmpty()) {
            throw new IllegalStateException(String.format("word length %s", words.size()));
        }
        int rdmNumber = StdRandom.uniform(words.size());
        chosenWord = words.get(rdmNumber);
        pattern = "";
        for (int i = 0; i < wordLength; i++) {
            pattern += '-';
        }
    }

    @Override
    public int makeGuess(char letter) {
        int count = 0;
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                pattern = pattern.substring(0, i) + letter + pattern.substring(i + 1);
                count++;
            }
        }
        return count;
    }


    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
