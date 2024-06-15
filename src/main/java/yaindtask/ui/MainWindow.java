package yaindtask.ui;

import static java.util.regex.Pattern.compile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import yaindtask.analyzers.Analyzer;
import yaindtask.analyzers.positions.lexngram.PositionsOfLexNGramHavingN;
import yaindtask.analyzers.positions.lexngram.PositionsOfLexNGramThatMatchText;
import yaindtask.analyzers.positions.lexngram.PositionsOfLexNgramFromMintoMaxWithNWithoutSymbols;
import yaindtask.analyzers.positions.sentences.length.PositionsOfSentencesHavingLetterCount;
import yaindtask.analyzers.positions.sentences.length.PositionsOfSentencesHavingSymbolCount;
import yaindtask.analyzers.positions.sentences.length.PositionsOfSentencesHavingWordCount;
import yaindtask.analyzers.positions.sentences.lexngram.PositionsOfSentencesThatContainsLexicalNgram;
import yaindtask.analyzers.positions.symbolngram.PositionsOfSymbolNGramHavingN;
import yaindtask.analyzers.positions.symbolngram.PositionsOfSymbolNGramHavingText;
import yaindtask.analyzers.positions.symbols.PositionsOfSymbolHavingFrequencyAnalyzer;
import yaindtask.analyzers.positions.symbols.PositionsOfSymbolMatchingSymbolAnalyzer;
import yaindtask.analyzers.positions.word.PositionsOfSpecificWordAnalyzer;
import yaindtask.analyzers.positions.word.PositionsOfWordsContainingText;
import yaindtask.analyzers.positions.word.PositionsOfWordsEndingWithText;
import yaindtask.analyzers.positions.word.PositionsOfWordsHavingFrequency;
import yaindtask.analyzers.positions.word.PositionsOfWordsHavingLengthAnalyzer;
import yaindtask.analyzers.positions.word.PositionsOfWordsStartingWithText;
import yaindtask.analyzers.print.frequency.LexicalNGramFrequencyAnalyzer;
import yaindtask.analyzers.print.frequency.SymbolFrequencyAnalyzer;
import yaindtask.analyzers.print.frequency.WordLengthFrequencyAnalyzer;
import yaindtask.analyzers.print.length.sentence.SentenceLengthInLettersAnalyzer;
import yaindtask.analyzers.print.length.sentence.SentenceLengthInSymbolsAnalyzer;
import yaindtask.analyzers.print.length.sentence.SentenceLengthInWordsAnalyzer;
import yaindtask.analyzers.print.length.word.WordLengthHavingFrequencyAnalyzer;

public class MainWindow {

  public static final String LEVEL_CHAR = "CHAR";
  public static final String LEVEL_LETTER = "LETTER";
  public static final String LEVEL_WORD = "WORD";
  public static final String LEVEL_SENTENCE = "SENTENCE";

  public static final String TARGET_LENGTH = "LENGTH";
  public static final String TARGET_FREQUENCY = "FREQUENCY";
  public static final String TARGET_EQUAL = "EQUAL";
  public static final String TARGET_STARTS = "STARTS";
  public static final String TARGET_CONTAINS = "CONTAINS";
  public static final String TARGET_ENDS = "ENDS";
  public static final String TARGET_FIRST = "FIRST";

  private final Executor executor = Executors.newSingleThreadExecutor();

  private JFrame frame;
  private String mPath;
  private JTextField mNgramEdittext;
  private JLabel mNgramLabel;
  private String mText = "";
  private JTextField mTextFieldStopSymbols;
  private JLabel mLbStopSymols;
  private JPanel panel;
  private Mode mMode;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        MainWindow window = new MainWindow();
        window.frame.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Create the application.
   */
  public MainWindow() {
    initialize();
  }

