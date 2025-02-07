package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsAncestorsHandler extends NgordnetQueryHandler {

    private WordNet wn;
    private NGramMap ngm;

    public HyponymsAncestorsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        if (NgordnetQueryType.HYPONYMS.equals(q.ngordnetQueryType())) {
            Set<String> hyponyms = wn.getHyponyms(words);
            return handleQueryResults(hyponyms, startYear, endYear, k, "hyponyms");
        }

        Set<String> ancestors = wn.getAncestors(words);
        return handleQueryResults(ancestors, startYear, endYear, k, "ancestors");
    }

    private String handleQueryResults(Set<String> results, int startYear, int endYear, int k, String resultType) {
        StringBuilder sb = new StringBuilder();

        if (k < 1) {
            return sb.append(results).toString();
        }

        if (!results.isEmpty()) {
            Map<String, Double> topAverages = calculateTopAverages(results, startYear, endYear);
            return sb.append(HandlerHelper.getTopAverages(topAverages, k)).toString();
        }

        return "There are no " + resultType + " for the corresponding word(s)";
    }

    private Map<String, Double> calculateTopAverages(Set<String> words, int startYear, int endYear) {
        Map<String, Double> topAverages = new HashMap<>();
        for (String word : words) {
            double wordAverage;
            TimeSeries timeSeries = ngm.countHistory(word, startYear, endYear);
            if (timeSeries.isEmpty()) {
                wordAverage = 0.0;
            } else {
                wordAverage = HandlerHelper.getAverage(timeSeries);
            }
            topAverages.put(word, wordAverage);
        }
        return topAverages;
    }
}

