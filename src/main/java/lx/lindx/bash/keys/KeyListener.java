package lx.lindx.bash.keys;

import java.io.IOException;
import java.io.Reader;

public class KeyListener {

  private StringBuilder buffer;

  private Reader reader;
  private KeyEvent keyEvent;

  public KeyListener(Reader reader, KeyEvent keyEvent) {
    buffer = new StringBuilder(9);

    this.reader = reader;
    this.keyEvent = keyEvent;
  }

  public String nextKey() throws IOException {

    // chars sequences
    int ch;

    // result key code
    String key;

    while (!Thread.interrupted() && (ch = reader.read()) != -1) {
      if ((key = keyEvent.getKeyCode(buffer.append(ch).toString())) != null) {
        dropBuffer();
        return key;
      }
    }

    return null;
  }

  private void dropBuffer() {
    buffer = new StringBuilder(10);
  }
}
