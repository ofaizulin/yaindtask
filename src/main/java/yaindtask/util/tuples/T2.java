package yaindtask.util.tuples;

public record T2<F, S>(F first, S second) {

  public static <F, S> T2<F, S> of(F first, S second) {
    return new T2<>(first, second);
  }
}
