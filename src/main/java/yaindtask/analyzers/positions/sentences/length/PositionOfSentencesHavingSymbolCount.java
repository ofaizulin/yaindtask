package yaindtask.analyzers.positions.sentences.length;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;

public class PositionOfSentencesHavingSymbolCount implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer;
  private final int symbolCount;

  public PositionOfSentencesHavingSymbolCount(int symbolCount) {
    this(new ManualSentenceTokenizer(), symbolCount);
  }

  public PositionOfSentencesHavingSymbolCount(SentenceTokenizer sentenceTokenizer,
      int symbolCount) {
    this.sentenceTokenizer = sentenceTokenizer;
    this.symbolCount = symbolCount;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var data = sentenceTokenizer.tokenize(text)
        .stream()
        .filter(st -> st.text().length() == symbolCount)
        .map(st -> new String[]{st.text(), AnalyzerFunctions.tokenPositions(st)})
        .toList();
    return of("Sentence", "Positions", data);
  }
}
