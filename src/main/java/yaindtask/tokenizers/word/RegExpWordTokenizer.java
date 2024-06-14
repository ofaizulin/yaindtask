package yaindtask.tokenizers.word;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RegExpWordTokenizer implements WordTokenizer {

  private static final Pattern PATTERN = Pattern.compile("\\s+|・");

  @Override
  public List<String> tokenize(String text) {
    return Arrays.stream(text.split("\\s+"))
        .toList();
  }
}
