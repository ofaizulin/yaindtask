package yaindtask.tokenizers.word.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.filter.TokenFilter;

class JapaneseWordDetectionTokenFilterTest {

  TokenFilter unit = new JapaneseWordDetectionTokenFilter();

  @Test
  void sampleFromChatGPT() {
    var result = unit.filter(new Token("今日はいい天気ですね。"));
    var expectedRes = List.of(
        new Token("今日", 0, 2),
        new Token("は", 2, 3),
        new Token("いい", 3, 5),
        new Token("天気", 5, 7),
        new Token("です", 7, 9),
        new Token("ね", 9, 10),
        new Token("。", 10, 11)
    );

    assertEquals(expectedRes, result);
  }
}