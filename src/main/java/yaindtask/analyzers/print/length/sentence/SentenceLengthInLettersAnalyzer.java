package yaindtask.analyzers.print.length.sentence;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;

public class SentenceLengthInLettersAnalyzer implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();

  @Override
  public AnalysisResult analyze(String text) {

    var data = sentenceTokenizer.tokenize(text).stream()
        .map(t -> {
          var letterCount = 0;
          for (char c : t.text().toCharArray()) {
            if (Character.isLetter(c)) {
              letterCount++;
            }
          }
          return new String[]{t.text(), String.valueOf(letterCount)};
        })
        .toList();

    return of("Sentence", "Letter Count", data);
  }
}
