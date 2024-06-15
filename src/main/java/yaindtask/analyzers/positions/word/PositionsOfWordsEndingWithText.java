package yaindtask.analyzers.positions.word;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfWordsEndingWithText implements Analyzer {

  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();
  private final String textToEndWith;

  public PositionsOfWordsEndingWithText(String textToEndWith) {
    this.textToEndWith = textToEndWith;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = wordTokenizer.tokenize(text).stream()
        .filter(t -> t.text().endsWith(this.textToEndWith))
        .map(t -> new String[]{
            t.text(),
            AnalyzerFunctions.tokenPositions(t)
        })
        .toList();
    return AnalysisResult.of("Word", "Positions", data);
  }
}
