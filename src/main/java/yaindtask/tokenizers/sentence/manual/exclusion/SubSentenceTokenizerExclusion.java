package yaindtask.tokenizers.sentence.manual.exclusion;

public interface SubSentenceTokenizerExclusion {

  boolean matches(char[] text, int index);

}