  private void adjustAccordingToOptions(int index) {
    mNgramEdittext.setVisible(false);
    mNgramLabel.setVisible(false);
    mLbStopSymols.setVisible(false);
    mTextFieldStopSymbols.setVisible(false);
//   	  switch (index) {
//	   	 case 1:
//		 case 2:
//			  mNgramEdittext.setVisible(true);
//			  mNgramLabel.setVisible(true);
//			  mLbStopSymols.setVisible(true);
//			  mTextFieldStopSymbols.setVisible(false);
//			  mLbStopSymols.setVisible(false);
//			  break;
//		  case 0:
//		  case 4:
//			  mNgramEdittext.setVisible(false);
//			  mNgramLabel.setVisible(false);
//			  mLbStopSymols.setVisible(false);
//			  mTextFieldStopSymbols.setVisible(false);
//			break;
//		  case 5:
//			  mNgramEdittext.setVisible(false);
//			  mNgramLabel.setVisible(false);
//			  mLbStopSymols.setVisible(false);
//			  mTextFieldStopSymbols.setVisible(false);
//		  default:
//			  mNgramEdittext.setVisible(true);
//			  mNgramLabel.setVisible(true);
//			  mLbStopSymols.setVisible(true);
//			  mTextFieldStopSymbols.setVisible(true);
//			break;
//		}
  }

  enum ValueType {
    N_VALUE,
    F_VALUE,
    S_VALUE,
    SS_VALUE,
    L_VALUE
  }

  static class Node {

    int level;
    int index = -1;
    String title;
    int selectedChild = -1;
    ValueType valueType = null;
    Node[] childList;

    public Node() {
    }

    public Node(int level, int index, String title, Node[] childList) {
      this.level = level;
      this.index = index;
      this.childList = childList;
      this.title = title;
      this.valueType = null;
    }

    public Node(int level, int index, String title, ValueType valueType, Node[] childList) {
      this.level = level;
      this.index = index;
      this.childList = childList;
      this.title = title;
      this.valueType = valueType;
    }

    public String getLevel() {
      return "None";
    }

    public String getSubLevel() {
      return "None";
    }

    public String getTarget() {
      return "None";
    }

    public ValueType getValueType() {
      return valueType;
    }

    public String toString() {
      return title;
    }

    public Node getSelection() {
      if (childList.length > 0 && selectedChild > -1) {
        return childList[selectedChild];
      } else {
        return null;
      }
    }

    public boolean isValuable() {
      return getSelection() != null && getSelection().valueType != null;
    }
  }


