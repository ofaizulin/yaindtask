package yaindtask.tokenizers.ngram.lexical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import yaindtask.tokenizers.Token;
import yaindtask.tokenizers.TokenPosition;
import yaindtask.tokenizers.ngram.NGramToken;
import yaindtask.tokenizers.word.DefaultWordTokenizer;
import yaindtask.tokenizers.word.WordTokenizer;
import yaindtask.util.tuples.T2;

public class DefaultLexicalNGramTokenizer implements LexicalNGramTokenizer {

  private final WordTokenizer wordTokenizer;
  private final int maxN;

  public DefaultLexicalNGramTokenizer() {
    this(10);
  }

  public DefaultLexicalNGramTokenizer(int maxN) {
    this(new DefaultWordTokenizer(), maxN);
  }

  public DefaultLexicalNGramTokenizer(WordTokenizer wordTokenizer, int maxN) {
    this.wordTokenizer = wordTokenizer;
    this.maxN = maxN;
  }

  @Override
  public List<NGramToken> tokenize(String text) {
    return tokenize(wordTokenizer.tokenize(text));
  }

  @Override
  public List<NGramToken> tokenize(List<? extends Token> tokens) {
    // restore order of the text by token appearances
    var tokensInOrder = tokens.stream()
        .map(token -> token.positions().stream()
            .map(p -> T2.of(token.text(), p))
            .toList())
        .flatMap(Collection::stream)
        .sorted(Comparator.comparingInt(o -> o.second().startInclusive()))
        .toList();

    // build lexical n-grams up to N level
    var result = new LinkedHashMap<T2<String, Integer>, List<TokenPosition>>();
    for (int tokenIndex = 0; tokenIndex < tokensInOrder.size(); tokenIndex++) {

      for (int lastTokenIndex = tokenIndex;
          lastTokenIndex <= Math.min(tokenIndex + maxN, tokensInOrder.size()); lastTokenIndex++) {
        var toJoin = tokensInOrder.subList(tokenIndex, lastTokenIndex);

        if (toJoin.isEmpty()) {
          continue;
        }

        var text = toJoin.stream().map(T2::first).collect(Collectors.joining(" "));
        var n = toJoin.size();
        var position = new TokenPosition(toJoin.getFirst().second().startInclusive(),
            toJoin.getLast().second().endExclusive());
        result.compute(T2.of(text, n),
            (stringIntegerT2, tokenPositions) -> {
              if (tokenPositions == null) {
                tokenPositions = new ArrayList<>();
              }
              tokenPositions.add(position);
              return tokenPositions;
            });
      }
    }

    return result.entrySet().stream()
        .map(e -> new NGramToken(e.getKey().first(), e.getKey().second(), e.getValue()))
        .toList();
  }
}
