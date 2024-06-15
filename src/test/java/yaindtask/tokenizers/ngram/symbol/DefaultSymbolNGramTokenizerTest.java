package yaindtask.tokenizers.ngram.symbol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.ngram.NGramToken;

class DefaultSymbolNGramTokenizerTest {

  @Test
  void tokenize() {

    var tokenizer = new DefaultSymbolNGramTokenizer(10);
    var result = tokenizer.tokenize("abc");

    var expected = List.of(
        new NGramToken("a", 1, new TokenPosition(0, 1)),
        new NGramToken("ab", 2, new TokenPosition(0, 2)),
        new NGramToken("abc", 3, new TokenPosition(0, 3)),
        new NGramToken("b", 1, new TokenPosition(1, 2)),
        new NGramToken("bc", 2, new TokenPosition(1, 3)),
        new NGramToken("c", 1, new TokenPosition(2, 3))
    );

    assertEquals(expected, result);
  }

  @Test
  void tokenizeWithRepetitive() {
    var tokenizer = new DefaultSymbolNGramTokenizer(10);
    var result = tokenizer.tokenize("aba");

    var expected = List.of(
        new NGramToken("a", 1, new TokenPosition(0, 1), new TokenPosition(2, 3)),
        new NGramToken("ab", 2, new TokenPosition(0, 2)),
        new NGramToken("aba", 3, new TokenPosition(0, 3)),
        new NGramToken("b", 1, new TokenPosition(1, 2)),
        new NGramToken("ba", 2, new TokenPosition(1, 3))
    );

    assertEquals(expected, result);
  }
}