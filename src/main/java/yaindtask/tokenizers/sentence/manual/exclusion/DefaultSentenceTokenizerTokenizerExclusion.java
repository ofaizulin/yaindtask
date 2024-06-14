package yaindtask.tokenizers.sentence.manual.exclusion;

public class DefaultSentenceTokenizerTokenizerExclusion implements SubSentenceTokenizerExclusion {

  private final char[] chars;
  private final int pos;

  public DefaultSentenceTokenizerTokenizerExclusion(String exclusion, int pos) {
    this.chars = exclusion.toCharArray();
    this.pos = pos;
  }

  public boolean matches(char[] sentence, int sentencePos) {
    int checkStart = sentencePos - pos;
    for(int i = 0; i < chars.length; i++) {
      if(chars[i] != sentence[checkStart + i]) {
        return false;
      }
    }
    return true;
  }
}
