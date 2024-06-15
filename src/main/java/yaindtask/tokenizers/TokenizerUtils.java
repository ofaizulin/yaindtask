package yaindtask.tokenizers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class TokenizerUtils {

  public static List<Token> deduplicate(List<Token> tokens) {
    var map = new LinkedHashMap<String, List<TokenPosition>>();
    for (var token : tokens) {
      map.compute(token.text(), (text, tokenPositions) -> {
        if (tokenPositions == null) {
          tokenPositions = new ArrayList<>();
        }
        tokenPositions.addAll(token.positions());
        return tokenPositions;
      });
    }

    return map.entrySet().stream()
        .map(e -> new Token(e.getKey(), e.getValue()))
        .peek(t -> t.positions().sort(Comparator.comparing(TokenPosition::startInclusive)))
        .toList();
  }

}
