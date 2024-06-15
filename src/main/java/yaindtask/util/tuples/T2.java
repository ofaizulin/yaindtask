package yaindtask.util.tuples;

public record T2<L, R>(L left, R right) {

  public L key() {
    return left;
  }

  public L first() {
    return left;
  }

  public R value() {
    return right;
  }

  public R second() {
    return right;
  }

  public R last() {
    return right;
  }

  public static <L, R> T2<L, R> of(L left, R right) {
    return new T2<>(left, right);
  }
}
