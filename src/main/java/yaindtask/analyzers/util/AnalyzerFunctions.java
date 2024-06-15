package yaindtask.analyzers.util;

import java.util.stream.Collectors;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

public final class AnalyzerFunctions {

  private AnalyzerFunctions() {
  }

  public static String tokenPositions(Token token) {
    return token.positions().stream()
        .map(TokenPosition::startInclusive)
        .map(String::valueOf)
        .collect(Collectors.joining(", "));
  }
}
