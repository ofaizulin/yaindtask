package yaindtask.tokenizers.ngram.lexical;

import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.Tokenizer;
import yaindtask.tokenizers.ngram.NGramToken;

public interface LexicalNGramTokenizer extends Tokenizer {

  @Override
  List<NGramToken> tokenize(String text);

  List<NGramToken> tokenize(List<? extends Token> tokens);
}
