package yaindtask.tokenizers.sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegExSentenceTokenizer implements SentenceTokenizer {

  private static final String DEFAULT_REGEX = "[.!?](?=\\s+)(?<!\\.\\.)(?<!Dr\\.)";
  private static final Pattern DEFAULT_PATTERN = Pattern.compile(DEFAULT_REGEX, Pattern.MULTILINE);

  @Override
  public List<String> tokenize(String text) {
    text = text.trim();
    if (text.endsWith(".")) {
      text += " ";
    }
    System.out.printf("[%s]%n", text);
    var matcher = DEFAULT_PATTERN.matcher(text);

    var result = new ArrayList<String>();
    var prevEnd = 0;
    while (matcher.find()) {
      var end = matcher.end();
      result.add(text.substring(prevEnd, end).trim());
      prevEnd = end;
    }

    return result;
  }

  public static void main(String[] args) {
    var sentences = new RegExSentenceTokenizer().tokenize("""
        Hi pretty girl. Hello, i'm Mary! Let's play a game? A game... Ok, Dr. Brown he said.
        I've asked here "What's your name? How do you do?".
        """);

    sentences.forEach(s -> {
      System.out.println(s);
      System.out.println("---");
    });
  }
}
