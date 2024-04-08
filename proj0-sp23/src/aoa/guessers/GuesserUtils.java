package aoa.guessers;

import java.util.*;

public class GuesserUtils {

    public static Map<Character, Integer> getFrequencyMap(List<String> words) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                frequencyMap.put(word.charAt(i), frequencyMap.getOrDefault(word.charAt(i), 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static char getLetter(List<Character> guesses, List<String> words) {
        final Map<Character, Integer> frequencyMap = getFrequencyMap(words);
        if (frequencyMap.isEmpty()) {
            return '?';
        }
        Map<Character, Integer> treeMap = new TreeMap<>();
        frequencyMap.entrySet().stream()
                .filter(entry -> !guesses.contains(entry.getKey()))
                .forEach(entry -> treeMap.put(entry.getKey(), entry.getValue()));

        return treeMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('?');

    }

    public static Map<Character, List<Integer>> matchLetterIndexes(final String pattern) {
        // [-e--] -> [beta, deal]
        // [----] ->  ally beta cool  deal else  flew good hope ibex , guesses {e} ans should be 'o'
        final char[] charArray = pattern.toCharArray();
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (charArray[i] != '-') {
                if (!map.containsKey(charArray[i])) {
                    map.put(charArray[i], new ArrayList<>());
                }
                map.get(charArray[i]).add(i);
            }
        }
        return map;
    }

    public static List<String> extractValidWords(final String pattern, final List<String> words) {
        return words.stream()
                .filter(word -> word.length() == pattern.length())
                .toList();
    }
}
