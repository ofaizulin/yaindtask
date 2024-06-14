package yaindtask.tokenizers.sentence.manual.subsentence;

public interface SubSentenceDetector {

  void onOnNextChar(char c);

  boolean isInSubSentence();
}
