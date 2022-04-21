package lx.lindx.bash.core;

import lx.lindx.bash.api.ChangeDirectory;
import lx.lindx.bash.api.ListDirectory;
import lx.lindx.bash.parser.CommandExpression;
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

  private Executor exec;

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

    this.exec = new Executor(console, ps1);
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

    int singleQuotes = buffer.getCountSymbol('\'') % 2;
    int doubleQuotes = buffer.getCountSymbol('\"') % 2;

    if (singleQuotes != 0 || doubleQuotes != 0) {

      buffer.insertChar('\n');
      console.addAtUnfinishedLine();

    } else if (singleQuotes == 0 && doubleQuotes == 0 && buffer.endsWith("\\")) {

      buffer.deletePrevChar();
      console.addAtUnfinishedLine();

    } else {

      CommandExpression command = null;

      if (buffer.getSize() != 0) {

        comParser.setCommandLine(buffer);

        while (comParser.hasNextCommand()) {

          command = exec.make(comParser.nextCommand());
          // System.out.println("[" + command.getCommand().substring(0, 4) +"][" +
          // command.getOptions() + "][" + command.getStdOutFileName() + "]" + "["
          // + command.getState());

          if (!command.getState())
            break;
        }
      }

      console.print("\n\r");
      console.setEdge(ps1.length() + 1);
      console.print(ps1);
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