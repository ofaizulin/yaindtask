package yaindtask.analyzers.print.frequency;

import java.util.ArrayList;
import java.util.TreeMap;
import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;

public class SymbolFrequencyAnalyzer implements Analyzer {

  @Override
  public AnalysisResult analyze(String text) {
    var symbolCount = new TreeMap<Integer, Long>();
    text.chars()
        .forEach(c -> symbolCount.compute(c, (character, count) -> count == null ? 1 : count + 1));

    var data = new ArrayList<String[]>();
    for (var entry : symbolCount.entrySet()) {
      var str = String.valueOf((char) entry.getKey().intValue());
      var code = entry.getKey();
      var frequency = entry.getValue();
      data.add(new String[]{str, String.valueOf(code), String.valueOf(frequency)});
    }

    return new AnalysisResult(new String[]{
        "Symbol", "Code", "Count"
    }, data);
  }
}
