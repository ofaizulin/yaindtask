package yaindtask.analyzers.print.frequency;


import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.lexicalngram.LexicalNGramTokenizer;
import yaindtask.tokenizers.lexicalngram.impl.DefaultLexicalNGramTokenizer;

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

    return new AnalysisResult(new String[]{"Lexical ngram", "Frequency"}, data);
  }
}
