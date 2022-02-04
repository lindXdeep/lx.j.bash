package lx.lindx.bash.sys;

public enum EscapeSequences {
  KEY_TAB("9"),
  KEY_ENTER("13"),
  KEY_BACKSPACE("127"),

  KEY_F1("277980"),
  KEY_F2("277981"),
  KEY_F3("277982"),
  KEY_F4("277983"),

  KEY_F5("27914953126"),
  KEY_F6("27914955126"),
  KEY_F7("27914956126"),
  KEY_F8("27914957126"),
  KEY_F9("27915048126"),

  KEY_INSERT("279150126"),
  KEY_DELETE("279151126"),

  KEY_UP("279165"),
  KEY_DOWN("279166"),
  KEY_RIGHT("279167"),
  KEY_LEFT("279168"),

  KEY_ESC("27");

  private final String seq;

  EscapeSequences(final String seq) {
    this.seq = seq;
  }

  public String sequence() {
    return seq;
  }
}