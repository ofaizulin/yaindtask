package yaindtask.tokenizers.sentence.manual.exclusion;

import java.util.Arrays;
import java.util.List;

public class CompositeSubSentenceTokenizerExclusion implements SubSentenceTokenizerExclusion {

  private final List<SubSentenceTokenizerExclusion> exclusions;

  public CompositeSubSentenceTokenizerExclusion(SubSentenceTokenizerExclusion[] exclusions) {
    this(Arrays.asList(exclusions));
  }

  public CompositeSubSentenceTokenizerExclusion(List<SubSentenceTokenizerExclusion> exclusions) {
    this.exclusions = exclusions;
  }

  @Override
  public boolean matches(char[] text, int index) {
    return exclusions.stream().anyMatch(exclusion -> exclusion.matches(text, index));
  }
}
