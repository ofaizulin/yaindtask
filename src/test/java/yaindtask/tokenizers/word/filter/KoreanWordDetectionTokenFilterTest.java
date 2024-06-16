package yaindtask.tokenizers.word.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.filter.TokenFilter;

class KoreanWordDetectionTokenFilterTest {

  TokenFilter unit = new KoreanWordDetectionTokenFilter();

  @Test
  void sampleFromChatGPT() {
    var result = unit.filter(new Token("오늘은 좋은 날입니다."));
    var expectedRes = List.of(
        new Token("오늘", new TokenPosition(0, 2)),
        new Token("은", new TokenPosition(2, 3)),
        new Token(" ", new TokenPosition(3, 4), new TokenPosition(6, 7)),
        new Token("좋은", new TokenPosition(4, 6)),
        new Token("날", new TokenPosition(7, 8)),
        new Token("입니다", new TokenPosition(8, 11)),
        new Token(".", new TokenPosition(11, 12))
    );

    assertEquals(expectedRes, result);
  }
}