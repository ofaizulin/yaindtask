package yaindtask.analyzers.print.length.sentence;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;

public class SentenceLengthInSymbolsAnalyzer implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();

  @Override
  public AnalysisResult analyze(String text) {
    var data = sentenceTokenizer.tokenize(text).stream()
        .map(t -> new String[]{t.text(), String.valueOf(text.length())})
        .toList();

    return new AnalysisResult(new String[]{"Sentence", "Symbol Count"}, data);
  }
}
