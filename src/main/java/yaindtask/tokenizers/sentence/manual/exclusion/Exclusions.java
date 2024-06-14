package yaindtask.tokenizers.sentence.manual.exclusion;

public class Exclusions {

  private static final DefaultSentenceTokenizerTokenizerExclusion[] EXCLUSIONS = new DefaultSentenceTokenizerTokenizerExclusion[]{
      new DefaultSentenceTokenizerTokenizerExclusion("Mr.", 2),
      new DefaultSentenceTokenizerTokenizerExclusion("Mrs.", 3),
      new DefaultSentenceTokenizerTokenizerExclusion("Dr.", 2),
      new DefaultSentenceTokenizerTokenizerExclusion("...", 2),
      new DefaultSentenceTokenizerTokenizerExclusion("???", 2),
      new DefaultSentenceTokenizerTokenizerExclusion("!!!", 2),
  };

  public static CompositeSubSentenceTokenizerExclusion getExclusions() {
    return new CompositeSubSentenceTokenizerExclusion(EXCLUSIONS);
  }
}
