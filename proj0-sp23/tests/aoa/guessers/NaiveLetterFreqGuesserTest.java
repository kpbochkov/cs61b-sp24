package aoa.guessers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

public class NaiveLetterFreqGuesserTest {

    @Order(1)
    @DisplayName("NaiveLetterFreqGuesser returns correct frequency map (small input)")
    @Test
    public void testFreqMapSmallFile() {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        Map<Character, Integer> freqMap = GuesserUtils.getFrequencyMap(nlfg.getWords());

        // y should occur once.
        assertThat(freqMap.get('y')).isEqualTo(1);

        // z shouldn't be present.
        assertThat(freqMap.containsKey('z')).isFalse();

        // a should appear three times.
        assertThat(freqMap.get('a')).isEqualTo(3);
    }

    @Order(2)
    @DisplayName("NaiveLetterFreqGuesser returns correct frequency map (large input)")
    @Test
    public void testFreqMapLargeFile() {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/sorted_scrabble.txt");
        Map<Character, Integer> freqMap = GuesserUtils.getFrequencyMap(nlfg.getWords());

        // y should occur 17,313 times.
        assertThat(freqMap.get('y')).isEqualTo(17313);

        // a should appear 80,229 times.
        assertThat(freqMap.get('a')).isEqualTo(80229);
    }

    @Order(3)
    @DisplayName("NaiveLetterFreqGuesser returns correct guess based on previous guesses")
    @Test
    public void testGetGuess() {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary.
        char guess = GuesserUtils.getLetter(List.of(), nlfg.getWords());
        assertThat(guess).isEqualTo('e');

        // check that the next guess is l if someone has already guessed e.
        guess = GuesserUtils.getLetter(List.of('e'), nlfg.getWords());
        assertThat(guess).isEqualTo('l');

        // check that the next guess is b if someone has already guessed l, o, x, a, e (in that order).
        guess = GuesserUtils.getLetter(List.of('l', 'o', 'x', 'a', 'e'), nlfg.getWords());
        assertThat(guess).isEqualTo('b');
    }
}
