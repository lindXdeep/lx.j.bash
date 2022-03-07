package lx.lindx.bash.view;

import java.nio.file.Path;
import java.util.List;

import lx.lindx.bash.com.ListDirectory;
import lx.lindx.bash.core.Ps1;

/**
 * Wordprocessor
 */
public class WordProcessor {

  private ConsoleView termView;
  private BufferView buffView;

  private Ps1 ps1;

  private ListDirectory ls;

  private PathParser pathParser;

  public WordProcessor() {

    this.ls = new ListDirectory();

    this.ps1 = new Ps1();
    this.termView = new ConsoleView(ps1);
    this.buffView = new BufferView(termView);

    this.pathParser = new PathParser(buffView);
    this.pathParser.setPs1(ps1);
    this.pathParser.setListDir(ls);
    this.pathParser.setTerminalView(termView);
  }

  public void insertElem(final char key) {
    buffView.insertChar(key);
  }

  public void moveRight() {
    buffView.moveRight();
  }

  public void moveLeft() {
    buffView.moveLeft();
  }

  public void deleteNextChar() {
    buffView.deleteNextChar();
  }

  public void deletePrevChar() {
    buffView.deletePrevChar();
  }

  public void enter() {

    termView.setEdge(ps1.length() + 1);
    termView.print("\n\r", ps1);
    termView.newLine();

    buffView.dropBuffer();
  }

  public void printResultLine() {
    termView.shiftRow(ls.getNumRows() + 1);
    termView.print(ps1, buffView.getbuffer());
    termView.shiftCol(-1);
    termView.next();
  }

  public void compleet() {

    pathParser.readPathBeforePos();

    if (!pathParser.isFullPathExists() ||
        (pathParser.isFullPathExists() && !pathParser.isFullPathEndSeparator())) {

      pathParser.appendSequence();

      if (pathParser.isPathEndsWithAppendSequence()) {
        ls.byCols(pathParser.getFiltredDirs());
        this.printResultLine();
      }

      pathParser.completePath();

    } else if (pathParser.isFullPathExists() && pathParser.isFullPathEndSeparator()) {
      ls.byCols(pathParser.getFullpath());
      this.printResultLine();
    }
  }

  /*--------forLog--------*/

  public String getFullpath() {
    return pathParser.getFullpath();
  }

  public String getParentpath() {
    return pathParser.getParentPath();
  }

  public String getChildpath() {
    return pathParser.getChildPath();
  }

  public String getBuffer() {
    return buffView.getbuffer().toString();
  }

  public String getTmpPath() {
    return pathParser.getTmpPath();
  }

  public int[] getPosInfo() {

    return new int[] {
        buffView.getPos(),
        buffView.getSize(),
        termView.getLinelength(),
        termView.getRow(),
        termView.getCol(),
        termView.toEnd(),
        termView.getSysCol()
    };
  }
}