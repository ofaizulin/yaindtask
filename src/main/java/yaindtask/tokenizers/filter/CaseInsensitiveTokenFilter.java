package yaindtask.tokenizers.filter;

import java.util.List;
import yaindtask.tokenizers.Token;

public class CaseInsensitiveTokenFilter implements TokenFilter {

  @Override
  public List<Token> filter(Token token) {
    return List.of(new Token(token.text().toLowerCase(), token.positions()));
  }
}
