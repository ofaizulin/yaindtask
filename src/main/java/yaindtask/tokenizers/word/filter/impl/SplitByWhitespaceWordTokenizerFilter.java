package yaindtask.tokenizers.word.filter.impl;

import java.util.Arrays;
import java.util.List;
import yaindtask.tokenizers.word.filter.WordTokenizerFilter;

public class SplitByWhitespaceWordTokenizerFilter implements WordTokenizerFilter {

  @Override
  public List<String> filter(String text) {
    return Arrays.asList(text.split("\\s+"));
  }
}
