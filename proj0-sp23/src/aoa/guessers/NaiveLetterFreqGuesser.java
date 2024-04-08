package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return GuesserUtils.getLetter(guesses, words);
    }

    /**
     * Returns a map from a given letter to its frequency across all words.
     * This task is similar to something you did in hw0b!
     */


    /**
     * Returns the most common letter in WORDS that has not yet been guessed
     * (and therefore isn't present in GUESSES).
     */


    public List<String> getWords() {
        return words;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + GuesserUtils.getFrequencyMap(nlfg.words));

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + GuesserUtils.getLetter(guesses, nlfg.words));
    }
}
