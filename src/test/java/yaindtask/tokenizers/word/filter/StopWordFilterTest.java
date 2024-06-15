package yaindtask.tokenizers.word.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import yaindtask.tokenizers.Token;

class StopWordFilterTest {

  @CsvSource(textBlock = """
      'I,and,my,dog', 'and,my', false, 2
      'I,and,my,dog', 'aNd,My', true, 2
      """)
  @ParameterizedTest
  void filter(String tokens, String stopWords, boolean caseSensitive, int expectedCount) {
    var tokenList = Arrays.stream(tokens.split(",")).map(Token::new).toList();
    var stopWordList = Arrays.asList(stopWords.split(","));

    var filter = new StopWordFilter(stopWordList, caseSensitive);
    var result = tokenList.stream()
        .map(filter::filter)
        .flatMap(List::stream)
        .toList();

    assertEquals(expectedCount, result.size());
  }
}