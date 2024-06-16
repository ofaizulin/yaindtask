package yaindtask.tokenizers.word.filter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.filter.TokenFilter;

class ChineseWordDetectionTokenFilterTest {

  TokenFilter unit = new ChineseWordDetectionTokenFilter();

  @Test
  void sampleFromChatGPT() {
    var result = unit.filter(new Token("今天是个好日子。"));
    var expectedRes = List.of(
        new Token("今天", 0, 2),
        new Token("是", 2, 3),
        new Token("个", 3, 4),
        new Token("日子", 5, 7),
        new Token("好日子", 4, 7),
        new Token("。", 7, 8)
    );

    assertEquals(expectedRes, result);
  }
}