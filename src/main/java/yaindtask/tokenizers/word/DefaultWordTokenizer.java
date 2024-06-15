package yaindtask.tokenizers.word;

import java.util.ArrayList;
import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenizerUtils;
import yaindtask.tokenizers.filter.TokenFilter;
import yaindtask.tokenizers.word.filter.WordDetectionTokenFilter;

public class DefaultWordTokenizer implements WordTokenizer {

  private final List<TokenFilter> filters;

  public DefaultWordTokenizer() {
    this(List.of(new WordDetectionTokenFilter()));
  }

  public DefaultWordTokenizer(List<TokenFilter> filters) {
    this.filters = filters;
  }

  public List<Token> tokenize(String text) {
    return tokenize(new Token(text));
  }

  public List<Token> tokenize(Token token) {
    var result = List.of(token);
    for (var filter : filters) {
      result = filter.filter(token);
    }
    return TokenizerUtils.deduplicate(result);
  }

  @Override
  public WordTokenizer withExtraFilter(TokenFilter filter) {
    var filters = new ArrayList<>(this.filters);
    filters.add(filter);
    return new DefaultWordTokenizer(filters);
  }
}
