package yaindtask.analyzers.print.length.word;

import java.util.ArrayList;
import java.util.TreeMap;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class WordLengthByFrequencyAnalyzer implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();
  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();

  private final int minFrequency;
  private final int maxFrequency;

  public WordLengthByFrequencyAnalyzer(int minFrequency, int maxFrequency) {
    this.minFrequency = minFrequency;
    this.maxFrequency = maxFrequency;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var wordCounter = new TreeMap<String, Long>();
    for (var sentence : sentenceTokenizer.tokenize(text)) {
      for (var word : wordTokenizer.tokenize(sentence)) {
        wordCounter.compute(word, (s, count) -> count == null ? 1 : count + 1);
      }
    }

    var result = new ArrayList<String[]>();
    for (var entry : wordCounter.entrySet()) {
      var word = entry.getKey();
      var wordFrequency = entry.getValue();

      if (wordFrequency >= minFrequency && wordFrequency <= maxFrequency) {
        result.add(
            new String[]{word, String.valueOf(wordFrequency), String.valueOf(word.length())});
      }
    }
    return new AnalysisResult(new String[]{
        "Word", "Frequenccy", "Length"
    }, result);
  }
}
