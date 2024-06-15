package yaindtask.tokenizers.filter;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;

class CompositeTokenFilterTest {

  @Test
  void filter() {

    CompositeTokenFilter filter = new CompositeTokenFilter(
        token -> List.of(new Token("Sentence1.", new TokenPosition(0, 10))),
        token -> List.of(new Token("Sentence1.", new TokenPosition(100, 110)))
    );

    System.out.println(filter.filter(new Token("a")));
  }
}