package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wn;

    public HyponymsHandler(WordNet wn) {
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder sb = new StringBuilder("");
        Set<String> hyponyms = wn.getHyponyms(words);

        if (!hyponyms.isEmpty()) {
            sb.append(hyponyms);
            return sb.toString();
        }

        return "There is no hyponyms for the corresponding word";
    }
}
