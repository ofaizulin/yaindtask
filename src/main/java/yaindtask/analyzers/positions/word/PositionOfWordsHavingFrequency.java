package yaindtask.analyzers.positions.word;

import static yaindtask.analyzers.util.AnalyzerFunctions.tokenPositions;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionOfWordsHavingFrequency implements Analyzer {

  private final WordTokenizer wordTokenizer;
  private final int minFrequency;
  private final int maxFrequency;

  public PositionOfWordsHavingFrequency(int minFrequency, int maxFrequency) {
    this(new DefaultWordTokenizer(), minFrequency, maxFrequency);
  }

  public PositionOfWordsHavingFrequency(WordTokenizer wordTokenizer, int minFrequency,
      int maxFrequency) {
    this.wordTokenizer = wordTokenizer;
    this.minFrequency = minFrequency;
    this.maxFrequency = maxFrequency;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = wordTokenizer.tokenize(text).stream()
        .filter(t -> t.positions().size() >= minFrequency)
        .filter(t -> t.positions().size() <= maxFrequency)
        .map(t -> new String[]{
            t.text(),
            String.valueOf(t.positions().size()),
            tokenPositions(t)
        })
        .toList();
    return new AnalysisResult(new String[]{"Word", "Frequency", "Positions"}, data);
  }
}
