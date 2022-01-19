package lx.lindx.bash;

import static lx.lindx.bash.util.Util.getLogger;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

import lx.lindx.bash.err.UndefinedKeysException;
import lx.lindx.bash.sys.Esc;
import lx.lindx.bash.sys.Tty;

public class Shell implements Runnable {

  private Console console;
  private Reader reader;
  private StringBuilder buffer;

  private int i = 0;

  public Shell() {
    Tty.raw();
    console = System.console();
    reader = console.reader();
    buffer = new StringBuilder(9);
  }

  @Override
  public void run() {

    int ch;

    try {
      while (!Thread.interrupted() && (ch = reader.read()) != -1) {

        String seq = buffer.append(ch).toString();

        findKey(seq);
      }
    } catch (IOException e) {
      kill();
    }

  }

  private void findKey(String seq) {

    if (seq.equals(Esc.KEY_TAB.sequence())) {
      processKey(Esc.KEY_TAB.name());
    } else if (seq.equals(Esc.KEY_ENTER.sequence())) {
      processKey(Esc.KEY_ENTER.name());
    } else if (seq.equals(Esc.KEY_BACKSPACE.sequence())) {
      processKey(Esc.KEY_BACKSPACE.name());
    } else if (seq.equals(Esc.KEY_F1.sequence())) {
      processKey(Esc.KEY_F1.name());
    } else if (seq.equals(Esc.KEY_F2.sequence())) {
      processKey(Esc.KEY_F2.name());
    } else if (seq.equals(Esc.KEY_F3.sequence())) {
      processKey(Esc.KEY_F3.name());
    } else if (seq.equals(Esc.KEY_F4.sequence())) {
      processKey(Esc.KEY_F4.name());
    } else if (seq.equals(Esc.KEY_F5.sequence())) {
      processKey(Esc.KEY_F5.name());
    } else if (seq.equals(Esc.KEY_F6.sequence())) {
      processKey(Esc.KEY_F6.name());
    } else if (seq.equals(Esc.KEY_F7.sequence())) {
      processKey(Esc.KEY_F7.name());
    } else if (seq.equals(Esc.KEY_F8.sequence())) {
      processKey(Esc.KEY_F8.name());
    } else if (seq.equals(Esc.KEY_F9.sequence())) {
      processKey(Esc.KEY_F9.name());
    } else if (seq.equals(Esc.KEY_INSERT.sequence())) {
      processKey(Esc.KEY_INSERT.name());
    } else if (seq.equals(Esc.KEY_DELETE.sequence())) {
      processKey(Esc.KEY_DELETE.name());
    } else if (seq.equals(Esc.KEY_UP.sequence())) {
      processKey(Esc.KEY_UP.name());
    } else if (seq.equals(Esc.KEY_DOWN.sequence())) {
      processKey(Esc.KEY_DOWN.name());
    } else if (seq.equals(Esc.KEY_RIGHT.sequence())) {
      processKey(Esc.KEY_RIGHT.name());
    } else if (seq.equals(Esc.KEY_LEFT.sequence())) {
      processKey(Esc.KEY_LEFT.name());
    } else if (!seq.startsWith(Esc.KEY_ESC.sequence())) {
      processKey(String.valueOf((char) Integer.parseInt(seq)));
      buffer = new StringBuilder(10);
    }
  }

  private void kill() {
    Tty.sane();
    getLogger().info("Exit shell");
    Thread.currentThread().interrupt();
  }

  private void processKey(final String key) {
    System.out.print(key);
    buffer = new StringBuilder(10);
  }
}
