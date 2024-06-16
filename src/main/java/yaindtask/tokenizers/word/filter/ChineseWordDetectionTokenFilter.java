package yaindtask.tokenizers.word.filter;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import java.util.ArrayList;
import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenizerUtils;
import yaindtask.tokenizers.filter.TokenFilter;

public class ChineseWordDetectionTokenFilter implements TokenFilter {

  @Override
  public List<Token> filter(Token token) {
    List<Token> result = new ArrayList<>();

    // Create a JiebaSegmenter instance
    JiebaSegmenter segmenter = new JiebaSegmenter();

    // Segment the text into words
    List<SegToken> cnTokens = segmenter.process(token.text(), JiebaSegmenter.SegMode.INDEX);

    // Print each token (word)
    for (SegToken cnToken : cnTokens) {
      for (var position : token.positions()) {
        var startPos = cnToken.startOffset + position.startInclusive();
        var endPos = cnToken.startOffset + cnToken.word.length() + position.startInclusive();
        result.add(new Token(cnToken.word, startPos, endPos));
      }
    }

    return TokenizerUtils.deduplicate(result);
  }
}
