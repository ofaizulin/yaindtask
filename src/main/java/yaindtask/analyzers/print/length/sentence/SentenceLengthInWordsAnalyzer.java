package yaindtask.analyzers.print.length.sentence;

import java.util.ArrayList;
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
    var sentences = sentenceTokenizer.tokenize(text);
    var data = new ArrayList<String[]>();

    for (var sentence : sentences) {
      var words = wordTokenizer.tokenize(sentence);
      data.add(new String[]{sentence, String.valueOf(words.size())});
    }

    return new AnalysisResult(new String[]{
        "Sentence", "Word Count"
    }, data);
  }
}
