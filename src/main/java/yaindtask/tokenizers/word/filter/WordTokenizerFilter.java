package yaindtask.tokenizers.word.filter;

import java.util.Collection;
import java.util.List;

public interface WordTokenizerFilter {

  List<String> filter(String text);

  default List<String> filter(List<String> texts) {
    return texts.stream().map(this::filter).flatMap(Collection::stream).toList();
  }
}
