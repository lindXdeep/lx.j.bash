package lx.lindx.bash.api;

import java.io.File;
import java.io.IOException;

import lx.lindx.bash.sys.Enviroment;
import lx.lindx.bash.sys.KeyEvent;
import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

import static lx.lindx.bash.sys.EscSeq.*;
import static lx.lindx.bash.sys.TermSeq.*;

public class Shell implements Runnable {

  private String userName = Enviroment.USER_NAME;
  private String compName = Enviroment.COMP_NAME;
  private String currdir = new File("./").getAbsolutePath();
  private String ps1;

  private String key;
  private KeyEvent keyEvent;

  // position and buffer
  private StringBuilder buffer; // buffer for line
  private int bufSize; // buffer size
  private int bufPos; // buffer position
  private int lineLength; // terminal-lineLength
  private int row = 1; // terminal-row
  private int col = 1; // terminal-col

  // for backspace
  private int strEdge;
  private int edge;
  private int sysCol;

  public Shell() {
    keyEvent = new KeyEvent();
    buffer = new StringBuilder(256);
    ps1 = String.format("%s@%s:%s$ ", userName, compName, COLOR_BLUE + currdir + RESET_FORMAT);
  }

  @Override
  public void run() {

    сlearScreen();

    print(ps1);
    edge = ps1.length() - 8;
    col = edge;

    Util.logKey(key, bufPos, buffer.length(), lineLength, row, col, (sysCol = Terminal.getColumns()), sysCol - col);

    while ((key = keyEvent.listenKey()) != null) {

      if (key.length() == 1) {

        print(key.charAt(0));

        if (bufPos == lineLength) {
          buffer.append(key);
        } else {
          buffer.insert(bufPos, key);
          print(buffer.substring(bufPos + 1));
        }

        if (col >= sysCol) {
          move(++row, col = 1);
        } else {
          move(row, ++col);
        }

        lineLength++;
        bufPos++;
      }

      else {
        if (key.equals(KEY_TAB.name())) {
          // TODO: команда автокомплита
          System.out.print(KEY_TAB);
        } else if (key.equals(KEY_ENTER.name())) {
          print("\n\r", ps1);
          row++;
          col = edge;

          dropBuffer();

        } else if (key.equals(KEY_BACKSPACE.name())) {

          if (bufPos > 0 && bufPos <= lineLength) {

            buffer.deleteCharAt(bufPos - 1);
            print('\b', ' ');

            lineLength--;
            bufPos--;

            move(row, --col);

            if (col < 1 && row > 1) {

              move(--row, col = sysCol);
              buffer.append(' ');
              print(buffer.substring(bufPos));
              buffer.setLength(lineLength);
            }

            if (bufPos < lineLength && col != sysCol) {

              buffer.append(' ');
              print(buffer.substring(bufPos));
              buffer.setLength(lineLength);
            }
          }

        } else if (key.equals(KEY_F1.name())) {
          // System.out.print(EscSeq.KEY_F1);

          Util.logKey(key);

          Util.logKey("\n--------- buffer contains ---------\n");
          Util.logKey("content: " + buffer.toString());
          Util.logKey("\n  size: " + buffer.length());
          Util.logKey("\n   pos: " + bufPos);

          Util.logKey("\n-----------------------------------\n");

        } else if (key.equals(KEY_F2.name())) {

          Util.logKey("\nlineLength: " + lineLength + "\n");

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

          if (bufPos < lineLength) {
            buffer.deleteCharAt(bufPos);
            buffer.append(' ');
            print(buffer.substring(bufPos));
            buffer.deleteCharAt(--lineLength);
          }

        } else if (key.equals(KEY_UP.name())) {
          // System.out.print(KEY_UP);
        } else if (key.equals(KEY_DOWN.name())) {

        } else if (key.equals(KEY_RIGHT.name())) {

          if (bufPos < lineLength) {
            move(row, ++col);
            bufPos++;
          }

          if (col > sysCol) {
            move(++row, col = 1);
          }

        } else if (key.equals(KEY_LEFT.name())) {

          if (bufPos > 0) {
            move(row, --col);
            bufPos--;
          }

          if (col < 1 && row > 1) {
            move(--row, col = sysCol);
          }
        }

        move(row, col);
      }

      Util.logKey(key, bufPos, buffer.length(), lineLength, row, col, sysCol - col, (sysCol = Terminal.getColumns()));
    }

  }

  private void dropBuffer() {
    buffer = new StringBuilder();
    bufPos = 0;
    lineLength = 0;
  }

  public void сlearScreen() {
    print(CLS, CURR_UP_LEFT);
    Terminal.clear();
    row = 1;
    col = 1;
  }

  private void move(int row, int col) {
    print(String.format(MOV_CURR_R_C, row, col));
  }

  private void print(final String... str) {
    for (String s : str)
      System.out.print(s);
  }

  private void print(final char... ch) {
    for (char c : ch)
      System.out.print(c);
  }
}
