package yaindtask.tokenizers.word.filter;

import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.filter.TokenFilter;

public class StopWordFilter implements TokenFilter {

  @SuppressWarnings("FieldCanBeLocal")
  private final List<String> STOP_WORDS = List.of(
      "and",
      "or"
  );

  private final List<String> stopWords;
  private final boolean caseSensitive;

  public StopWordFilter() {
    this.stopWords = STOP_WORDS;
    this.caseSensitive = true;
  }

  public StopWordFilter(List<String> stopWords, boolean caseSensitive) {
    this.stopWords = stopWords;
    this.caseSensitive = caseSensitive;
  }

  @Override
  public List<Token> filter(Token token) {
    for (var stopWord : stopWords) {
      if (caseSensitive && stopWord.equals(token.text())) {
        return List.of();
      } else if (stopWord.equalsIgnoreCase(token.text())) {
        return List.of();
      }
    }
    return List.of(token);
  }
}
