package yaindtask.tokenizers.word.filter;

import java.util.ArrayList;
import java.util.List;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer.KoreanToken;
import scala.collection.Seq;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenizerUtils;
import yaindtask.tokenizers.filter.TokenFilter;

public class KoreanWordDetectionTokenFilter implements TokenFilter {

  @Override
  public List<Token> filter(Token token) {

    List<Token> result = new ArrayList<>();

    CharSequence normalized = OpenKoreanTextProcessorJava.normalize(token.text());
    Seq<KoreanToken> krTokens = OpenKoreanTextProcessorJava.tokenize(normalized);

    var krIterator = krTokens.iterator();
    while (krIterator.hasNext()) {
      KoreanToken krToken =  krIterator.next();
      for (var position : token.positions()) {
        var startPos = krToken.offset() + position.startInclusive();
        var endPos = krToken.offset() + krToken.length() + position.startInclusive();
        result.add(new Token(krToken.text(), startPos, endPos));
      }
    }

    return TokenizerUtils.deduplicate(result);
  }
}