  Node mCurrentNode = new Node(0, 0, "", new Node[]{
      new Node(1, 0, "Позначити позиції всіх", new Node[]{
          new Node(2, 0, "символів", new Node[]{
              new Node(3, 0, "що відповідають символу", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_CHAR;
                }

                public String getTarget() {
                  return TARGET_EQUAL;
                }
              },
              new Node(3, 1, "з частотою", ValueType.F_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_CHAR;
                }

                public String getTarget() {
                  return TARGET_FREQUENCY;
                }
              }
          }),
          new Node(2, 1, "символьних нграм", new Node[]{
              new Node(3, 0, "а саме n-грами =", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_CHAR;
                }

                public String getTarget() {
                  return TARGET_EQUAL;
                }
              },
              new Node(3, 1, "нових, для n=", ValueType.N_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_CHAR;
                }

                public String getTarget() {
                  return TARGET_FIRST;
                }
              },
              new Node(3, 2, "при n =", ValueType.N_VALUE, new Node[]{
                  new Node(4, 0, "з частотою", ValueType.F_VALUE, new Node[]{})
              })
          }),
          new Node(2, 2, "слів", new Node[]{
              new Node(3, 0, "а саме слова", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_EQUAL;
                }
              },
              new Node(3, 1, "довжиною", ValueType.L_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_LENGTH;
                }
              },
              new Node(3, 2, "з частотою", ValueType.F_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_FREQUENCY;
                }
              },
              new Node(3, 3, "що починаються з", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_STARTS;
                }
              },
              new Node(3, 4, "що містять нграму =", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_CONTAINS;
                }
              },
              new Node(3, 5, "що закінчуються на ngram-у", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_ENDS;
                }
              }
          }),
          new Node(2, 3, "лексичних n-грам", new Node[]{
              new Node(3, 0, "що відповідають нграмі ", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_EQUAL;
                }
              },
              new Node(3, 1, "з частотою", ValueType.F_VALUE, new Node[]{
                  new Node(4, 0, "при n =", ValueType.N_VALUE, new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_WORD;
                    }

                    public String getTarget() {
                      return TARGET_FREQUENCY;
                    }
                  },
              }),
              new Node(3, 2, "нових, при n=", ValueType.N_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_FIRST;
                }
              },
          }),
          new Node(2, 4, "речень", new Node[]{
              new Node(3, 0, "довжиною", ValueType.L_VALUE, new Node[]{
                  new Node(3, 0, "символів", new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_SENTENCE;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }

                    public String getSubLevel() {
                      return LEVEL_CHAR;
                    }
                  },
                  new Node(3, 1, "букв", new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_SENTENCE;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }

                    public String getSubLevel() {
                      return LEVEL_LETTER;
                    }
                  },
                  new Node(3, 2, "слів", new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_SENTENCE;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }

                    public String getSubLevel() {
                      return LEVEL_WORD;
                    }
                  }
              }),
              new Node(3, 1, "що містять лексичну n-граму", ValueType.S_VALUE, new Node[]{}) {
                public String getLevel() {
                  return LEVEL_SENTENCE;
                }

                public String getTarget() {
                  return TARGET_CONTAINS;
                }
              }
          })
      }),
      new Node(1, 1, "Вивести", new Node[]{
          new Node(2, 0, "довжини", new Node[]{
              new Node(3, 0, "слів", new Node[]{
                  new Node(4, 0, "частотою", ValueType.F_VALUE, new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_WORD;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }
                  },
              }) {
                public String getLevel() {
                  return LEVEL_WORD;
                }

                public String getTarget() {
                  return TARGET_LENGTH;
                }
              },
              new Node(3, 1, "речень", new Node[]{
                  new Node(4, 0, "у символах", new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_SENTENCE;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }

                    public String getSubLevel() {
                      return LEVEL_CHAR;
                    }
                  },
                  new Node(4, 1, "у буквах", new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_SENTENCE;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }

                    public String getSubLevel() {
                      return LEVEL_LETTER;
                    }
                  },
                  new Node(4, 2, "у словах", new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_SENTENCE;
                    }

                    public String getTarget() {
                      return TARGET_LENGTH;
                    }

                    public String getSubLevel() {
                      return LEVEL_WORD;
                    }
                  },
              })
          }),
          new Node(2, 1, "частоти", new Node[]{
              new Node(3, 0, "символів", new Node[]{}) {
                public String getLevel() {
                  return LEVEL_CHAR;
                }

                public String getTarget() {
                  return TARGET_FREQUENCY;
                }
              },
              new Node(3, 1, "слів", new Node[]{
                  new Node(4, 0, "довжиною", ValueType.L_VALUE, new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_WORD;
                    }

                    public String getTarget() {
                      return TARGET_FREQUENCY;
                    }
                  }
              }),
              new Node(3, 2, "лексичних n-грам", new Node[]{
                  new Node(4, 0, "при n =", ValueType.N_VALUE, new Node[]{}) {
                    public String getLevel() {
                      return LEVEL_CHAR;
                    }

                    public String getTarget() {
                      return TARGET_FREQUENCY;
                    }
                  },
              })
          })
      })
  });
  private JLabel lblPath;

  private void convert() {
    String path = null;
    String text = null;
    String separator = JOptionPane.showInputDialog(frame, "Вкажіть розділювач");
    try {
      path = new Chooser().getFilePath();
      text = IOUtils.readFile(path);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (separator == null || separator.isEmpty()) {
      separator = ",";
    }
    String[] numbers = text.split(separator);
    ArrayList<Integer> indexes = new ArrayList<>();
    for (int i = 0; i < numbers.length; i++) {
      if ("1".equals(numbers[i].trim())) {
        indexes.add(i);
      } else if ("0".equals(numbers[i].trim())) {
      } else {
        JOptionPane.showMessageDialog(null, "Неправильний файл або формат", "Error",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }
    }

    ArrayList<Integer> distances = getDistances(indexes);
    Integer[] array = new Integer[distances.size()];
    distances.toArray(array);
    IOUtils.writeFile(Arrays.toString(array).replace("[", "").replace("]", ""),
        new Chooser().getFileToSaveCsv());
  }

  private static ArrayList<Integer> getDistances(ArrayList<Integer> indeces) {
    ArrayList<Integer> distances = new ArrayList<Integer>();
    distances.add(indeces.get(0));
    for (int i = 1; i < indeces.size(); i++) {
      distances.add(indeces.get(i) - indeces.get(i - 1) - 1);
    }
    return distances;
  }

  private String readMode() throws Exception {
    mMode = new Mode();
    ArrayList<Integer> codeList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Node lastNode = null;
    StringBuilder phrase = new StringBuilder();
    for (int i = 0; i < panel.getComponentCount(); i++) {
      JPanel box = (JPanel) panel.getComponent(i);
      JComboBox combo = (JComboBox) box.getComponent(0);
      Node node = nodeMap.get(combo.hashCode());
      if (node.getSelection() != null) {
        codeList.add(node.getSelection().index);
        sb.append(node.getSelection().title).append(" ");
      }
      if (box.getComponentCount() > 1) {
        if (node.getSelection().getValueType() == ValueType.F_VALUE) {
          JLabel lFrom = (JLabel) box.getComponent(1);
          JTextField tfFrom = (JTextField) box.getComponent(2);
          String valueFrom = tfFrom.getText().trim();
          JLabel lTo = (JLabel) box.getComponent(3);
          JTextField tfTo = (JTextField) box.getComponent(4);
          String valueTo = tfTo.getText().trim();
          sb.append(lFrom.getText()).append(" ").append(valueFrom).append(" ")
              .append(lTo.getText()).append(" ").append(valueTo).append(" ");

          if (valueFrom != null && !valueFrom.isEmpty() && valueTo != null && !valueTo.isEmpty()) {
            if (node.isValuable()) {
              mMode.valueMap.put(node.getSelection().valueType, valueFrom + ":" + valueTo);
            } else {
              throw new Exception("Node is not valuable but contains texfield");
            }
          }
        } else {
          JTextField tf = (JTextField) box.getComponent(1);
          String value = tf.getText();
          if (node.getSelection().getValueType() == ValueType.SS_VALUE
              || node.getSelection().getValueType() == ValueType.S_VALUE) {
            value = value.replace(" ", "$space$");
          }
          if (node.getSelection().getValueType() != ValueType.SS_VALUE) {
            sb.append(value).append(" ");
          }
          value = value.trim();
          if (!value.isEmpty()) {
            if (node.isValuable()) {
              mMode.valueMap.put(node.getSelection().valueType, value);
            } else {
              throw new Exception("Node is not valuable but contains texfield");
            }
          }
        }


      }
      lastNode = node;
    }
    Integer[] code = new Integer[codeList.size()];
    code = codeList.toArray(code);
    mMode.code = code;
    mMode.phrase = sb.toString();
    mMode.lastNode = lastNode.getSelection() == null ? lastNode : lastNode.getSelection();
    return sb.toString();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 1220, 365);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mPath = new Chooser().getFilePath();
    if (mPath == null || mPath.isEmpty()) {
      JOptionPane.showMessageDialog(MainWindow.this.frame,
          "Файл не обрано! Програма звершена.",
          "Помилка!",
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    try {
      mText = IOUtils.readFile(mPath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    JButton btnNewButton = new JButton("Отримати результат");
    btnNewButton.addActionListener(e -> {
      try {
        analyze();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    });

    lblPath = new JLabel(mPath);

    mNgramLabel = new JLabel("N-грама");

    mNgramEdittext = new JTextField();
    mNgramEdittext.setText("а");
    mNgramEdittext.setColumns(10);

    mLbStopSymols = new JLabel("Стоп символи");

    mTextFieldStopSymbols = new JTextField();
    mTextFieldStopSymbols.setText(".");
    mTextFieldStopSymbols.setColumns(10);

    panel = new JPanel();
    panel.setBackground(Color.WHITE);
    FlowLayout flowLayout = (FlowLayout) panel.getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);

    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setBackground(Color.LIGHT_GRAY);

    var textViewer = new JTextArea();
    textViewer.setText(mText);
    tabbedPane.addTab("File Content", new JScrollPane(textViewer));

    JLabel label = new JLabel("Обрати :");

    GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                    .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(lblPath)
                        .addGap(18)
                        .addComponent(btnNewButton)
                        .addPreferredGap(ComponentPlacement.RELATED, 801, Short.MAX_VALUE)
                        .addGap(165))
                    .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(label)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                            .addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1114,
                                Short.MAX_VALUE)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(mNgramLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(mNgramEdittext, GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(95)
                                .addComponent(mLbStopSymols)
                                .addGap(18)
                                .addComponent(mTextFieldStopSymbols, GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 627, Short.MAX_VALUE)
                            )))
                    .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1169, Short.MAX_VALUE))
                .addGap(25))
    );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(20)
                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPath)
                        .addComponent(btnNewButton))
                    .addGroup(groupLayout.createSequentialGroup()
                        .addGap(18)))
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                    .addGroup(groupLayout.createSequentialGroup()
                        .addGap(13)
                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(mNgramLabel)
                            .addComponent(mNgramEdittext, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(mLbStopSymols)
                            .addComponent(mTextFieldStopSymbols, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(groupLayout.createSequentialGroup()
                        .addGap(18)
                        .addComponent(label)))
                .addGap(26)
                .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 148,
                    GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addGap(170))
    );

    addCombo();
    adjustAccordingToOptions(0);
    frame.getContentPane().setLayout(groupLayout);

    menuBar = new JMenuBar();
    initMenu();
    frame.setJMenuBar(menuBar);
  }


  HashMap<Integer, Node> nodeMap = new HashMap<>();
  private JTabbedPane tabbedPane;
  private JMenuBar menuBar;

  private void addResultTab1(String title, String text) {
    JTextArea textArea = new JTextArea(text);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    JScrollPane scroll = new JScrollPane(textArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    tabbedPane.addTab(title, scroll);

  }

  private void initMenu() {
    JMenu file = new JMenu("Файл");
    menuBar.add(file);
    JMenuItem open = new JMenuItem("Відкрити");
    open.addActionListener(e -> openNew());
    file.add(open);
    JMenuItem convert = new JMenuItem("Convert 10010.. to dt");
    convert.addActionListener(e -> convert());
    file.add(convert);
    JMenuItem help = new JMenuItem("Help");
    help.addActionListener(arg0 -> {
      String helpTxt = "Lviv Ivan Franko University. Xtractor Hacked\n\n";
      try {
        helpTxt += IOUtils.readFile("README.txt");
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      addResultTab1("Help", helpTxt);
    });
    menuBar.add(help);
  }

  protected void openNew() {
    try {
      mPath = new Chooser().getFilePath();
      mText = IOUtils.readFile(mPath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    lblPath.setText(mPath);
  }

  private void addCombo() {
    JComboBox comboBox = new JComboBox<>(mCurrentNode.childList);
    comboBox.setSelectedIndex(-1);
    comboBox.addActionListener(event -> {
      int selectedIndex = comboBox.getSelectedIndex();
      JComboBox currentBox = (JComboBox) event.getSource();
      int hashCode = currentBox.hashCode();
      mCurrentNode = nodeMap.get(hashCode);
      JPanel currentPanel = null;
      boolean wasRemoved = false;
      for (int i = panel.getComponentCount() - 1; i >= 0; i--) {
        long componentHash = ((JPanel) panel.getComponent(i)).getComponent(0).hashCode();
        if (componentHash == hashCode) {
          currentPanel = (JPanel) panel.getComponent(i);
          break;
        }
        panel.remove(i);
        Node node = nodeMap.get(hashCode);
        if (node != null) {
          node.selectedChild = -1;
          nodeMap.remove(node);
        }
        wasRemoved = true;
      }

      Node selectedNode = mCurrentNode.childList[selectedIndex];
      mCurrentNode.selectedChild = selectedIndex;
      if (mCurrentNode.isValuable()) {
        for (int i = currentPanel.getComponentCount() - 1; i > 0; i--) {
          currentPanel.remove(i);
        }
        if (mCurrentNode.getSelection().getValueType() == ValueType.F_VALUE) {
          JLabel labelFrom = new JLabel("від");
          labelFrom.setPreferredSize(new Dimension(25, 25));
          JLabel labelTo = new JLabel("до");
          labelTo.setPreferredSize(new Dimension(25, 25));
          JTextField tfFrom = new JTextField("");
          tfFrom.setPreferredSize(new Dimension(35, 25));
          JTextField tfTo = new JTextField("");
          tfTo.setPreferredSize(new Dimension(35, 25));
          if (currentPanel != null && currentPanel.getComponentCount() <= 1) {
            currentPanel.add(labelFrom);
            currentPanel.add(tfFrom);
            currentPanel.add(labelTo);
            currentPanel.add(tfTo);
            frame.invalidate();
            frame.validate();
            frame.repaint();
          }
        } else {
          JTextField tf = new JTextField("");
          tf.setPreferredSize(new Dimension(35, 25));
          if (currentPanel != null && currentPanel.getComponentCount() <= 1) {
            currentPanel.add(tf);
            frame.invalidate();
            frame.validate();
            frame.repaint();
          }
        }

      } else if (currentPanel != null && currentPanel.getComponentCount() > 1) {
        currentPanel.remove(currentPanel.getComponentCount() - 1);
        frame.invalidate();
        frame.validate();
        frame.repaint();
      }
      if (wasRemoved) {
        frame.invalidate();
        frame.validate();
        frame.repaint();
      }
      if (selectedNode.childList.length > 0) {
        mCurrentNode = selectedNode;
        addCombo();
      }
    });
    JPanel p = new JPanel();
    p.add(comboBox);
    panel.add(p);
    mCurrentNode.selectedChild = -1;
    nodeMap.put(comboBox.hashCode(), mCurrentNode);
    frame.invalidate();
    frame.validate();
    frame.repaint();
  }


  protected void analyze() throws IOException {
    Analyzer analyzer;
    try {
      var mode = readMode();
      analyzer = getAnalyzer(mode);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(MainWindow.this.frame,
          e.getClass().getSimpleName() + ": " + e.getMessage(),
          "Помилка!",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (analyzer == null) {
      JOptionPane.showMessageDialog(MainWindow.this.frame,
          "Analyzer is null!",
          "Помилка!",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    executor.execute(() -> {
      var analysisResult = analyzer.analyze(mText);
      SwingUtilities.invokeLater(() -> {
        var resultTabIndex = tabbedPane.indexOfTab("Результат");
        if (resultTabIndex != -1) {
          tabbedPane.remove(resultTabIndex);
        }

        var tableModel = new DefaultTableModel();
        Arrays.stream(analysisResult.getHeaders()).forEach(tableModel::addColumn);
        analysisResult.getData()
            .forEach(row -> tableModel.addRow(new Vector<>(Arrays.asList(row))));

        tabbedPane.addTab("Результат", new JScrollPane(new JTable(tableModel)));
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
      });
    });
  }

  public Analyzer getAnalyzer(String mode) {
    Map<Pattern, Supplier<Analyzer>> map = new LinkedHashMap<>();
    map.put(compile("Позначити позиції всіх символів що відповідають символу .+ "), () -> {
      var input = mode.substring(0, mode.length() - 1);
      input = input.replace("Позначити позиції всіх символів що відповідають символу ", "");
      if (input.length() != 1) {
        throw new IllegalArgumentException("Input length should be 1 but is " + input.length());
      }
      return new PositionsOfSymbolMatchingSymbolAnalyzer(input.charAt(0));
    });
    map.put(compile("Позначити позиції всіх символів з частотою від \\d+ до \\d+ "), () -> {
      var matcher = compile("\\d+").matcher(mode);
      matcher.find();
      var from = Integer.parseInt(matcher.group());
      matcher.find();
      var to = Integer.parseInt(matcher.group());
      return new PositionsOfSymbolHavingFrequencyAnalyzer(from, to);
    });
    map.put(compile("Позначити позиції всіх символьних нграм а саме n-грами = .+ "), () -> {
      var input = mode.substring(0, mode.length() - 1);
      input = input.replace("Позначити позиції всіх символьних нграм а саме n-грами = ", "");
      return new PositionsOfSymbolNGramHavingText(input);
    });
    map.put(compile("Позначити позиції всіх символьних нграм нових, для n= \\d+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх символьних нграм нових, для n= ", "");
      return new PositionsOfSymbolNGramHavingN(Integer.parseInt(input));
    });
    map.put(compile(
            "Позначити позиції всіх символьних нграм при n = \\d+ з частотою від \\d+ до \\d+ "),
        () -> {
          var matcher = compile("\\d+").matcher(mode);
          matcher.find();
          var n = Integer.parseInt(matcher.group());
          matcher.find();
          var from = Integer.parseInt(matcher.group());
          matcher.find();
          var to = Integer.parseInt(matcher.group());
          return new PositionsOfSymbolNGramHavingN(n, from, to);
        });
    map.put(compile("Позначити позиції всіх слів а саме слова .+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх слів а саме слова ", "");
      return new PositionsOfSpecificWordAnalyzer(input);
    });
    map.put(compile("Позначити позиції всіх слів довжиною \\d+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх слів довжиною ", "");
      return new PositionsOfWordsHavingLengthAnalyzer(Integer.parseInt(input));
    });
    map.put(compile("Позначити позиції всіх слів з частотою від \\d+ до \\d+ "), () -> {
      var input = mode.trim();
      var matcher = compile("\\d+").matcher(mode);
      matcher.find();
      var from = Integer.parseInt(matcher.group());
      matcher.find();
      var to = Integer.parseInt(matcher.group());
      return new PositionsOfWordsHavingFrequency(from, to);
    });
    map.put(compile("Позначити позиції всіх слів що починаються з .+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх слів що починаються з ", "");
      return new PositionsOfWordsStartingWithText(input);
    });
    map.put(compile("Позначити позиції всіх слів що містять нграму = .+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх слів що містять нграму = ", "");
      return new PositionsOfWordsContainingText(input);
    });
    map.put(compile("Позначити позиції всіх слів що закінчуються на ngram-у .+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх слів що закінчуються на ngram-у ", "");
      return new PositionsOfWordsEndingWithText(input);
    });
    map.put(compile(
        "Позначити позиції всіх лексичних n-грам що відповідають нграмі {2}.+ "), () -> {
      var input = mode;
      input = input.replace("Позначити позиції всіх лексичних n-грам що відповідають нграмі  ",
          "");
      input = input.trim();
      return new PositionsOfLexNGramThatMatchText(input);
    });
    map.put(compile(
            "Позначити позиції всіх лексичних n-грам з частотою від \\d+ до \\d+ при n = \\d+ "),
        () -> {
          var matcher = compile("\\d+").matcher(mode);
          matcher.find();
          var from = Integer.parseInt(matcher.group());
          matcher.find();
          var to = Integer.parseInt(matcher.group());
          matcher.find();
          var n = Integer.parseInt(matcher.group());
          return new PositionsOfLexNgramFromMintoMaxWithNWithoutSymbols(n, from, to);
        });
    map.put(compile(
        "Позначити позиції всіх лексичних n-грам нових, при n= \\d+ "), () -> {
      var input = mode.trim();
      input = input.replace("Позначити позиції всіх лексичних n-грам нових, при n= ", "");
      return new PositionsOfLexNGramHavingN(Integer.parseInt(input));
    });
    map.put(compile("Позначити позиції всіх речень довжиною \\d+ (символів|букв|слів) "), () -> {
      var intput = mode.substring("Позначити позиції всіх речень довжиною ".length())
          .split("\\s+");
      int length = Integer.parseInt(intput[0]);
      return switch (intput[1]) {
        case "символів" -> new PositionsOfSentencesHavingSymbolCount(length);
        case "букв" -> new PositionsOfSentencesHavingLetterCount(length);
        case "слів" -> new PositionsOfSentencesHavingWordCount(length);
        case null, default -> throw new IllegalArgumentException("Should not happen");
      };
    });
    map.put(compile("Позначити позиції всіх речень що містять лексичну n-граму .+"), () -> {
      var input = mode.replace("Позначити позиції всіх речень що містять лексичну n-граму ",
          "").trim();
      return new PositionsOfSentencesThatContainsLexicalNgram(input);
    });
    map.put(compile("Вивести довжини слів частотою від \\d+ до \\d+ "), new Supplier<Analyzer>() {
      @Override
      public Analyzer get() {
        var matcher = compile("\\d+").matcher(mode);
        matcher.find();
        var from = Integer.parseInt(matcher.group());
        matcher.find();
        var to = Integer.parseInt(matcher.group());
        return new WordLengthHavingFrequencyAnalyzer(from, to);
      }
    });
    map.put(compile("Вивести довжини речень у символах "), SentenceLengthInSymbolsAnalyzer::new);
    map.put(compile("Вивести довжини речень у буквах "), SentenceLengthInLettersAnalyzer::new);
    map.put(compile("Вивести довжини речень у словах "), SentenceLengthInWordsAnalyzer::new);
    map.put(compile("Вивести частоти символів "), SymbolFrequencyAnalyzer::new);
    map.put(compile("Вивести частоти слів довжиною \\d+ "), () -> {
      var matcher = compile("\\d+").matcher(mode);
      matcher.find();
      return new WordLengthFrequencyAnalyzer(Integer.parseInt(matcher.group()));
    });
    map.put(compile("Вивести частоти лексичних n-грам при n = (?<n>\\d+) "), () -> {
      var matcher = compile("\\d+").matcher(mode);
      matcher.find();
      return new LexicalNGramFrequencyAnalyzer(Integer.parseInt(matcher.group()));
    });

    for (var entry : map.entrySet()) {
      var pattern = entry.getKey();
      if (pattern.matcher(mode).matches()) {
        return entry.getValue().get();
      }
    }

    return null;
  }
}
