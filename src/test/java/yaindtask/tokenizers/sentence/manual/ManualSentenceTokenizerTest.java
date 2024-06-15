package yaindtask.tokenizers.sentence.manual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.TokenPosition;

class ManualSentenceTokenizerTest {

  ManualSentenceTokenizer unit = new ManualSentenceTokenizer();

  @Test
  public void testRepeatingSentences() {

    var maryText = "Hi, my name is Mary.";
    var whaIsYourNameText = "What is your name?";

    var text = """
        Hi, my name is Mary. What is your name? Hi, my name is Mary.
        """;

    var result = unit.tokenize(text);
    assertEquals(maryText, result.getFirst().text());
    assertEquals(whaIsYourNameText, result.getLast().text());

    assertEquals(2, result.getFirst().positions().size());
    assertEquals(1, result.getLast().positions().size());

    var maryPositions = List.of(
        new TokenPosition(text.indexOf(maryText), text.indexOf(maryText) + maryText.length()),
        new TokenPosition(text.lastIndexOf(maryText),
            text.lastIndexOf(maryText) + maryText.length())
    );
    assertEquals(maryPositions, result.getFirst().positions());

    var whatIsYourNamePositions = List.of(new TokenPosition(text.indexOf(whaIsYourNameText),
        text.indexOf(whaIsYourNameText) + whaIsYourNameText.length()));
    assertEquals(whatIsYourNamePositions, result.getLast().positions());
  }
}