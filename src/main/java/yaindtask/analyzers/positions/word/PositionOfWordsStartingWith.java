package yaindtask.analyzers.positions.word;

import static yaindtask.analyzers.util.AnalyzerFunctions.tokenPositions;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionOfWordsStartingWith implements Analyzer {

  private final WordTokenizer wordTokenizer;
  private final String startWithStr;

  public PositionOfWordsStartingWith(String startWithStr) {
    this(new DefaultWordTokenizer(), startWithStr);
  }

  public PositionOfWordsStartingWith(WordTokenizer wordTokenizer, String startWithStr) {
    this.wordTokenizer = wordTokenizer;
    this.startWithStr = startWithStr;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = wordTokenizer.tokenize(text).stream()
        .filter(t -> t.text().startsWith(startWithStr))
        .map(t -> new String[]{t.text(), tokenPositions(t)})
        .toList();
    return new AnalysisResult(new String[]{"Word", "Positions"}, data);
  }
}
