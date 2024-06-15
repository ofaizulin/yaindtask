package yaindtask.analyzers.positions.symbols;

import static yaindtask.analyzers.AnalysisResult.of;

import java.util.ArrayList;
import java.util.TreeMap;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;

public class PositionSymbolFrequencyAnalyzer implements Analyzer {

  private final int minFrequency;
  private final int maxFrequency;

  public PositionSymbolFrequencyAnalyzer(int minFrequency, int maxFrequency) {
    this.minFrequency = minFrequency;
    this.maxFrequency = maxFrequency;
  }

  @Override
  public AnalysisResult analyze(String text) {
    var chars = text.toCharArray();
    var data = new TreeMap<Integer, Integer>();
    for (char aChar : chars) {
      data.compute((int) aChar, (c, count) -> count == null ? 1 : count + 1);
    }

    var result = new ArrayList<String[]>();
    for (var entry : data.entrySet()) {
      var symbol = String.valueOf((char) (int) entry.getKey());
      var code = String.valueOf(entry.getKey());
      var frequency = entry.getValue();
      if (frequency >= minFrequency && frequency <= maxFrequency) {
        result.add(new String[]{symbol, code, String.valueOf(frequency)});
      }
    }

    return of("Symbol", "Symbol Code", "Frequency", result);
  }
}
