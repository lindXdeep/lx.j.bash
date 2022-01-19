package lx.lindx.bash.err;

public class UndefinedSequenceException extends NumberFormatException {
  public UndefinedSequenceException() {
    this("Escape sequence not defined");
  }

  public UndefinedSequenceException(String msg) {
    super(msg);
  }
}
