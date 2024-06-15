package yaindtask.tokenizers.lexicalngram.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.lexicalngram.LexicalNGramToken;

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
        new LexicalNGramToken("I", 1, new TokenPosition(0, 1)),
        new LexicalNGramToken("I love", 2, new TokenPosition(0, 6)),
        new LexicalNGramToken("I love to", 3, new TokenPosition(0, 9)),
        new LexicalNGramToken("I love to learn", 4, new TokenPosition(0, 15)),
        new LexicalNGramToken("love", 1, new TokenPosition(0, 6)),
        new LexicalNGramToken("love to", 2, new TokenPosition(0, 9)),
        new LexicalNGramToken("love to learn", 3, new TokenPosition(0, 15)),
        new LexicalNGramToken("to", 1, new TokenPosition(7, 9)),
        new LexicalNGramToken("to learn", 2, new TokenPosition(7, 15)),
        new LexicalNGramToken("learn", 1, new TokenPosition(10, 15))
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
        new LexicalNGramToken("love", 1, new TokenPosition(0, 4), new TokenPosition(7, 11)),
        new LexicalNGramToken("love I", 2, new TokenPosition(0, 6)),
        new LexicalNGramToken("I", 1, new TokenPosition(5, 6)),
        new LexicalNGramToken("I love", 2, new TokenPosition(5, 11))
    );

    assertEquals(expected, result);
  }
}