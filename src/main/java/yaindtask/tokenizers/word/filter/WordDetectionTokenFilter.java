package yaindtask.tokenizers.word.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.filter.TokenFilter;

public class WordDetectionTokenFilter implements TokenFilter {

  private static final Pattern PATTERN = Pattern.compile("\\b\\w+\\b", Pattern.MULTILINE);

  @Override
  public List<Token> filter(Token token) {
    var text = token.text();
    var matcher = PATTERN.matcher(text);

    var result = new ArrayList<Token>();
    while (matcher.find()) {
      String word = matcher.group();
      var positions = new ArrayList<TokenPosition>();
      for (var tp : token.positions()) {
        var start = matcher.start();
        var end = matcher.end();
        var newPos = new TokenPosition(start + tp.startInclusive(), end + tp.startInclusive());
        positions.add(newPos);
      }
      result.add(new Token(word, positions));
    }
    return result;
  }
}
