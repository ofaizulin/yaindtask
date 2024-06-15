package yaindtask.analyzers.print.frequency;

import java.util.ArrayList;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class WordLengthFrequencyAnalyzer implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();
  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();

  private final int length;

  public WordLengthFrequencyAnalyzer(int length) {
    this.length = length;
  }

  @Override
  public AnalysisResult analyze(String text) {

//    var wordMap = new TreeMap<String, Long>();
//
//    for (var sentence : sentenceTokenizer.tokenize(text)) {
//      for (var word : wordTokenizer.tokenize(sentence)) {
//        if (word.length() == length) {
//          wordMap.compute(word, (s, count) -> count == null ? 1 : count + 1);
//        }
//      }
//    }

    var data = new ArrayList<String[]>();
//    for (var entry : wordMap.entrySet()) {
//      data.add(new String[]{entry.getKey(), String.valueOf(entry.getValue())});
//    }

    return new AnalysisResult(new String[]{
        "Word", "Frequency"
    }, data);
  }
}
