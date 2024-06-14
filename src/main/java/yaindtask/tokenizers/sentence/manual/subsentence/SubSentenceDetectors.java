package yaindtask.tokenizers.sentence.manual.subsentence;

import java.util.ArrayList;
import java.util.List;

public class SubSentenceDetectors {

  private static final char[] SINGLE_CHAR_CASES = new char[]{
      '"'
  };

  private static final char[][] DOUBLE_CHAR_CASES = new char[][]{
      {'«', '»'}, // UA
      {'“', '”'}, // UA
      {'「', '」'}, // Japanese
  };

  public static CompositeSubSentenceDetector getSubSentenceDetector() {
    List<SubSentenceDetector> subSentenceDetectors = new ArrayList<>(
        SINGLE_CHAR_CASES.length + DOUBLE_CHAR_CASES.length);
    for (char c : SINGLE_CHAR_CASES) {
      subSentenceDetectors.add(new SingleCharSubSentenceDetector(c));
    }
    for (char[] chars : DOUBLE_CHAR_CASES) {
      subSentenceDetectors.add(new DoubleCharSubSentenceDetector(chars[0], chars[1]));
    }
    return new CompositeSubSentenceDetector(subSentenceDetectors);
  }
}
