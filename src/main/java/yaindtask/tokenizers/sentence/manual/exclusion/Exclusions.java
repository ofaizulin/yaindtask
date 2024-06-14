package yaindtask.tokenizers.sentence.manual.exclusion;

public class Exclusions {

  private static final DefaultSentenceTokenizerExclusion[] EXCLUSIONS = new DefaultSentenceTokenizerExclusion[]{
      new DefaultSentenceTokenizerExclusion("Mr.", 2),
      new DefaultSentenceTokenizerExclusion("Mrs.", 3),
      new DefaultSentenceTokenizerExclusion("Dr.", 2),
      new DefaultSentenceTokenizerExclusion("...", 2),
      new DefaultSentenceTokenizerExclusion("???", 2),
      new DefaultSentenceTokenizerExclusion("!!!", 2),
  };

  public static CompositeSubSentenceTokenizerExclusion getExclusions() {
    return new CompositeSubSentenceTokenizerExclusion(EXCLUSIONS);
  }
}
