package yaindtask.analyzers.print.frequency;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class WordLengthFrequencyAnalyzer implements Analyzer {

  private final int wordLength;
  private final WordTokenizer wordTokenizer;

  public WordLengthFrequencyAnalyzer(int wordLength) {
    this(wordLength, new DefaultWordTokenizer());
  }

  public WordLengthFrequencyAnalyzer(int length, WordTokenizer wordTokenizer) {
    this.wordLength = length;
    this.wordTokenizer = wordTokenizer;
  }

  @Override
  public AnalysisResult analyze(String text) {

    var data = wordTokenizer.tokenize(text)
        .stream()
        .filter(t -> t.text().length() == wordLength)
        .map(t -> new String[]{t.text(), String.valueOf(t.positions().size())})
        .toList();

    return of("Word", "Frequency", data);
  }
}
