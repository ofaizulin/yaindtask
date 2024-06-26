package yaindtask.analyzers.positions.word;

import static yaindtask.analyzers.AnalysisResult.of;
import static yaindtask.analyzers.util.AnalyzerFunctions.tokenPositions;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfWordsHavingLengthAnalyzer implements Analyzer {

  private final WordTokenizer wordTokenizer;
  private final int wordLength;

  public PositionsOfWordsHavingLengthAnalyzer(int wordLength) {
    this(new DefaultWordTokenizer(), wordLength);
  }

  public PositionsOfWordsHavingLengthAnalyzer(WordTokenizer wordTokenizer, int wordLength) {
    this.wordTokenizer = wordTokenizer;
    this.wordLength = wordLength;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = wordTokenizer.tokenize(text).stream()
        .filter(t -> t.text().length() == wordLength)
        .map(t -> new String[]{t.text(), tokenPositions(t)})
        .toList();
    return of("Word", "Positions", data);
  }
}
