package yaindtask.analyzers.positions.symbolngram;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.ngram.symbol.DefaultSymbolNGramTokenizer;
import yaindtask.tokenizers.ngram.symbol.SymbolNGramTokenizer;

public class PositionsOfSymbolNGramHavingN implements Analyzer {

  private final SymbolNGramTokenizer tokenizer;
  private final int n;
  private final Integer minFrequency;
  private final Integer maxFrequency;

  public PositionsOfSymbolNGramHavingN(int n) {
    this.tokenizer = new DefaultSymbolNGramTokenizer(n);
    this.n = n;
    this.minFrequency = null;
    this.maxFrequency = null;
  }

  public PositionsOfSymbolNGramHavingN(int n, Integer minFrequency, Integer maxFrequency) {
    this.tokenizer = new DefaultSymbolNGramTokenizer(n);
    this.n = n;
    this.minFrequency = minFrequency;
    this.maxFrequency = maxFrequency;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = tokenizer.tokenize(text).stream()
        .filter(t -> t.n() == n)
        .filter(t -> {
          if (minFrequency == null) {
            return true;
          }
          return t.positions().size() >= minFrequency;
        })
        .filter(t -> {
          if (maxFrequency == null) {
            return true;
          }
          return t.positions().size() <= maxFrequency;
        })
        .map(t -> new String[]{
            t.text(),
            AnalyzerFunctions.tokenPositions(t),
            String.valueOf(t.positions().size())
        })
        .toList();

    return of("nGram", "Positions", "Frequency", data);
  }
}
