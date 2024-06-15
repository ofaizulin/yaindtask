package yaindtask.tokenizers;

import java.util.List;

public interface Tokenizer {

  List<? extends Token> tokenize(String text);
}
