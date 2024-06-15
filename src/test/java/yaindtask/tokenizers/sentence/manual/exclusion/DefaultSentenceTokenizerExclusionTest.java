package yaindtask.tokenizers.sentence.manual.exclusion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DefaultSentenceTokenizerExclusionTest {

  @CsvSource(textBlock = """
      Dr. Kittie, 2, Dr., 2
      я і т.д. по списку, 5, т.д., 1
      я і т.д. по списку, 7, т.д., 3
      """)
  @ParameterizedTest
  void matches(String sentence, int sentencePos, String exclusionText, int exclusionPos) {
    var exclusion = new DefaultSentenceTokenizerExclusion(exclusionText, exclusionPos);
    assertTrue(exclusion.matches(sentence.toCharArray(), sentencePos));
  }
}