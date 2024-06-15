package yaindtask.analyzers.positions.word;

import static yaindtask.analyzers.AnalysisResult.of;
import static yaindtask.analyzers.util.AnalyzerFunctions.tokenPositions;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfWordsStartingWithText implements Analyzer {

  private final WordTokenizer wordTokenizer;
  private final String startWithStr;

  public PositionsOfWordsStartingWithText(String startWithStr) {
    this(new DefaultWordTokenizer(), startWithStr);
  }

  public PositionsOfWordsStartingWithText(WordTokenizer wordTokenizer, String startWithStr) {
    this.wordTokenizer = wordTokenizer;
    this.startWithStr = startWithStr;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = wordTokenizer.tokenize(text).stream()
        .filter(t -> t.text().startsWith(startWithStr))
        .map(t -> new String[]{t.text(), tokenPositions(t)})
        .toList();
    return of("Word", "Positions", data);
  }
}
