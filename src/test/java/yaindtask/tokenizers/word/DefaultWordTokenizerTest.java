package yaindtask.tokenizers.word;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.util.tuples.T2;

class DefaultWordTokenizerTest {

  WordTokenizer tokenizer = new DefaultWordTokenizer();

  @ParameterizedTest
  @MethodSource("tokenize_source")
  void tokenize(T2<String, List<Token>> example) {
    var result = tokenizer.tokenize(example.left());
    assertEquals(example.right(), result);
  }

  static List<T2<String, List<Token>>> tokenize_source() {
    return List.of(
        T2.of("Hello World, exiting World", List.of(
            new Token("Hello", new TokenPosition(0, 5)),
            new Token("World", new TokenPosition(6, 11), new TokenPosition(21, 26)),
            new Token("exiting", new TokenPosition(13, 20))
        ))
    );
  }
}