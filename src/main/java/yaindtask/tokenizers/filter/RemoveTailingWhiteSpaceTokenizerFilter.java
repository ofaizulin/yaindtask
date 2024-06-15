package yaindtask.tokenizers.filter;

import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

public class RemoveTailingWhiteSpaceTokenizerFilter implements TokenFilter {

  @Override
  public List<Token> filter(Token token) {
    var chars = token.text().toCharArray();

    var endPos = chars.length;
    for (var index = chars.length - 1; index >= 0; index--) {
      if (Character.isWhitespace(chars[index])) {
        endPos--;
      } else {
        break;
      }
    }

    if (endPos == chars.length) {
      return List.of(token);
    }

    if (endPos == 0) {
      return List.of();
    }

    var resChars = new char[endPos];
    System.arraycopy(chars, 0, resChars, 0, endPos);
    var resText = new String(resChars);
    var diff = chars.length - endPos;
    var positions = token.positions().stream()
        .map(tp -> new TokenPosition(tp.startInclusive(), tp.endExclusive() - diff)).toList();
    return List.of(new Token(resText, positions));
  }
}
