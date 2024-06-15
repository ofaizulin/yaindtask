package yaindtask.analyzers.positions.symbols;

import static yaindtask.analyzers.AnalysisResult.of;

import java.util.ArrayList;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;

public class PositionsSymbolAnalyzer implements Analyzer {

  private final char character;

  public PositionsSymbolAnalyzer(char character) {
    this.character = character;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var positions = new ArrayList<String[]>();
    var chars = text.toCharArray();
    for (int index = 0; index < text.length(); index++) {
      if (chars[index] == character) {
        positions.add(new String[]{String.valueOf(index)});
      }
    }

    return of("Position", positions);
  }
}
