package yaindtask.tokenizers.sentence.manual.exclusion;

/**
 * Informs sentence tokenizer that provided character sequence
 * should not be treated as a sentence separator.
 */
public class DefaultSentenceTokenizerExclusion implements SubSentenceTokenizerExclusion {

  private final char[] chars;
  private final int pos;

  /**
   * Creates an exclusion case for sentence tokenizer. For instance, if we want to add "Dr." it
   * should be added as "Dr.", 2, what means that we are processing "." Character and if it's after
   * Dr. it should not be considered as a new string.
   *
   * @param exclusion char sequence to treat as an exclusion
   * @param pos       marker of the character that is processed in exclusion
   */
  public DefaultSentenceTokenizerExclusion(String exclusion, int pos) {
    this.chars = exclusion.toCharArray();
    this.pos = pos;
  }

  public boolean matches(char[] sentence, int sentencePos) {
    int checkStart = sentencePos - pos;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] != sentence[checkStart + i]) {
        return false;
      }
    }
    return true;
  }
}
