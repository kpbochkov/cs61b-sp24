package ngrams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /**
     * If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here.
     */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        TimeSeries newTS = ts.entrySet().stream()
                .filter(entry -> entry.getKey() >= startYear && entry.getKey() <= endYear)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, TimeSeries::new));
        this.clear();
        this.putAll(newTS);
    }


    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>();
        for (Integer year : this.years()) {
            dataList.add(this.get(year));
        }
        return dataList;
    }


    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     * <p>
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();

        Set<Integer> allYears = new HashSet<>(this.years());
        allYears.addAll(ts.years());

        for (Integer year : allYears) {
            Double thisData = this.get(year);
            Double tsData = ts.get(year);

            if (thisData != null) {
                newTS.put(year, thisData);
            }

            if (tsData != null) {
                if (!newTS.containsKey(year)) {
                    newTS.put(year, tsData);
                } else if (thisData != null) {
                    newTS.put(year, thisData + tsData);
                }
            }
        }
        return newTS;
    }



    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     * <p>
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();

        for (Integer year : this.years()) {
            Double thisData = this.get(year);
            Double tsData = ts.get(year);

            if (tsData == null) {
                throw new IllegalArgumentException();
            }
            double quotient = thisData / tsData;
            newTS.put(year, quotient);
        }
        return newTS;
    }
}
