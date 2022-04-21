package lx.lindx.bash.err;

/**
 * NoMoreSequence
 */
public class InvalidOptionExeption extends RuntimeException {

  public InvalidOptionExeption() {
    this("invalid option");
  }

  public InvalidOptionExeption(String... msg) {
    super(msg[0] + ": invalid option -- '" + msg[1] + "'");
  }
}