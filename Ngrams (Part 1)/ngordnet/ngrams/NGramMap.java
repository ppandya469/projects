package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private Map<String, TimeSeries> wordsMap;
    private TimeSeries countTS;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */

    public NGramMap(String wordsFilename, String countsFilename) {
        In wordIn = new In(wordsFilename);
        In countIn = new In(countsFilename);

        wordsMap = new HashMap<>(); //initialize wordsMap to empty HashMap
        countTS = new TimeSeries(); //initialize countsTS to empty TimeSeries

        while (wordIn.hasNextLine()) { //storing values in wordsMap
            String[] lineHolder;
            String line = wordIn.readLine();
            lineHolder = line.split("[\t]+");

            String key1 = lineHolder[0];
            int value1 = Integer.parseInt(lineHolder[1]);
            double value2 = Double.parseDouble(lineHolder[2]);

            if (wordsMap.containsKey(key1)) {
                wordsMap.get(key1).put(value1, value2);
            } else {
                TimeSeries placeHolder = new TimeSeries();
                placeHolder.put(value1, value2);
                wordsMap.put(key1, placeHolder);
            }
        }

        while (countIn.hasNextLine()) { //storing values in countsTS
            String[] lineHolder;
            String line = countIn.readLine();
            lineHolder = line.split(",");

            int value1 = Integer.parseInt(lineHolder[0]);
            double value2 = Double.parseDouble(lineHolder[1]);
            countTS.put(value1, value2);
        }
    }

    public static void main(String[] args) {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
        TimeSeries ngmTS = ngm.weightHistory("airport", MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordsMap.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(wordsMap.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countTS, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries countHistoryTS = countHistory(word, startYear, endYear);
        TimeSeries totalCountTS = totalCountHistory();
        return countHistoryTS.dividedBy(totalCountTS);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries countHistoryTS = countHistory(word, MIN_YEAR, MAX_YEAR);
        TimeSeries totalCountTS = totalCountHistory();
        return countHistoryTS.dividedBy(totalCountTS);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedWeightHistoryTS = new TimeSeries();
        for (String i : words) {
            TimeSeries weightHistoryTS = weightHistory(i, startYear, endYear);
            summedWeightHistoryTS = summedWeightHistoryTS.plus(weightHistoryTS);
        }
        return summedWeightHistoryTS;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedWeightHistoryTS = new TimeSeries();
        for (String i : words) {
            TimeSeries weightHistoryTS = weightHistory(i, MIN_YEAR, MAX_YEAR);
            summedWeightHistoryTS = summedWeightHistoryTS.plus(weightHistoryTS);
        }
        return summedWeightHistoryTS;
    }
}
