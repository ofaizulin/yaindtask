package yaindtask.tokenizers.sentence.manual.subsentence;

public class SingleCharSubSentenceDetector extends AbstractSubSentenceDetector {

  private char c;

  public SingleCharSubSentenceDetector(char c){
    this.c = c;
  }

  @Override
  public void onOnNextChar(char c) {
    if(c == this.c) inSubSentence = !inSubSentence;
  }
}
