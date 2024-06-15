package yaindtask.tokenizers.sentence.manual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.TokenizerUtils;
import yaindtask.tokenizers.filter.CompositeTokenFilter;
import yaindtask.tokenizers.filter.RemoveLeadingWhitespaceTokenizerFilter;
import yaindtask.tokenizers.filter.RemoveTailingWhiteSpaceTokenizerFilter;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.exclusion.Exclusions;
import yaindtask.tokenizers.sentence.manual.exclusion.SubSentenceTokenizerExclusion;
import yaindtask.tokenizers.sentence.manual.subsentence.SubSentenceDetector;
import yaindtask.tokenizers.sentence.manual.subsentence.SubSentenceDetectors;

public class ManualSentenceTokenizer implements SentenceTokenizer {

  private final SubSentenceTokenizerExclusion exclusion;
  private final SubSentenceDetector subSentenceDetector;
  private final CompositeTokenFilter mergingTokenFilter;

  public ManualSentenceTokenizer() {
    this(
        Exclusions.getExclusions(),
        SubSentenceDetectors.getSubSentenceDetector(),
        new CompositeTokenFilter(
            new RemoveLeadingWhitespaceTokenizerFilter(),
            new RemoveTailingWhiteSpaceTokenizerFilter()
        )
    );
  }

  public ManualSentenceTokenizer(
      SubSentenceTokenizerExclusion exclusion,
      SubSentenceDetector subSentenceDetector,
      CompositeTokenFilter compositeTokenFilter) {
    this.exclusion = exclusion;
    this.subSentenceDetector = subSentenceDetector;
    this.mergingTokenFilter = compositeTokenFilter;
  }

  @Override
  public List<Token> tokenize(String text) {

    var sentences = new LinkedHashMap<String, List<TokenPosition>>();
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
        var sentence = text.substring(sentenceStartIndex, index);
        var position = new TokenPosition(sentenceStartIndex, index);
        sentences.compute(sentence, (s, tokenPositions) -> {
          tokenPositions = Optional.ofNullable(tokenPositions).orElse(new ArrayList<>());
          tokenPositions.add(position);
          return tokenPositions;
        });
        sentenceStartIndex = index;
      }
    }

    var result = sentences.entrySet().stream()
        .map(e -> new Token(e.getKey(), e.getValue()))
        .map(mergingTokenFilter::filter)
        .flatMap(Collection::stream)
        .toList();
    return TokenizerUtils.deduplicate(result);
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
}
