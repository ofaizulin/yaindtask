package yaindtask.analyzers.positions.word;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TokenPositionOfSpecificWordAnalyzerTest {

  @CsvSource(textBlock = """
      Hello, I am Grut. You 
      """)
  @ParameterizedTest
  void analyze() {
  }
}