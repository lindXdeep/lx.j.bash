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
import lx.lindx.bash.util.Util;
import lx.lindx.bash.view.WordProcessor;

public class KeyProcessor {

  private WordProcessor wordProc;

  public KeyProcessor() {
    wordProc = new WordProcessor();
  }

  public void proccess(final String key) {

    if (key.length() == 1) {
      wordProc.insertElem(key.charAt(0));
    } else {

      // --------- START TAB --------- //

      if (key.equals(KEY_TAB.name())) {

        Terminal.saneMode();

        wordProc.compleet();

        Terminal.rawMode();

      } else if (key.equals(KEY_ENTER.name())) {
        wordProc.enter();
      } else if (key.equals(KEY_BACKSPACE.name())) {
        wordProc.deletePrevChar();
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
        wordProc.deleteNextChar();
      } else if (key.equals(KEY_UP.name())) {
        // System.out.print(KEY_UP);
      } else if (key.equals(KEY_DOWN.name())) {

      } else if (key.equals(KEY_RIGHT.name())) {
        wordProc.moveRight();
      } else if (key.equals(KEY_LEFT.name())) {
        wordProc.moveLeft();
      }
    }

    Util.logKey(
        "\nF:" + wordProc.getFullpath() + "\nP:" + wordProc.getParentpath() + "\nC:" + wordProc.getChildpath() + "\n");

    Util.logKey(null,
        wordProc.getBuffer(),
        wordProc.getTmpPath(),
        wordProc.getPosInfo()[0],
        wordProc.getPosInfo()[1],
        wordProc.getPosInfo()[2],
        wordProc.getPosInfo()[3],
        wordProc.getPosInfo()[4],
        wordProc.getPosInfo()[5],
        wordProc.getPosInfo()[6]);
  }
}
