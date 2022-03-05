package lx.lindx.bash.view;

import lx.lindx.bash.core.Ps1;
import lx.lindx.bash.sys.EnvironmentVariables;

public class BufferView {

  private final static String SPTR = EnvironmentVariables.FILE_SEPARATOR;

  private ConsoleView termView;

  private StringBuffer buffer;
  private int bufPos;
  private int bufSize;

  private Ps1 ps1;

  public BufferView(ConsoleView termView) {
    this.buffer = new StringBuffer();
    this.termView = termView;

    bufSize = buffer.length();
  }

  public void insertChar(char key) {

    if (bufPos == bufSize) {
      buffer.append(key);
      termView.print(key);
    } else {
      buffer.insert(bufPos, key);
      termView.print(buffer.substring(bufPos));
    }

    termView.setLineLength(bufSize = buffer.length());
    termView.next();
    bufPos++;
  }

  public void dropBuffer() {
    buffer = new StringBuffer();
    bufPos = 0;
    bufSize = 0;
  }

  public void deletePrevChar() {

    if (bufPos > 0) {

      buffer.deleteCharAt(--bufPos);
      buffer.append(' ');

      termView.print("\b", bufPos == bufSize ? " " : buffer.substring(bufPos));

      if (termView.prev()) {
        termView.print(buffer.substring(bufPos));
      }

      termView.lastPos();
      termView.setLineLength(--bufSize);
      buffer.setLength(bufSize);
    }
  }

  public void moveRight() {
    if (bufPos < bufSize) {
      termView.next();
      bufPos++;
    }
  }

  public void moveLeft() {
    if (bufPos > 0) {
      termView.prev();
      bufPos--;
    }
  }

  public void deleteNextChar() {
    if (bufPos < bufSize) {
      buffer.deleteCharAt(bufPos);
      buffer.append(' ');
      termView.print(buffer.substring(bufPos));
      termView.lastPos();
      buffer.setLength(--bufSize);
    }
  }

  public void insertElem(final String elem) {
    buffer.insert(bufPos, elem);
    termView.print(elem);
  }

  public void shiftpos(int move) {
    bufPos += move;
    bufSize = buffer.length();
  }

  public String cutPathBeforePos() {

    if (buffer.length() < 1)
      return "";

    String str = buffer.substring(0, bufPos);
    int idx = str.lastIndexOf(32);

    if (idx != -1) {
      return str.substring(idx + 1, bufPos);
    }
    return str.substring(0, bufPos);
  }

  public String getBufferFromPos() {
    return buffer.substring(bufPos);
  }

  public StringBuffer getbuffer() {
    return this.buffer;
  }

  public int getPos() {
    return this.bufPos;
  }

  public int getSize() {
    return this.bufSize;
  }
}
