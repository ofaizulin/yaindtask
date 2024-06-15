package yaindtask.tokenizers.lexicalngram;

import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.Tokenizer;

public interface LexicalNGramTokenizer extends Tokenizer {

  @Override
  List<LexicalNGramToken> tokenize(String text);

  List<LexicalNGramToken> tokenize(List<? extends Token> tokens);
}
