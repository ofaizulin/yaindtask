package yaindtask.analyzers;

import java.util.List;

public class AnalysisResult {

  private String[] headers;

  private List<String[]> data;

  public AnalysisResult(String[] headers, List<String[]> data) {
    this.headers = headers;
    this.data = data;
  }

  public String[] getHeaders() {
    return headers;
  }

  public List<String[]> getData() {
    return data;
  }

  public static AnalysisResult of(String header, List<String[]> data) {
    return new AnalysisResult(new String[]{header}, data);
  }

  public static AnalysisResult of(String header1, String header2, List<String[]> data) {
    return new AnalysisResult(new String[]{header1, header2}, data);
  }

  public static AnalysisResult of(String header1, String header2, String header3,
      List<String[]> data) {
    return new AnalysisResult(new String[]{header1, header2, header3}, data);
  }
}
