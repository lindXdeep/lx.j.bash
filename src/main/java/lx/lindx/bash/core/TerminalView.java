package lx.lindx.bash.core;

import static lx.lindx.bash.sys.TerminalSequences.CLS;
import static lx.lindx.bash.sys.TerminalSequences.CURR_UP_LEFT;
import static lx.lindx.bash.sys.TerminalSequences.MOV_CURR_R_C;

import lx.lindx.bash.term.Terminal;

public class TerminalView {

  private int lineLength; // terminal-lineLength
  private int edge;
  private int row = 1; // terminal-row
  private int col = 1; // terminal-col

  private int sysCol; // system terminal width by cols

  public TerminalView() {
    sysCol = Terminal.getColumns();
    col = edge;
  }

  public void setLineLength(int lineLength) {
    this.lineLength = lineLength;
  }

  public void print(final String... str) {
    for (String s : str)
      System.out.print(s);
  }

  public void print(final char... ch) {

    for (char c : ch)
      System.out.print(c);
  }

  public boolean next() {
    if (col < sysCol) {
      move(row, ++col);
    } else {
      move(++row, col = 1);
      return true;
    }
    return false;
  }

  public boolean prev() {

    if (col == 1 && row > 1) {
      move(--row, col = sysCol);
      return true;
    } else {
      move(row, --col);
    }
    return false;
  }

  public void lastPos() {
    move(row, col);
  }

  public void newLine() {
    move(++row, col = edge);
    lineLength = 0;
  }

  private void move(int row, int col) {
    print(String.format(MOV_CURR_R_C, row, col));
  }

  public void clearScreen() {
    print(CLS, CURR_UP_LEFT);
    Terminal.clear();
    row = 1;
    col = edge;
  }

  public int getLinelength() {
    return this.lineLength;
  }

  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }

  public int getSysCol() {
    return this.sysCol;
  }

  public int toEnd() {
    return sysCol - col;
  }

  public void setEdge(int length) {
    this.edge = length;
  }
}
