package lx.lindx.bash.err;

/**
 * UndefinedKeysException
 */
public class UndefinedKeysException extends RuntimeException {

  public UndefinedKeysException() {
    this("Unable to process command");
  }

  public UndefinedKeysException(String msg) {
    super(msg);
  }
}