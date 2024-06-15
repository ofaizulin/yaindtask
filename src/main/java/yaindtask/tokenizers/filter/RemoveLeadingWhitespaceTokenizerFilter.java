package yaindtask.tokenizers.filter;

import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

public class RemoveLeadingWhitespaceTokenizerFilter implements TokenFilter {

  @Override
  public List<Token> filter(Token token) {
    var chars = token.text().toCharArray();
    int startPos = 0;
    for (char aChar : chars) {
      if (Character.isWhitespace(aChar)) {
        startPos++;
      } else {
        break;
      }
    }

    if (startPos == 0) {
      return List.of(token);
    }

    if(startPos == chars.length){
      return List.of();
    }

    var newChars = new char[chars.length - startPos];
    System.arraycopy(chars, startPos, newChars, 0, newChars.length);
    var resultToken = new String(newChars);
    var startPosFinal = startPos;
    var positions = token.positions().stream()
        .map(tp -> new TokenPosition(tp.startInclusive() + startPosFinal, tp.endExclusive()))
        .toList();
    return List.of(new Token(resultToken, positions));
  }
}
