package yaindtask.analyzers.positions.sentences.lexngram;

import static yaindtask.analyzers.AnalysisResult.of;

import java.util.ArrayList;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.util.AnalyzerFunctions;
import yaindtask.tokenizers.ngram.lexical.LexicalNGramTokenizer;
import yaindtask.tokenizers.ngram.lexical.DefaultLexicalNGramTokenizer;
import yaindtask.tokenizers.sentence.SentenceTokenizer;
import yaindtask.tokenizers.sentence.manual.ManualSentenceTokenizer;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;

public class PositionsOfSentencesThatContainsLexicalNgram implements Analyzer {

  private final SentenceTokenizer sentenceTokenizer = new ManualSentenceTokenizer();
  private final WordTokenizer wordTokenizer = new DefaultWordTokenizer();
  private final LexicalNGramTokenizer lexicalNGramTokenizer = new DefaultLexicalNGramTokenizer();

  private final String lexicalNgramToFind;

  public PositionsOfSentencesThatContainsLexicalNgram(String lexicalNgramToFind) {
    this.lexicalNgramToFind = lexicalNgramToFind;
  }


  @Override
  public AnalysisResult analyze(String text) {

    var data = new ArrayList<String[]>();
    for (var sentence : sentenceTokenizer.tokenize(text)) {
      var words = wordTokenizer.tokenize(sentence);
      var lexNgrams = lexicalNGramTokenizer.tokenize(words);
      for (var lexicalNgram : lexNgrams) {
        if (lexicalNgram.text().equals(lexicalNgramToFind)) {
          data.add(new String[]{sentence.text(), AnalyzerFunctions.tokenPositions(sentence)});
        }
      }
    }

    return of("Sentence", "Positions", data);
  }
}
