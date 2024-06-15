package yaindtask.analyzers.positions.sentences.length;

import static yaindtask.analyzers.AnalysisResult.of;
import static yaindtask.analyzers.util.AnalyzerFunctions.tokenPositions;

import java.util.ArrayList;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfSentencesHavingWordCount implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();
  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();

  private final int wordCount;

  public PositionsOfSentencesHavingWordCount(int wordCount) {
    this.wordCount = wordCount;
  }

  @Override
  public AnalysisResult analyze(String text) {

    var result = new ArrayList<String[]>();
    for (var st : sentenceTokenizer.tokenize(text)) {
      var wordTokens = wordTokenizer.tokenize(st);
      if (wordTokens.size() == wordCount) {
        result.add(new String[]{st.text(), tokenPositions(st)});
      }
    }

    return of("Sentence", "Positions", result);
  }
}
