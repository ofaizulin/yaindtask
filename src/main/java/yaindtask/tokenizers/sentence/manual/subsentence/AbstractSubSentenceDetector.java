package yaindtask.tokenizers.sentence.manual.subsentence;

public abstract class AbstractSubSentenceDetector implements SubSentenceDetector {

  protected boolean inSubSentence;

  @Override
  public boolean isInSubSentence() {
    return inSubSentence;
  }
}
