package yaindtask.tokenizers.sentence.manual.subsentence;

public class DoubleCharSubSentenceDetector extends AbstractSubSentenceDetector {

  private final char openChar;

  private final char closeChar;

  public DoubleCharSubSentenceDetector(char openChar, char closeChar) {
    this.openChar = openChar;
    this.closeChar = closeChar;
  }

  @Override
  public void onOnNextChar(char c) {
    if (c == openChar) {
      inSubSentence = true;
    } else if (c == closeChar) {
      inSubSentence = false;
    }
  }
}
