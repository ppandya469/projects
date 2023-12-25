package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap myNGM;

    public HistoryTextHandler(NGramMap ngm) {
        myNGM = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String holderStrings = "";

        for (String i : words) {
            String holder = myNGM.weightHistory(i, startYear, endYear).toString();
            String r1 = i + ": " + holder + "\n";
            holderStrings = holderStrings + r1;
        }

        return holderStrings;
    }
}
