package yaindtask.tokenizers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class TokenizerUtilsTest {

  @Test
  void deduplicate() {

    var result = TokenizerUtils.deduplicate(List.of(
        new Token("test", new TokenPosition(1, 2)),
        new Token("test2", new TokenPosition(2, 4)),
        new Token("test", new TokenPosition(0, 1))
    ));

    assertEquals(List.of(
        new Token("test", new TokenPosition(0, 1), new TokenPosition(1, 2)),
        new Token("test2", new TokenPosition(2, 4))
    ), result);
  }
}