package yaindtask.tokenizers.word;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.util.tuples.T2;

class DefaultWordTokenizerTest {

  DefaultWordTokenizer tokenizer = new DefaultWordTokenizer();

  @ParameterizedTest
  @MethodSource("tokenize_source")
  void tokenize(T2<Token, List<Token>> example) {
    var result = tokenizer.tokenize(example.first());
    assertEquals(example.second(), result);
  }

  static List<T2<Token, List<Token>>> tokenize_source() {
    return List.of(
        T2.of(new Token("Hello World, exiting World"), List.of(
            new Token("Hello", 0, 5),
            new Token("World", new TokenPosition(6, 11), new TokenPosition(21, 26)),
            new Token("exiting", 13, 20)
        )),
        T2.of(new Token("Exiting World.", 13, 27), List.of(
            new Token("Exiting", 13, 20),
            new Token("World", 21, 26)
        ))
    );
  }
}