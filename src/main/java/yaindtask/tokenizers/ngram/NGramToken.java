package yaindtask.tokenizers.ngram;

import java.util.List;
import java.util.Objects;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

public class NGramToken extends Token {

  private final int n;

  public NGramToken(String token, int n) {
    super(token);
    this.n = n;
  }

  public NGramToken(String token, int n, TokenPosition position) {
    super(token, position);
    this.n = n;
  }

  public NGramToken(String token, int n, TokenPosition... position) {
    super(token, position);
    this.n = n;
  }

  public NGramToken(String token, int n, List<TokenPosition> positions) {
    super(token, positions);
    this.n = n;
  }


  public int n() {
    return n;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    NGramToken that = (NGramToken) o;
    return text().equals(that.text())
        && positions().equals(that.positions())
        && n == that.n;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), n);
  }

  @Override
  public String toString() {
    return "NGramToken[" +
        "text=" + text() +
        ", n=" + n +
        ", positions=" + positions() +
        ']';
  }
}
