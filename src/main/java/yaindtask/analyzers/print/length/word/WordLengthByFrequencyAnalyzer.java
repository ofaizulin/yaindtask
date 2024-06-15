package yaindtask.analyzers.print.length.word;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class WordLengthByFrequencyAnalyzer implements Analyzer {

  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();

  private final int minFrequency;
  private final int maxFrequency;

  public WordLengthByFrequencyAnalyzer(int minFrequency, int maxFrequency) {
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
            String.valueOf(t.text().length())
        })
        .toList();

    return new AnalysisResult(new String[]{"Word", "Frequency", "Length"}, data);
  }
}
