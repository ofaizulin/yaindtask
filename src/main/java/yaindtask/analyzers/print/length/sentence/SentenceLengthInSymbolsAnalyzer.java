package yaindtask.analyzers.print.length.sentence;

import java.util.ArrayList;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;

public class SentenceLengthInSymbolsAnalyzer implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();

  @Override
  public AnalysisResult analyze(String text) {
    var sentences = sentenceTokenizer.tokenize(text);
    var data = new ArrayList<String[]>();
//    for (var sentence : sentences) {
//      data.add(new String[]{sentence, String.valueOf(sentence.length())});
//    }

    return new AnalysisResult(new String[]{
        "Sentence", "Symbol Count"
    }, data);
  }
}
