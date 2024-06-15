package yaindtask.tokenizers.word;


import java.util.List;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.Tokenizer;

public interface WordTokenizer extends Tokenizer {

  List<Token> tokenize(Token token);
}
