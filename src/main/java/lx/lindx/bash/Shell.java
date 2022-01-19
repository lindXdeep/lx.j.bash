package lx.lindx.bash;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import lx.lindx.bash.err.UndefinedKeysException;
import lx.lindx.bash.sys.Esc;
import lx.lindx.bash.sys.Tty;
import static lx.lindx.bash.util.Util.getLogger;

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
    try {
      mainLoop();
    } catch (IOException e) {
      Tty.sane();
      getLogger().error(e.getMessage());
    }
  }

  private void mainLoop() throws IOException {

    int ch;

    while (!Thread.interrupted() && (ch = reader.read()) != -1) {

      try {

        String seq = buffer.append(ch).toString();

        if (seq.equals(Esc.KEY_TAB.sequence())) {
          printChar(Esc.KEY_TAB);
        } else if (seq.equals(Esc.KEY_ENTER.sequence())) {
          printChar(Esc.KEY_ENTER);
        } else if (seq.equals(Esc.KEY_BACKSPACE.sequence())) {
          printChar(Esc.KEY_BACKSPACE);
        } else if (seq.equals(Esc.KEY_F1.sequence())) {
          printChar(Esc.KEY_F1);
        } else if (seq.equals(Esc.KEY_F2.sequence())) {
          printChar(Esc.KEY_F2);
        } else if (seq.equals(Esc.KEY_F3.sequence())) {
          printChar(Esc.KEY_F3);
        } else if (seq.equals(Esc.KEY_F4.sequence())) {
          printChar(Esc.KEY_F4);
        } else if (seq.equals(Esc.KEY_F5.sequence())) {
          printChar(Esc.KEY_F5);
        } else if (seq.equals(Esc.KEY_F6.sequence())) {
          printChar(Esc.KEY_F6);
        } else if (seq.equals(Esc.KEY_F7.sequence())) {
          printChar(Esc.KEY_F7);
        } else if (seq.equals(Esc.KEY_F8.sequence())) {
          printChar(Esc.KEY_F8);
        } else if (seq.equals(Esc.KEY_F9.sequence())) {
          printChar(Esc.KEY_F9);
        } else if (seq.equals(Esc.KEY_INSERT.sequence())) {
          printChar(Esc.KEY_INSERT);
        } else if (seq.equals(Esc.KEY_DELETE.sequence())) {
          printChar(Esc.KEY_DELETE);
        } else if (seq.equals(Esc.KEY_UP.sequence())) {
          printChar(Esc.KEY_UP);
        } else if (seq.equals(Esc.KEY_DOWN.sequence())) {
          printChar(Esc.KEY_DOWN);
        } else if (seq.equals(Esc.KEY_RIGHT.sequence())) {
          printChar(Esc.KEY_RIGHT);
        } else if (seq.equals(Esc.KEY_LEFT.sequence())) {
          printChar(Esc.KEY_LEFT);
        } else if (!seq.startsWith(Esc.KEY_ESC.sequence())) {
          System.out.print((char) Integer.parseInt(buffer.toString()));
          buffer = new StringBuilder(10);
        }
      } catch (UndefinedKeysException e) {
        getLogger().error(e.getMessage());
        kill();
      }
    }
  }

  private void kill() {
    Tty.sane();
    getLogger().info("Exit shell");
    Thread.currentThread().interrupt();
  }

  private void printChar(Esc key) {
    System.out.print(key);
    buffer = new StringBuilder(10);
  }
}
