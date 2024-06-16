package yaindtask.tokenizers.word.filter;

import com.atilika.kuromoji.ipadic.Tokenizer;
import java.util.ArrayList;
import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenizerUtils;
import yaindtask.tokenizers.filter.TokenFilter;

public class JapaneseWordDetectionTokenFilter implements TokenFilter {

  Tokenizer tokenizer = new Tokenizer();

  @Override
  public List<Token> filter(Token token) {

    List<Token> result = new ArrayList<>();

    var japaneseTokens = tokenizer.tokenize(token.text());
    for (var jpToken : japaneseTokens) {
      for (var position : token.positions()) {
        var startPos = jpToken.getPosition() + position.startInclusive();
        var endPos =
            jpToken.getPosition() + jpToken.getSurface().length() + position.startInclusive();
        result.add(new Token(jpToken.getSurface(), startPos, endPos));
      }
    }

    return TokenizerUtils.deduplicate(result);
  }
}
