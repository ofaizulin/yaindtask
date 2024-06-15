package yaindtask.tokenizers.filter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

class RemoveTailingWhiteSpaceTokenizerFilterTest {

  TokenFilter filter = new RemoveTailingWhiteSpaceTokenizerFilter();

  @CsvSource(textBlock = """
      'Hello, how are you? ', 1
      'Hello, how are you?  ', 2
      """)
  @ParameterizedTest
  void filter(String text, int endPosOffset) {

    var result = filter.filter(new Token(text)).getFirst();

    assertEquals(text.substring(0, text.length() - endPosOffset), result.text());
    assertEquals(result.positions().getFirst(), new TokenPosition(0, text.length() - endPosOffset));
  }

  @Test
  void filterAllWhiteSpace() {
    var result = filter.filter(new Token("   "));
    assertTrue(result.isEmpty());
  }
}