package yaindtask.tokenizers.filter;

import java.util.List;
import yaindtask.tokenizers.Token;

public interface TokenFilter {

  List<Token> filter(Token token);
}
