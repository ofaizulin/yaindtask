package yaindtask.analyzers.positions.sentences.length;

import static yaindtask.analyzers.AnalysisResult.of;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;

public class PositionOfSentencesHavingLetterCount implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer;
  private final int letterCount;

  public PositionOfSentencesHavingLetterCount(int letterCount) {
    this(new ManualSentenceTokenizer(), letterCount);
  }

  public PositionOfSentencesHavingLetterCount(SentenceTokenizer sentenceTokenizer,
      int letterCount) {
    this.sentenceTokenizer = sentenceTokenizer;
    this.letterCount = letterCount;
  }

  @Override
  public AnalysisResult analyze(String text) {

    var data = sentenceTokenizer.tokenize(text)
        .stream()
        .filter(st -> st.text().chars().filter(Character::isLetter).count() == letterCount)
        .map(st -> new String[]{st.text(), AnalyzerFunctions.tokenPositions(st)})
        .toList();

    return of("Sentence", "Positions", data);
  }
}
