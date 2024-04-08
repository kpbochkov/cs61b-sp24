package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    public char getGuess(String pattern, List<Character> guesses) {
        final Map<Integer, Character> letterIndexes = matchLetterIndexes(pattern);
        List<String> validWords = extractValidWords(pattern);
        final List<String> matchingWords = getMatchingWords(letterIndexes, validWords);
        if (letterIndexes.isEmpty()) {
            return GuesserUtils.getLetter(guesses, validWords);
        }
        return GuesserUtils.getLetter(guesses, matchingWords);
    }

    private List<String> extractValidWords(final String pattern) {
        return words.stream()
                .filter(word -> word.length() == pattern.length())
                .toList();
    }

    private Map<Integer, Character> matchLetterIndexes(final String pattern) {
        // [-e--] -> [beta, deal]
        final char[] charArray = pattern.toCharArray();
        Map<Integer, Character> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (charArray[i] != '-') {
                map.put(i, map.getOrDefault(i, charArray[i]));
            }
        }
        return map;
    }

    private List<String> getMatchingWords(final Map<Integer, Character> map, final List<String> validWords) {
        List<String> matchingWords = new ArrayList<>();
        for (String word : validWords) {
            boolean isMatching = true;
            for (Map.Entry<Integer, Character> entry : map.entrySet()) {
                int index = entry.getKey();
                int value = entry.getValue();
                if (word.charAt(index) != value) {
                    isMatching = false;
                    break;
                }
            }
            if (isMatching) {
                matchingWords.add(word);
            }
        }
        return matchingWords;
    }


    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}