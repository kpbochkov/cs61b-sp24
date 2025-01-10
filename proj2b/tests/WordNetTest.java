import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;


public class WordNetTest {
    private static final String PREFIX = "./data/wordnet/";

    public static final String SYNSETS11_FILE = PREFIX + "synsets11.txt";
    public static final String HYPONYMS11_FILE = PREFIX + "hyponyms11.txt";
    public static final String SYNSETS16_FILE = PREFIX + "synsets16.txt";
    public static final String HYPONYMS16_FILE = PREFIX + "hyponyms16.txt";
    public static final String HYPONYMS_FILE = PREFIX + "hyponyms.txt";
    public static final String SYNSETS_FILE = PREFIX + "synsets.txt";

    @Test
    public void getHyponymsWithSynsets11FileTest() {
        WordNet wn = new WordNet(SYNSETS11_FILE, HYPONYMS11_FILE);

        Set<String> result = wn.getHyponyms(Collections.singletonList("descent"));

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.contains("descent")).isTrue();
    }

    @Test
    public void getHyponymsWithSynsets16FileTest() {
        WordNet wn = new WordNet(SYNSETS16_FILE, HYPONYMS16_FILE);

        Set<String> result = wn.getHyponyms(Collections.singletonList("change"));

        assertThat(result.size()).isEqualTo(10);
        assertThat(result.contains("demotion")).isTrue();
    }

    @Test
    public void synsetsCointainingGivenWordTest() {
        WordNet wn = new WordNet(SYNSETS16_FILE, HYPONYMS16_FILE);

        assertThat(wn.getSynsetIdsOfAWord("change")).isEqualTo(List.of(2, 8));
    }

    @Test
    public void getHyponymsWithLargeFileTest() {
        WordNet wn = new WordNet(SYNSETS_FILE, HYPONYMS_FILE);

        Set<String> result = wn.getHyponyms(List.of("video", "recording"));

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.contains("videocassette")).isTrue();
        assertThat(result.contains("film")).isFalse();
    }

//    @Test
//    public void getWordsFromASynsetTest() {
//        WordNet wn = new WordNet(SYNSETS16_FILE, HYPONYMS16_FILE);
//
//        assertThat(wn.getSynset(11).getWords().size()).isEqualTo(3);
//        assertThat(wn.getSynset(11).getWords().contains("adjustment")).isTrue();
//    }
}
