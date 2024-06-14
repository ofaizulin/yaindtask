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
}
