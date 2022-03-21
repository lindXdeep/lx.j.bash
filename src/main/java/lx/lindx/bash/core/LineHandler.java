package lx.lindx.bash.core;

import lx.lindx.bash.api.ChangeDirectory;
import lx.lindx.bash.api.ListDirectory;
import lx.lindx.bash.parser.Command;
import lx.lindx.bash.parser.CommandParser;
import lx.lindx.bash.util.Util;
import lx.lindx.bash.view.Buffer;
import lx.lindx.bash.view.Console;

public class LineHandler {

  private Console console;
  private Buffer buffer;

  private Ps1 ps1;

  private ListDirectory ls;
  private ChangeDirectory cd;

  private PathParser pathParser;
  private CommandParser comParser;

  public LineHandler() {

    this.ls = new ListDirectory();
    this.cd = new ChangeDirectory();

    this.ps1 = new Ps1();
    this.console = new Console(ps1);
    this.buffer = new Buffer(console);

    this.pathParser = new PathParser(buffer);
    this.pathParser.setPs1(ps1);
    this.pathParser.setListDir(ls);
    this.pathParser.setTerminalView(console);

    this.comParser = new CommandParser();
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

    if (buffer.toString().endsWith("\\")) {

      console.setEdge(1);
      console.newLineAndReturnСarriage();
      buffer.deletePrevChar();
      console.next();
      console.print(">");
      console.shiftCol(1);

    } else {

      comParser.setCommandLine(buffer);

      while (comParser.hasNextCommand()) {

        Command command = new Executor(comParser.nextCommand()).make();

        System.out.println("[" + command + "][" + command.getOperation() + "]" + "["
            + command.getState());

        if (!command.getState())
          break;
      }

      console.setEdge(ps1.length() + 1);
      console.print("\n\r", ps1);
      console.newLine();

      buffer.dropBuffer();
    }
  }

  public void printResultLine() {
    console.shiftRow(ls.getNumRows() + 1);
    console.print(ps1, buffer);
    console.shiftCol(-1);
    console.next();
  }

  public void compleet() {

    pathParser.readPathBeforePos();

    if (!pathParser.isFullPathExists() ||
        (pathParser.isFullPathExists() && !pathParser.isFullPathEndSeparator())) {

      pathParser.appendSequence();

      if (pathParser.isPathEndsWithAppendSequence()) {
        ls.printDirsByCols(pathParser.getFiltredDirs());
        this.printResultLine();
      }

      pathParser.completePath();

    } else if (pathParser.isFullPathExists() && pathParser.isFullPathEndSeparator() && !pathParser.isBuffEmpty()) {
      ls.printDirsByCols(pathParser.getFullpath());
      this.printResultLine();
    }
  }

  public void makeLog() {

    Util.logKey(
        "\nF:" + pathParser.getFullpath() + "\nP:" +
            pathParser.getParentPath() + "\nC:" +
            pathParser.getChildPath() + "\n");

    Util.logKey(null,
        buffer.toString(),
        pathParser.getTmpPath(),
        buffer.getPos(),
        buffer.getSize(),
        console.getLinelength(),
        console.getRow(),
        console.getCol(),
        console.toEnd(),
        console.getSysCol());
  }
}