package yaindtask.tokenizers.word.filter.impl;

import java.util.List;
import yaindtask.tokenizers.word.filter.WordTokenizerFilter;

public class RemoveLeadingAndTailingSymbolsWordTokenizerFilter implements WordTokenizerFilter {

  @Override
  public List<String> filter(String text) {

    int startIndex = 0;

    char[] chars = text.toCharArray();
    for (int i = 0; i < text.length(); i++) {
      if (!Character.isAlphabetic(chars[i])) {
        startIndex++;
      } else {
        break;
      }
    }

    int endIndex = text.length();
    for (int i = text.length() - 1; i >= startIndex; i--) {
      if (!Character.isAlphabetic(chars[i])) {
        endIndex--;
      } else {
        break;
      }
    }
    var res = text.substring(startIndex, endIndex);
    return res.isEmpty() ? List.of() : List.of(res);
  }
}
