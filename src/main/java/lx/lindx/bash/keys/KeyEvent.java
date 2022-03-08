package lx.lindx.bash.keys;

import static lx.lindx.bash.sys.EscapeSequences.KEY_BACKSPACE;
import static lx.lindx.bash.sys.EscapeSequences.KEY_DELETE;
import static lx.lindx.bash.sys.EscapeSequences.KEY_DOWN;
import static lx.lindx.bash.sys.EscapeSequences.KEY_ENTER;
import static lx.lindx.bash.sys.EscapeSequences.KEY_ESC;
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

public class KeyEvent {


  public String getKeyCode(final String sequence) {

    if (sequence.equals(KEY_TAB.sequence())) {
      return KEY_TAB.name();
    } else if (sequence.equals(KEY_ENTER.sequence())) {
      return KEY_ENTER.name();
    } else if (sequence.equals(KEY_BACKSPACE.sequence())) {
      return KEY_BACKSPACE.name();
    } else if (sequence.equals(KEY_F1.sequence())) {
      return KEY_F1.name();
    } else if (sequence.equals(KEY_F2.sequence())) {
      return KEY_F2.name();
    } else if (sequence.equals(KEY_F3.sequence())) {
      return KEY_F3.name();
    } else if (sequence.equals(KEY_F4.sequence())) {
      return KEY_F4.name();
    } else if (sequence.equals(KEY_F5.sequence())) {
      return KEY_F5.name();
    } else if (sequence.equals(KEY_F6.sequence())) {
      return KEY_F6.name();
    } else if (sequence.equals(KEY_F7.sequence())) {
      return KEY_F7.name();
    } else if (sequence.equals(KEY_F8.sequence())) {
      return KEY_F8.name();
    } else if (sequence.equals(KEY_F9.sequence())) {
      return KEY_F9.name();
    } else if (sequence.equals(KEY_INSERT.sequence())) {
      return KEY_INSERT.name();
    } else if (sequence.equals(KEY_DELETE.sequence())) {
      return KEY_DELETE.name();
    } else if (sequence.equals(KEY_UP.sequence())) {
      return KEY_UP.name();
    } else if (sequence.equals(KEY_DOWN.sequence())) {
      return KEY_DOWN.name();
    } else if (sequence.equals(KEY_RIGHT.sequence())) {
      return KEY_RIGHT.name();
    } else if (sequence.equals(KEY_LEFT.sequence())) {
      return KEY_LEFT.name();
    } else if (!sequence.startsWith(KEY_ESC.sequence())) {
      return String.valueOf((char) Integer.parseInt(sequence));
    }
    return null;
  }
}
