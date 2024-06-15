package yaindtask.tokenizers.word.filter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordDetectionTokenFilterTest {

  WordDetectionTokenFilter filter = new WordDetectionTokenFilter();

  @CsvSource(textBlock = """
      "Hello. Hello. Hello, a"
      """)
  @ParameterizedTest
  void filter2(String input, String expectedPositions) {
//    System.out.println(filter.filter(input));
  }
}