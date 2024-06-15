package yaindtask.tokenizers.ngram.symbol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.Tokenizer;
import yaindtask.tokenizers.ngram.NGramToken;

public class DefaultSymbolNGramTokenizer implements Tokenizer {

  private final int maxN;

  public DefaultSymbolNGramTokenizer() {
    this(10);
  }

  public DefaultSymbolNGramTokenizer(int maxN) {
    this.maxN = maxN;
  }

  @Override
  public List<? extends Token> tokenize(String text) {

    var tokens = new LinkedHashMap<String, List<TokenPosition>>();

    var textAsChar = text.toCharArray();
    for (int textPos = 0; textPos < text.length(); textPos++) {

      for (int len = 1; len <= maxN; len++) {

        if (textPos + len > text.length()) {
          break;
        }

        var token = new char[len];
        System.arraycopy(textAsChar, textPos, token, 0, len);
        int textPosFinal = textPos;
        tokens.compute(new String(token), (s, tokenPositions) -> {
          if (tokenPositions == null) {
            tokenPositions = new ArrayList<>();
          }
          tokenPositions.add(new TokenPosition(textPosFinal, textPosFinal + token.length));
          return tokenPositions;
        });
      }
    }

    return tokens.entrySet().stream()
        .map(e -> new NGramToken(e.getKey(), e.getKey().length(), e.getValue()))
        .toList();
  }
}
