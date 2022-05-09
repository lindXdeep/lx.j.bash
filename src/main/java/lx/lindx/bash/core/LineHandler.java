package lx.lindx.bash.core;

import java.util.Iterator;

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

    char lastchar = 0;
    boolean openDoubleQuotes = false;
    boolean openSingeQuotes = false;
    StringBuilder tmpBuffer = new StringBuilder();

    Iterator iter = buffer.iterator();

    while (iter.hasNext()) {

      char c = (Character) iter.next();

      if (!openSingeQuotes && !openDoubleQuotes && (lastchar == '\\' & c == '\'')) {
        openSingeQuotes = false;
      } else if (!openDoubleQuotes /* && lastchar != '\\' */ && c == '\'') {
        openSingeQuotes = !openSingeQuotes ? true : false;
      } else if (!openSingeQuotes && lastchar != '\\' && c == '\"') {
        openDoubleQuotes = !openDoubleQuotes ? true : false;
      }

      if (lastchar == '\\' && (c != '&' & c != '|' & c != '>')) {

        if (!openSingeQuotes && !openDoubleQuotes) {

          tmpBuffer.setLength(tmpBuffer.length() - 1);

          if (c == '\\' || c == '\'' || c == '\"') {
            tmpBuffer.append(c);
            lastchar = iter.hasNext() ? (Character) iter.next() : lastchar;
            tmpBuffer.append(lastchar);
            continue;
          }
        }

        if (openDoubleQuotes && c == '\\') {
          lastchar = iter.hasNext() ? (Character) iter.next() : lastchar;
          tmpBuffer.append(lastchar);
          continue;
        }
      }

      tmpBuffer.append(lastchar = c);
    }

    this.buffer.update(tmpBuffer);

    if (openSingeQuotes || openDoubleQuotes) {

      buffer.insertChar('\n');
      console.addAtUnfinishedLine();

    } else if (!openSingeQuotes && !openDoubleQuotes &&
        (buffer.endsWith("\\") & !buffer.endsWith("\\\\"))) {

      if (console.getRow() >= 25)
        System.out.println();

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

  /**
   * @return Console return the console
   */
  public Console getConsole() {
    return console;
  }

  /**
   * @param console the console to set
   */
  public void setConsole(Console console) {
    this.console = console;
  }

  /**
   * @return Buffer return the buffer
   */
  public Buffer getBuffer() {
    return buffer;
  }

  /**
   * @param buffer the buffer to set
   */
  public void setBuffer(Buffer buffer) {
    this.buffer = buffer;
  }

  /**
   * @return Ps1 return the ps1
   */
  public Ps1 getPs1() {
    return ps1;
  }

  /**
   * @return ListDirectory return the ls
   */
  public ListDirectory getLs() {
    return ls;
  }

  /**
   * @param ls the ls to set
   */
  public void setLs(ListDirectory ls) {
    this.ls = ls;
  }

  /**
   * @return ChangeDirectory return the cd
   */
  public ChangeDirectory getCd() {
    return cd;
  }

  /**
   * @param cd the cd to set
   */
  public void setCd(ChangeDirectory cd) {
    this.cd = cd;
  }

  /**
   * @return PathParser return the pathParser
   */
  public PathParser getPathParser() {
    return pathParser;
  }

  /**
   * @param pathParser the pathParser to set
   */
  public void setPathParser(PathParser pathParser) {
    this.pathParser = pathParser;
  }

  /**
   * @return CommandParser return the comParser
   */
  public CommandParser getComParser() {
    return comParser;
  }

  /**
   * @param comParser the comParser to set
   */
  public void setComParser(CommandParser comParser) {
    this.comParser = comParser;
  }

  /**
   * @return Executor return the exec
   */
  public Executor getExec() {
    return exec;
  }

  /**
   * @param exec the exec to set
   */
  public void setExec(Executor exec) {
    this.exec = exec;
  }

}