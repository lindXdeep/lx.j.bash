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

import lx.lindx.bash.term.Terminal;

public class KeyProcessor {

  private LineHandler lineHandler;

  public KeyProcessor() {
    lineHandler = new LineHandler();
  }

  public void proccess(final String key) {

    if (key.length() == 1) {
      lineHandler.insertElem(key.charAt(0));
    } else {

      // --------- START TAB --------- //

      if (key.equals(KEY_TAB.name())) {

        Terminal.saneMode();

        lineHandler.compleet();

        Terminal.rawMode();

      } else if (key.equals(KEY_ENTER.name())) {
        lineHandler.enter();
      } else if (key.equals(KEY_BACKSPACE.name())) {
        lineHandler.deletePrevChar();
      } else if (key.equals(KEY_F1.name())) {

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
        lineHandler.deleteNextChar();
      } else if (key.equals(KEY_UP.name())) {
        // System.out.print(KEY_UP);
      } else if (key.equals(KEY_DOWN.name())) {

      } else if (key.equals(KEY_RIGHT.name())) {
        lineHandler.moveRight();
      } else if (key.equals(KEY_LEFT.name())) {
        lineHandler.moveLeft();
      }
    }

    lineHandler.makeLog();
  }
}
