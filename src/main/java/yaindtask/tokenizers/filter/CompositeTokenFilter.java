package yaindtask.tokenizers.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import yaindtask.tokenizers.Token;

public class CompositeTokenFilter implements TokenFilter {

  private final List<TokenFilter> filters;

  public CompositeTokenFilter(TokenFilter... filters) {
    this(Arrays.asList(filters));
  }

  public CompositeTokenFilter(List<TokenFilter> filters) {
    this.filters = filters;
  }

  @Override
  public List<Token> filter(Token token) {
    var result = List.of(token);
    for (var filter : filters) {

      var newResult = new ArrayList<Token>();
      for (var tokenToParse : result) {
        newResult.addAll(filter.filter(tokenToParse));
      }
      result = newResult;
    }

    return result;
  }
}
