package main;

import ngrams.TimeSeries;

import java.util.*;
import java.util.stream.Collectors;

public class HandlerHelper {

    public static double getAverage(TimeSeries ts) {
        return ts.values().stream()
                .mapToDouble(value -> (Double) value)
                .average()
                .orElseThrow(() -> new IllegalStateException("TimeSeries should not be empty"));
    }

    public static Set<String> getTopAverages(Map<String, Double> topAverages, int n) {
        return topAverages.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
