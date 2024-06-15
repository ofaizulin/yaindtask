package yaindtask.tokenizers;

import java.util.List;

public interface Tokenizer {

  List<Token> tokenize(String text);
}
