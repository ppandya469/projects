package ngordnet.ngrams;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

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
        for (int i = startYear; i <= endYear; i++) {
            if (ts != null) {
                if (ts.containsKey(i)) {
                    double value = ts.get(i);
                    this.put(i, value);
                }
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> returnList = new ArrayList<>();
        Object[] tempList = this.keySet().toArray();
        for (int i = 0; i < tempList.length; i++) { //converts array to arraylist
            returnList.add((Integer) tempList[i]);
        }
        return returnList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> returnList = new ArrayList<>();
        Object[] tempList = this.keySet().toArray();
        for (int i = 0; i < tempList.length; i++) {
            returnList.add(this.get(tempList[i]));
        }
        return returnList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTimeSeries = new TimeSeries();
        Object[] tempThisList = this.keySet().toArray();
        Object[] tempTsList = ts.keySet().toArray();
        double sum = 0;

        if (ts.isEmpty() && this.isEmpty()) {
            return newTimeSeries;
        }

        for (Object i : tempTsList) {
            if (this.containsKey(i)) {
                sum = this.get(i) + ts.get((Integer) i); //can't get object i???
                newTimeSeries.put((Integer) i, sum);
            } else {
                newTimeSeries.put((Integer) i, ts.get(i));
            }
        }

        for (Object j : tempThisList) {
            if (ts.containsKey(j)) {
                sum = this.get(j) + ts.get((Integer) j); //can't get object j???
                newTimeSeries.put((Integer) j, sum);
            } else {
                newTimeSeries.put((Integer) j, this.get(j));
            }
        }

        return newTimeSeries;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTimeSeries = new TimeSeries();
        Object[] tempThisList = this.keySet().toArray();
        Object[] tempTsList = ts.keySet().toArray();

        double quotient = 0;

        for (Object j : tempThisList) {
            if (!ts.containsKey(j)) {
                throw new IllegalArgumentException();
            }
        }

        for (Object i : tempThisList) {
            if (ts.containsKey(i)) {
                quotient = this.get(i) / ts.get(i);
            } else {
                quotient = this.get(i); //do I insert the value or do I skip adding it? what does ignore mean?
            }
            newTimeSeries.put((Integer) i, quotient);
        }
        return newTimeSeries;
    }
}
