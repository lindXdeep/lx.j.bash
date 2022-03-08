package lx.lindx.bash.core;

import lx.lindx.bash.api.ListDirectory;
import lx.lindx.bash.view.Buffer;
import lx.lindx.bash.view.Console;

public class LineHandler {

  private Console console;
  private Buffer buffer;

  private Ps1 ps1;

  private ListDirectory ls;

  private PathParser pathParser;

  public LineHandler() {

    this.ls = new ListDirectory();

    this.ps1 = new Ps1();
    this.console = new Console(ps1);
    this.buffer = new Buffer(console);

    this.pathParser = new PathParser(buffer);
    this.pathParser.setPs1(ps1);
    this.pathParser.setListDir(ls);
    this.pathParser.setTerminalView(console);
  }

  public void insertElem(final char key) {
    buffer.insertChar(key);
  }

  public void moveRight() {
    buffer.moveRight();
  }

  public void moveLeft() {
    buffer.moveLeft();
  }

  public void deleteNextChar() {
    buffer.deleteNextChar();
  }

  public void deletePrevChar() {
    buffer.deletePrevChar();
  }

  public void enter() {

    console.setEdge(ps1.length() + 1);
    console.print("\n\r", ps1);
    console.newLine();

    buffer.dropBuffer();
  }

  public void printResultLine() {
    console.shiftRow(ls.getNumRows() + 1);
    console.print(ps1, buffer.getbuffer());
    console.shiftCol(-1);
    console.next();
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

  public String[] getLogPaths() {
    return new String[] {
        pathParser.getFullpath(),
        pathParser.getParentPath(),
        pathParser.getChildPath()
    };
  }

  public String getBuffer() {
    return buffer.getbuffer().toString();
  }

  public String getTmpPath() {
    return pathParser.getTmpPath();
  }

  public int[] getLogPosInfo() {

    return new int[] {
        buffer.getPos(),
        buffer.getSize(),
        console.getLinelength(),
        console.getRow(),
        console.getCol(),
        console.toEnd(),
        console.getSysCol()
    };
  }
}