package yaindtask.tokenizers.word;

import java.util.List;
import yaindtask.tokenizers.word.filter.WordTokenizerFilter;
import yaindtask.tokenizers.word.filter.impl.RemoveLeadingAndTailingSymbolsWordTokenizerFilter;
import yaindtask.tokenizers.word.filter.impl.SplitByWhitespaceWordTokenizerFilter;

public class DefaultWordTokenizer implements WordTokenizer {

  private final List<WordTokenizerFilter> filters;

  public DefaultWordTokenizer() {
    this(List.of(
        new SplitByWhitespaceWordTokenizerFilter(),
        new RemoveLeadingAndTailingSymbolsWordTokenizerFilter()
    ));
  }

  public DefaultWordTokenizer(List<WordTokenizerFilter> filters) {
    this.filters = filters;
  }

  @Override
  public List<String> tokenize(String text) {
    var result = List.of(text);
    for (var filter : filters) {
      result = filter.filter(result);
    }
    return result;
  }
}
