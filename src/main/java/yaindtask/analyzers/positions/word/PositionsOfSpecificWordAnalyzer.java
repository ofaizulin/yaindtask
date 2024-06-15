package yaindtask.analyzers.positions.word;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfSpecificWordAnalyzer implements Analyzer {

  private final String word;
  private final WordTokenizer wordTokenizer;

  public PositionsOfSpecificWordAnalyzer(String word) {
    this(word, new DefaultWordTokenizer());
  }

  public PositionsOfSpecificWordAnalyzer(String text, WordTokenizer wordTokenizer) {
    this.word = text;
    this.wordTokenizer = wordTokenizer;
  }

  @Override
  public AnalysisResult analyze(String text) {

    var data = wordTokenizer.tokenize(text).stream()
        .filter(t -> t.text().equals(this.word))
        .map(t -> new String[]{t.text(), AnalyzerFunctions.tokenPositions(t)})
        .toList();

    return of("Word", "Positions", data);
  }
}
