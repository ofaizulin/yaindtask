package yaindtask.tokenizers.ngram.symbol;

import java.util.List;
import yaindtask.tokenizers.Tokenizer;
import yaindtask.tokenizers.ngram.NGramToken;

public interface SymbolNGramTokenizer extends Tokenizer {

  @Override
  List<NGramToken> tokenize(String text);
}
