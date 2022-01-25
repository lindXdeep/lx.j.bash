package lx.lindx.bash.sys;

import static lx.lindx.bash.util.Util.getLogger;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

public class KeyEvent {

  private Console console;
  private Reader reader;
  private StringBuilder buffer;

  private int i = 0;

  public KeyEvent() {
    Tty.raw();
    console = System.console();
    reader = console.reader();
    buffer = new StringBuilder(9);
  }

  public String listenKey() {

    int ch;
    String key;

    try {

      while (!Thread.interrupted() && (ch = reader.read()) != -1) {
        if ((key = findKey(buffer.append(ch).toString())) != null) {
          buffer = new StringBuilder(10);
          return key;
        }
      }
    } catch (IOException e) {
      kill();
    }
    throw new RuntimeException();
  }

  private String findKey(String seq) {
    if (seq.equals(EscSeq.KEY_TAB.sequence())) {
      return EscSeq.KEY_TAB.name();
    } else if (seq.equals(EscSeq.KEY_ENTER.sequence())) {
      return EscSeq.KEY_ENTER.name();
    } else if (seq.equals(EscSeq.KEY_BACKSPACE.sequence())) {
      return EscSeq.KEY_BACKSPACE.name();
    } else if (seq.equals(EscSeq.KEY_F1.sequence())) {
      return EscSeq.KEY_F1.name();
    } else if (seq.equals(EscSeq.KEY_F2.sequence())) {
      return EscSeq.KEY_F2.name();
    } else if (seq.equals(EscSeq.KEY_F3.sequence())) {
      return EscSeq.KEY_F3.name();
    } else if (seq.equals(EscSeq.KEY_F4.sequence())) {
      return EscSeq.KEY_F4.name();
    } else if (seq.equals(EscSeq.KEY_F5.sequence())) {
      return EscSeq.KEY_F5.name();
    } else if (seq.equals(EscSeq.KEY_F6.sequence())) {
      return EscSeq.KEY_F6.name();
    } else if (seq.equals(EscSeq.KEY_F7.sequence())) {
      return EscSeq.KEY_F7.name();
    } else if (seq.equals(EscSeq.KEY_F8.sequence())) {
      return EscSeq.KEY_F8.name();
    } else if (seq.equals(EscSeq.KEY_F9.sequence())) {
      return EscSeq.KEY_F9.name();
    } else if (seq.equals(EscSeq.KEY_INSERT.sequence())) {
      return EscSeq.KEY_INSERT.name();
    } else if (seq.equals(EscSeq.KEY_DELETE.sequence())) {
      return EscSeq.KEY_DELETE.name();
    } else if (seq.equals(EscSeq.KEY_UP.sequence())) {
      return EscSeq.KEY_UP.name();
    } else if (seq.equals(EscSeq.KEY_DOWN.sequence())) {
      return EscSeq.KEY_DOWN.name();
    } else if (seq.equals(EscSeq.KEY_RIGHT.sequence())) {
      return EscSeq.KEY_RIGHT.name();
    } else if (seq.equals(EscSeq.KEY_LEFT.sequence())) {
      return EscSeq.KEY_LEFT.name();
    } else if (!seq.startsWith(EscSeq.KEY_ESC.sequence())) {
      return String.valueOf((char) Integer.parseInt(seq));
    }
    return null;
  }

  private void kill() {
    Tty.sane();
    getLogger().info("Exit shell");
    Thread.currentThread().interrupt();
  }
}
