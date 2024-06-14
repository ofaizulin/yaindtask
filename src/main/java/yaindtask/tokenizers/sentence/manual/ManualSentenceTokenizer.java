package yaindtask.tokenizers.sentence.manual;

import java.util.ArrayList;
import java.util.List;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.exclusion.Exclusions;
import yaindtask.tokenizers.sentence.manual.exclusion.SubSentenceTokenizerExclusion;
import yaindtask.tokenizers.sentence.manual.subsentence.SubSentenceDetector;
import yaindtask.tokenizers.sentence.manual.subsentence.SubSentenceDetectors;

public class ManualSentenceTokenizer implements SentenceTokenizer {

  private final SubSentenceTokenizerExclusion exclusion;
  private final SubSentenceDetector subSentenceDetector;

  public ManualSentenceTokenizer() {
    this(
        Exclusions.getExclusions(),
        SubSentenceDetectors.getSubSentenceDetector()
    );
  }

  public ManualSentenceTokenizer(SubSentenceTokenizerExclusion exclusion,
      SubSentenceDetector subSentenceDetector) {
    this.exclusion = exclusion;
    this.subSentenceDetector = subSentenceDetector;
  }


  @Override
  public List<String> tokenize(String text) {

    var sentences = new ArrayList<String>();
    text = text.trim();
    if (text.charAt(text.length() - 1) != '.') {
      text += '.';
    }
    text += " ";

    char[] chars = text.toCharArray();
    int sentenceStartIndex = 0;
    for (int index = 0; index < chars.length; index++) {

      subSentenceDetector.onOnNextChar(chars[index]);
      if (subSentenceDetector.isInSubSentence()) {
        continue;
      }

      if (isSpaceOrLineBreak(chars[index]) && isEndOfSentence(chars, index)) {
        sentences.add(text.substring(sentenceStartIndex, index).trim());
        sentenceStartIndex = index;
      }
    }

    return sentences;
  }

  protected boolean isSpaceOrLineBreak(char c) {
    return Character.isWhitespace(c)
        || Character.isSpaceChar(c);
  }

  protected boolean isEndOfSentence(char[] chars, int index) {
    return switch (chars[index - 1]) {
      case '!', '?' -> true;
      case '.' -> !exclusion.matches(chars, index - 1);
      case '。' -> !exclusion.matches(chars, index - 1); // Japanese
      default -> false;
    };
  }

  public static void main(String[] args) {
    var sentences = new ManualSentenceTokenizer().tokenize("""
        Hi pretty girl. Hello, i'm Mary! Let's play a game? A game... Ok, Dr. Brown he said.
        I've asked here "What's your name? My is Mr. Cat. How do you do?".
        """);

    sentences.forEach(s -> {
      System.out.println(s);
      System.out.println("---");
    });
  }
}
