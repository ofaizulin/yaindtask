package yaindtask.analyzers.print.length.sentence;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class SentenceLengthInWordsAnalyzer implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();
  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();

  @Override
  public AnalysisResult analyze(String text) {
    var data = sentenceTokenizer.tokenize(text).stream()
        .map(t -> {
          int wordCount = 0;
          for (var word : wordTokenizer.tokenize(t)) {
            wordCount += word.positions().size();
          }
          return new String[]{t.text(), String.valueOf(wordCount)};
        })
        .toList();

    return of("Sentence", "Word Count", data);
  }
}
