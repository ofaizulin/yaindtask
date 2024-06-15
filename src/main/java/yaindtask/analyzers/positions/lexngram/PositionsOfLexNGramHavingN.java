package yaindtask.analyzers.positions.lexngram;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.ngram.lexical.LexicalNGramTokenizer;
import yaindtask.tokenizers.ngram.lexical.DefaultLexicalNGramTokenizer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfLexNGramHavingN implements Analyzer {

  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();
  private final LexicalNGramTokenizer lexicalNGramTokenizer;
  private final int n;

  public PositionsOfLexNGramHavingN(int n) {
    this.lexicalNGramTokenizer = new DefaultLexicalNGramTokenizer(n);
    this.n = n;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var words = wordTokenizer.tokenize(text);
    var data = lexicalNGramTokenizer.tokenize(words).stream()
        .filter(lt -> lt.n() == n)
        .map(lt -> new String[]{
            lt.text(),
            String.valueOf(AnalyzerFunctions.tokenPositions(lt))
        })
        .toList();
    return of("Lexical nGram", "Positions", data);
  }
}
