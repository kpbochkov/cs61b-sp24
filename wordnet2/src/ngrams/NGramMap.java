package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private HashMap<String, TimeSeries> wordMapping;
    private TimeSeries wordsPerYearCount;


    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordMapping = new HashMap<>();
        this.wordsPerYearCount = new TimeSeries();
        getWordsFile(wordsFilename);
        getCountsFile(countsFilename);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMapping.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordMapping.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordMapping.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = wordMapping.get(word);
        TimeSeries copy = new TimeSeries();
        copy.putAll(original);
        return copy;
    }


    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result = new TimeSeries();
        wordsPerYearCount.forEach((year, value) -> result.put(year, Double.valueOf(value)));
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordMapping.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries wordDataTS = wordMapping.get(word);
        TimeSeries wordDataInRange = new TimeSeries(wordDataTS, startYear, endYear);
        TimeSeries countWordsInRange = new TimeSeries(wordsPerYearCount, startYear, endYear);
        return wordDataInRange.dividedBy(countWordsInRange);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordMapping.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordDataTS = wordMapping.get(word);
        return wordDataTS.dividedBy(wordsPerYearCount);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            TimeSeries wordFrequency = weightHistory(word, startYear, endYear);
            result = result.plus(wordFrequency);
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            TimeSeries wordFrequency = weightHistory(word);
            result = result.plus(wordFrequency);
        }
        return result;
    }

    private void getCountsFile(final String countsFilename) {
        In countsFile = new In(countsFilename);
        while (!countsFile.isEmpty()) {
            String line = countsFile.readLine();
            String[] words = line.split(",");
            if (words.length >= 2) {
                int year = Integer.parseInt(words[0]);
                if (year >= MIN_YEAR && year <= MAX_YEAR) {
                    String count = words[1];
                    wordsPerYearCount.put(year, Double.valueOf(count));
                }
            }
        }
        countsFile.close();
    }

    private void getWordsFile(final String wordsFilename) {
        In wordsFile = new In(wordsFilename);
        while (!wordsFile.isEmpty()) {
            String line = wordsFile.readLine();
            String[] words = line.split("\t");
            if (words.length >= 3) {
                int year = Integer.parseInt(words[1]);
                if (year >= MIN_YEAR && year <= MAX_YEAR) {
                    String word = words[0];
                    Double value = Double.parseDouble(words[2]);
                    wordMapping.computeIfAbsent(word, k -> new TimeSeries()).put(year, value);
                }
            }
        }
        wordsFile.close();
    }
}
