package yaindtask.analyzers.positions.lexngram;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.lexicalngram.LexicalNGramTokenizer;
import yaindtask.tokenizers.lexicalngram.impl.DefaultLexicalNGramTokenizer;

public class PositionsOfLexNgramFromMintoMaxWithNWithoutSymbols implements Analyzer {

  private final LexicalNGramTokenizer lexicalNGramTokenizer;
  private final int n;
  private final int minFrequency;
  private final int maxFrequency;
  private final String bannedCharacters;

  public PositionsOfLexNgramFromMintoMaxWithNWithoutSymbols(
      int n,
      int minFrequency,
      int maxFrequency) {
    this(n, minFrequency, maxFrequency, null);
  }

  public PositionsOfLexNgramFromMintoMaxWithNWithoutSymbols(
      int n,
      int minFrequency,
      int maxFrequency,
      String bannedCharacters) {
    this.lexicalNGramTokenizer = new DefaultLexicalNGramTokenizer(n);
    this.n = n;
    this.minFrequency = minFrequency;
    this.maxFrequency = maxFrequency;
    this.bannedCharacters = bannedCharacters;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = lexicalNGramTokenizer.tokenize(text).stream()
        .filter(lt -> lt.n() == n)
        .filter(lt -> lt.positions().size() >= minFrequency)
        .filter(lt -> lt.positions().size() <= maxFrequency)
        .filter(lt -> {
          if (bannedCharacters == null || bannedCharacters.isEmpty()) {
            return true;
          }

          for (var bannedChar : bannedCharacters.toCharArray()) {
            if (lt.text().indexOf(bannedChar) != -1) {
              return false;
            }
          }
          return true;
        })
        .map(lt -> new String[]{
            lt.text(),
            AnalyzerFunctions.tokenPositions(lt)
        })
        .toList();
    return of("Lexical nGram", "Positions", data);
  }
}
