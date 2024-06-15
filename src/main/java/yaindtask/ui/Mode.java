package yaindtask.ui;

import java.util.HashMap;

public class Mode {
    HashMap<MainWindow.ValueType, String> valueMap = new HashMap<>();
    public Integer[] code;
	public MainWindow.Node lastNode = new MainWindow.Node();
	public String phrase;

    public String getSingleCode() {
        String singleCode = "";
        for (int c : code) {
            singleCode+=Integer.toString(c);
        }
        return singleCode;
    }

    public int getFrequencyFrom() {
        String v = valueMap.get(MainWindow.ValueType.F_VALUE);
        if (v != null) {
            return Integer.parseInt(v.split(":")[0]);
        }
        return -1;
    }
    
    public int getFrequencyTo() {
        String v = valueMap.get(MainWindow.ValueType.F_VALUE);
        if (v != null) {
            return Integer.parseInt(v.split(":")[1]);
        }
        return -1;
    }

    public int getN() {
        String v = valueMap.get(MainWindow.ValueType.N_VALUE);
        if (v != null) {
            return Integer.parseInt(v);
        }
        return -1;
    }

    public int getL() {
        String v = valueMap.get(MainWindow.ValueType.L_VALUE);
        if (v != null) {
            return Integer.parseInt(v);
        }
        return -1;
    }

    public String getStopSymbols() {
        String v = valueMap.get(MainWindow.ValueType.SS_VALUE);
        if (v != null) {
            return v;
        }
        return "None";
    }

    public String getTargetString() {
        String v = valueMap.get(MainWindow.ValueType.S_VALUE);
        if (v != null) {
            return v;
        }
        return "None";
    }

	public String getLevel() {
		return lastNode.getLevel();
	}

	public String getTarget() {
		return lastNode.getTarget();
	}

	public String getSubLevel() {
		return lastNode.getSubLevel();
	}



}
