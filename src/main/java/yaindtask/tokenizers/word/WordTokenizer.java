package yaindtask.tokenizers.word;


import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.Tokenizer;
import yaindtask.tokenizers.filter.TokenFilter;

public interface WordTokenizer extends Tokenizer {

  List<Token> tokenize(Token token);

  WordTokenizer withExtraFilter(TokenFilter tokenFilter);
}
