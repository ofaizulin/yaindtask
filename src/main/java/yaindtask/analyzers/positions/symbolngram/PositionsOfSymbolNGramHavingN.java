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

  public PositionsOfSymbolNGramHavingN(int n) {
    this.tokenizer = new DefaultSymbolNGramTokenizer(n);
    this.n = n;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = tokenizer.tokenize(text).stream()
        .filter(t -> t.n() == n)
        .map(t -> new String[]{
            t.text(),
            AnalyzerFunctions.tokenPositions(t)
        })
        .toList();

    return of("nGram", "Positions", data);
  }
}
