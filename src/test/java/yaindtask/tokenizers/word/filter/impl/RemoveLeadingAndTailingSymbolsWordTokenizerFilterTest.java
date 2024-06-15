package yaindtask.tokenizers.word.filter.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RemoveLeadingAndTailingSymbolsWordTokenizerFilterTest {

  RemoveLeadingAndTailingSymbolsWordTokenizerFilter unit = new RemoveLeadingAndTailingSymbolsWordTokenizerFilter();

  @CsvSource(textBlock = """
      ??`test?!?, test
      test, test
      """)
  @ParameterizedTest
  void filter(String input, String expected) {
    assertEquals(List.of(expected), unit.filter(input));
  }

  @Test
  void filterAllCharsAreSymbols() {
    assertEquals(0, unit.filter("???").size());
  }
}