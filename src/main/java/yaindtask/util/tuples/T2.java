package yaindtask.util.tuples;

public record T2<L, R>(L left, R right) {

  L key() {
    return left;
  }

  L first() {
    return left;
  }

  R value() {
    return right;
  }

  R last() {
    return right;
  }

  public static <L, R> T2<L, R> of(L left, R right) {
    return new T2<>(left, right);
  }
}
