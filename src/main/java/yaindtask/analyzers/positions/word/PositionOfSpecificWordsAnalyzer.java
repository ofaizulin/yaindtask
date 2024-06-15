package yaindtask.analyzers.positions.word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;

public class PositionOfSpecificWordsAnalyzer implements Analyzer {

  @Override
  public AnalysisResult analyze(String text) {
    var words = text.split(", ");

    var data = new ArrayList<String[]>(words.length);

    for (var word : words) {
      var positions = findWordPosition(word, text);
      var positionsStr = positions.stream().map(String::valueOf).collect(Collectors.joining(", "));
      data.add(new String[]{word, positionsStr});
    }

    return new AnalysisResult(new String[]{"Word", "Positions"}, data);
  }

  private List<Integer> findWordPosition(String word, String text) {

    var positions = new ArrayList<Integer>();

    int startIndex = 0;
    int pos;
    while ((pos = text.indexOf(word, startIndex)) != -1) {
      positions.add(pos);
      startIndex++;
    }
    return positions;
  }
}
