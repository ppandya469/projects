package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap myNGM;

    public HistoryHandler(NGramMap ngm) {
        myNGM = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();

        for (String i : words) {
            TimeSeries holder = myNGM.weightHistory(i, startYear, endYear);
            lts.add(holder);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}

