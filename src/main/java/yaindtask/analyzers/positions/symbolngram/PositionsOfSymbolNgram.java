package yaindtask.analyzers.positions.symbolngram;

import static yaindtask.analyzers.AnalysisResult.of;

import java.util.ArrayList;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;

public class PositionsOfSymbolNgram implements Analyzer {

  private final String textToFind;

  public PositionsOfSymbolNgram(String textToFind) {
    this.textToFind = textToFind;
  }

  @Override
  public AnalysisResult analyze(String text) {

    var data = new ArrayList<Integer>();

    int pos = 0;
    while ((pos = text.indexOf(textToFind, pos)) != -1) {
      data.add(pos);
      pos += 1;
      if (pos > text.length()) {
        break;
      }
    }

    return of("Positions", data.stream()
        .map(p -> new String[]{String.valueOf(p)})
        .toList()
    );
  }
}
