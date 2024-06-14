package yaindtask.tokenizers.sentence.manual.subsentence;

import java.util.Arrays;
import java.util.List;

public class CompositeSubSentenceDetector implements SubSentenceDetector {

  private final List<SubSentenceDetector> detectors;

  public CompositeSubSentenceDetector(SubSentenceDetector... detectors) {
    this(Arrays.asList(detectors));
  }

  public CompositeSubSentenceDetector(List<SubSentenceDetector> detectors) {
    this.detectors = detectors;
  }

  @Override
  public void onOnNextChar(char c) {
    detectors.forEach(d -> d.onOnNextChar(c));
  }

  @Override
  public boolean isInSubSentence() {
    return detectors.stream().anyMatch(SubSentenceDetector::isInSubSentence);
  }
}
