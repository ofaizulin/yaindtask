package yaindtask.tokenizers;

import java.util.Arrays;
import java.util.List;

public record Token(String text, List<TokenPosition> positions) {

  public Token(String text) {
    this(text, List.of(new TokenPosition(0, text.length())));
  }

  public Token(String text, TokenPosition... tokenPosition) {
    this(text, Arrays.asList(tokenPosition));
  }
}
