package yaindtask.analyzers.positions.symbolngram;

import yaindtask.analyzers.AnalysisResult;
import yaindtask.analyzers.Analyzer;

public class PositionsOfSymbolNGram implements Analyzer {

  private final int n;

  public PositionsOfSymbolNGram(int n) {

    this.n = n;
  }

  @Override
  public AnalysisResult analyze(String text) {
    return null;
  }
}
