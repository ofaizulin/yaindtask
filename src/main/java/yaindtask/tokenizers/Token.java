package yaindtask.tokenizers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Token {

  private final String text;
  private final List<TokenPosition> positions;

  public Token(String text, List<TokenPosition> positions) {
    this.text = text;
    this.positions = positions;
  }

  public Token(String text) {
    this(text, List.of(new TokenPosition(0, text.length())));
  }

  public Token(String text, int positionStartInclusive, int positionEndExclusive) {
    this(text, List.of(new TokenPosition(positionStartInclusive, positionEndExclusive)));
  }

  public Token(String text, TokenPosition... tokenPosition) {
    this(text, Arrays.asList(tokenPosition));
  }

  public Token(Token token) {
    this(token.text, token.positions);
  }

  public String text() {
    return text;
  }

  public List<TokenPosition> positions() {
    return positions;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Token) obj;
    return Objects.equals(this.text, that.text) &&
        Objects.equals(this.positions, that.positions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, positions);
  }

  @Override
  public String toString() {
    return "Token[" +
        "text=" + text + ", " +
        "positions=" + positions + ']';
  }
}
