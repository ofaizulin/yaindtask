package yaindtask.analyzers.print.frequency;


import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.ngram.lexical.LexicalNGramTokenizer;
import yaindtask.tokenizers.ngram.lexical.DefaultLexicalNGramTokenizer;

public class LexicalNGramFrequencyAnalyzer implements Analyzer {

  private final LexicalNGramTokenizer lexicalNGramTokenizer;
  private final int nEqTo;

  public LexicalNGramFrequencyAnalyzer(int nEqTo) {
    this.lexicalNGramTokenizer = new DefaultLexicalNGramTokenizer(nEqTo);
    this.nEqTo = nEqTo;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = lexicalNGramTokenizer.tokenize(text).stream()
        .filter(t -> t.n() == nEqTo)
        .map(t -> new String[]{t.text(), String.valueOf(t.positions().size())})
        .toList();

    return of("Lexical ngram", "Frequency", data);
  }
}
