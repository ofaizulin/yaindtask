package yaindtask.tokenizers.ngram.lexical;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.ngram.NGramToken;

class DefaultLexicalNGramTokenizerTest {

  @Test
  void iLoveToLearnMaxN3() {

    // text = I love to learn
    var tokenizer = new DefaultLexicalNGramTokenizer(5);
    var input = List.of(
        new Token("I", 0, 1),
        new Token("love", 0, 6),
        new Token("to", 7, 9),
        new Token("learn", 10, 15)
    );

    var expected = List.of(
        new NGramToken("I", 1, new TokenPosition(0, 1)),
        new NGramToken("I love", 2, new TokenPosition(0, 6)),
        new NGramToken("I love to", 3, new TokenPosition(0, 9)),
        new NGramToken("I love to learn", 4, new TokenPosition(0, 15)),
        new NGramToken("love", 1, new TokenPosition(0, 6)),
        new NGramToken("love to", 2, new TokenPosition(0, 9)),
        new NGramToken("love to learn", 3, new TokenPosition(0, 15)),
        new NGramToken("to", 1, new TokenPosition(7, 9)),
        new NGramToken("to learn", 2, new TokenPosition(7, 15)),
        new NGramToken("learn", 1, new TokenPosition(10, 15))
    );

    assertEquals(expected, tokenizer.tokenize(input));
  }

  @Test
  void repeatingTokens() {
    // text = love I love
    var tokenizer = new DefaultLexicalNGramTokenizer(2);
    var result = tokenizer.tokenize(List.of(
        new Token("love", new TokenPosition(0, 4), new TokenPosition(7, 11)),
        new Token("I", 5, 6)
    ));

    var expected = List.of(
        new NGramToken("love", 1, new TokenPosition(0, 4), new TokenPosition(7, 11)),
        new NGramToken("love I", 2, new TokenPosition(0, 6)),
        new NGramToken("I", 1, new TokenPosition(5, 6)),
        new NGramToken("I love", 2, new TokenPosition(5, 11))
    );

    assertEquals(expected, result);
  }
}