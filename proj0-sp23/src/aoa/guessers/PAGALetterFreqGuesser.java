package aoa.guessers;

import aoa.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static aoa.guessers.GuesserUtils.extractValidWords;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        final Map<Character, List<Integer>> letterIndexes = GuesserUtils.matchLetterIndexes(pattern);
        List<String> validWords = extractValidWords(pattern, words);
        final List<String> matchingWords = getMatchingWords(letterIndexes, validWords, guesses);
        return GuesserUtils.getLetter(guesses, matchingWords);
    }

    private List<String> getMatchingWords(final Map<Character, List<Integer>> map, final List<String> validWords, final List<Character> guesses) {
        Set<Character> wrongGuesses = guesses.stream()
                .filter(guess -> !map.containsKey(guess))
                .collect(Collectors.toSet());

        return validWords.stream()
                .filter(word -> isMatchingWord(word, map, wrongGuesses))
                .collect(Collectors.toList());
    }

    private boolean isMatchingWord(String word, Map<Character, List<Integer>> map, Set<Character> wrongGuesses) {
        return map.entrySet().stream().allMatch(entry -> {
            char ch = entry.getKey();
            List<Integer> indexes = entry.getValue();
            return indexes.stream().allMatch(index -> word.charAt(index) == ch) &&
                    indexes.size() == StringUtils.countMatches(word, ch);
        }) && wrongGuesses.stream().noneMatch(ch -> word.contains(ch.toString()));
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
