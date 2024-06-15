package yaindtask.tokenizers.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;

class CaseInsensitiveTokenFilterTest {

  TokenFilter filter = new CaseInsensitiveTokenFilter();

  @Test
  void filter() {
    var result = filter.filter(new Token("Hello"));
    assertEquals(List.of(new Token("hello")), result);
  }
}