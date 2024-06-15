package yaindtask.analyzers.positions.lexngram;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.ngram.lexical.LexicalNGramTokenizer;
import yaindtask.tokenizers.ngram.lexical.DefaultLexicalNGramTokenizer;

public class PositionsOfLexNGramThatMatchText implements Analyzer {

  private final LexicalNGramTokenizer tokenizer = new DefaultLexicalNGramTokenizer();
  private final String text;

  public PositionsOfLexNGramThatMatchText(String text) {
    this.text = text;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = tokenizer.tokenize(text).stream()
        .filter(lt -> lt.text().equals(this.text))
        .map(lt -> new String[]{
            lt.text(),
            AnalyzerFunctions.tokenPositions(lt)
        })
        .toList();
    return of("Lexical nGram", "Positions", data);
  }
}
