package lx.lindx.bash.core;

import static lx.lindx.bash.sys.EscapeSequences.KEY_BACKSPACE;
import static lx.lindx.bash.sys.EscapeSequences.KEY_DELETE;
import static lx.lindx.bash.sys.EscapeSequences.KEY_DOWN;
import static lx.lindx.bash.sys.EscapeSequences.KEY_ENTER;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F1;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F2;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F3;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F4;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F5;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F6;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F7;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F8;
import static lx.lindx.bash.sys.EscapeSequences.KEY_F9;
import static lx.lindx.bash.sys.EscapeSequences.KEY_INSERT;
import static lx.lindx.bash.sys.EscapeSequences.KEY_LEFT;
import static lx.lindx.bash.sys.EscapeSequences.KEY_RIGHT;
import static lx.lindx.bash.sys.EscapeSequences.KEY_TAB;
import static lx.lindx.bash.sys.EscapeSequences.KEY_UP;

import lx.lindx.bash.util.Util;

public class KeyProcessor {

  private StringBuffer buffer;
  private int bufPos;
  private int bufSize;

  private TerminalView termView;
  private String ps1 = "lindx@lindx$";

  public KeyProcessor() {
    buffer = new StringBuffer();

    termView = new TerminalView();
    termView.setEdge(ps1.length() + 2);
    termView.clearScreen();
    termView.print(ps1 + " ");

    Util.logKey(null, bufPos, bufSize,
        termView.getLinelength(), termView.getRow(), termView.getCol(), termView.toEnd(), termView.getSysCol());
  }

  public void proccess(final String key) {

    termView.setEdge(ps1.length() + 1);
    bufSize = buffer.length();
    
    
    if (key.length() == 1) {

      if (bufPos == bufSize) {
        buffer.append(key);
        termView.print(key.charAt(0));
      } else {
        buffer.insert(bufPos, key);
        termView.print(buffer.substring(bufPos));
      }

      termView.setLineLength(bufSize = buffer.length());
      termView.next();
      bufPos++;

    } else {
      if (key.equals(KEY_TAB.name())) {

      } else if (key.equals(KEY_ENTER.name())) {

        termView.setEdge(ps1.length() + 2);
        termView.print("\n\r", ps1);
        termView.newLine();
        dropBuffer();

      } else if (key.equals(KEY_BACKSPACE.name())) {

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

      } else if (key.equals(KEY_F1.name())) {

        Util.logKey("--------------------\n");
        Util.logKey(buffer.toString() + "\n");
        Util.logKey("--------------------\n");

      } else if (key.equals(KEY_F2.name())) {
        // System.out.print(EscSeq.KEY_F2);
      } else if (key.equals(KEY_F3.name())) {
        // System.out.print(EscSeq.KEY_F3);
      } else if (key.equals(KEY_F4.name())) {
        // System.out.print(KEY_F4);
      } else if (key.equals(KEY_F5.name())) {
        // System.out.print(KEY_F5);
      } else if (key.equals(KEY_F6.name())) {
        // System.out.print(KEY_F6);
      } else if (key.equals(KEY_F7.name())) {
        // System.out.print(KEY_F7);
      } else if (key.equals(KEY_F8.name())) {
        // System.out.print(KEY_F8);
      } else if (key.equals(KEY_F9.name())) {
        // System.out.print(KEY_F9);
      } else if (key.equals(KEY_INSERT.name())) {
        // System.out.print(KEY_INSERT);
      } else if (key.equals(KEY_DELETE.name())) {

        if (bufPos < bufSize) {
          buffer.deleteCharAt(bufPos);
          buffer.append(' ');
          termView.print(buffer.substring(bufPos));
          termView.lastPos();
          buffer.setLength(--bufSize);
        }

      } else if (key.equals(KEY_UP.name())) {
        // System.out.print(KEY_UP);
      } else if (key.equals(KEY_DOWN.name())) {
      } else if (key.equals(KEY_RIGHT.name())) {
        if (bufPos < bufSize) {
          termView.next();
          bufPos++;
        }
      } else if (key.equals(KEY_LEFT.name())) {
        if (bufPos > 0) {
          termView.prev();
          bufPos--;
        }
      }
    }

    Util.logKey(key, bufPos, bufSize,
        termView.getLinelength(), termView.getRow(), termView.getCol(), termView.toEnd(), termView.getSysCol());
  }

  private void dropBuffer() {
    buffer = new StringBuffer();
    bufPos = 0;
    bufSize = 0;
  }
}
