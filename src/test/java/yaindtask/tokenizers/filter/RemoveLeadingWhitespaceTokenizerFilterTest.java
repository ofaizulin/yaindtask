package yaindtask.tokenizers.filter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

class RemoveLeadingWhitespaceTokenizerFilterTest {

  TokenFilter filter = new RemoveLeadingWhitespaceTokenizerFilter();

  @CsvSource(textBlock = """
      ' Hello, how are you?', 1
      '  Hello, how are you?', 2
      """)
  @ParameterizedTest
  void filter(String text, int expectedStartPos) {
    var result = filter.filter(new Token(text)).getFirst();

    assertEquals(text.substring(expectedStartPos), result.text());
    assertEquals(result.positions().getFirst(), new TokenPosition(expectedStartPos, text.length()));
  }

  @Test
  void filterAllWhiteSpace() {
    var result = filter.filter(new Token("   "));
    assertTrue(result.isEmpty());
  }
}