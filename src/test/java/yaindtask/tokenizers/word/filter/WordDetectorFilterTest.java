package yaindtask.tokenizers.word.filter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordDetectorFilterTest {

  WordDetectionTokenFilter filter = new WordDetectionTokenFilter();

  @CsvSource(delimiterString = "___", textBlock = """
      Hello, my name is Kate. ___ Hello,my,name,is,Kate
      O'neal.___ aa
      привіт, я Катя.___ aa
      """)
  @ParameterizedTest
  void filter(String input, String expected) {
//    filter.filter(input).forEach(System.out::println);
  }
}