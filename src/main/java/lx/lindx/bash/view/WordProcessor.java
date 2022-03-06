package lx.lindx.bash.view;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lx.lindx.bash.com.ListDirectory;
import lx.lindx.bash.core.Ps1;
import lx.lindx.bash.sys.EnvironmentVariables;

/**
 * Wordprocessor
 */
public class WordProcessor {

  private final static String SPTR = EnvironmentVariables.FILE_SEPARATOR;

  private ConsoleView termView;
  private BufferView buffView;

  private Ps1 ps1;

  private String workPath;
  private String tmpPath;
  private String fullpath;
  private String parentPath;
  private String childPath;

  private ListDirectory ls;

  private FiltredDirs filtredDirs;

  public WordProcessor(final FiltredDirs filtredDirs) {

    this.filtredDirs = filtredDirs;

    termView = new ConsoleView();
    buffView = new BufferView(termView);

    ps1 = new Ps1();
    workPath = ps1.getWorkingDirectory();

    ls = new ListDirectory();

    termView.setEdge(ps1.length() + 1);
    termView.clearScreen();
    termView.print(ps1);
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

  public void readPathBeforePos() {

    tmpPath = buffView.cutPathBeforePos();
    fullpath = tmpPath.startsWith(SPTR) ? tmpPath : workPath.concat(SPTR).concat(tmpPath);

    String subPaths = fullpath.endsWith(SPTR)
        ? fullpath.substring(0, fullpath.length() - 1)
        : fullpath;

    int idx = subPaths.lastIndexOf(SPTR);

    if (idx == -1) {
      parentPath = SPTR;
      childPath = "";
    } else {
      parentPath = subPaths.substring(0, ++idx);
      childPath = subPaths.substring(idx);
    }
  }

  public String getFullpath() {
    return fullpath;
  }

  public String getParentpath() {
    return this.parentPath;
  }

  public String getChildpath() {
    return this.childPath;
  }

  public void printDirs(final String fullPath) {

    System.out.println();
    ls.byCols(fullPath);

    termView.shiftRow(ls.getNumRows() + 1);

    termView.print(ps1);
    termView.print(buffView.getbuffer());
    termView.shiftCol(-1);
    termView.next();
  }

  public void printDirs(List<Path> dirs) {

    System.out.println();
    ls.byCols(dirs);

    termView.shiftRow(ls.getNumRows() + 1);

    termView.print(ps1);
    termView.print(buffView.getbuffer());
    termView.shiftCol(-1);
    termView.next();
  }

  public String getBuffer() {
    return buffView.getbuffer().toString();
  }

  public int getBufferPos() {
    return buffView.getPos();
  }

  public int getBufferSize() {
    return buffView.getSize();
  }

  public int getTermRow() {
    return termView.getRow();
  }

  public int getTermCol() {
    return termView.getCol();
  }

  public int getTermEnd() {
    return termView.toEnd();
  }

  public int getSysCol() {
    return termView.getSysCol();
  }

  public int getLinelength() {
    return termView.getLinelength();
  }

  public String getTmpPath() {
    return this.tmpPath;
  }

  public boolean isFullPathExists() {
    return Files.exists(Paths.get(fullpath)) && Files.exists(Paths.get(parentPath));
  }

  public boolean isFullPathEndSeparator() {
    return fullpath.endsWith(SPTR);
  }

  public void completePath() {

    StringBuilder result = new StringBuilder();
    char[] elem = filtredDirs.getDirs().get(0).toString().toCharArray();

    boolean ch = true;

    int i = 0;
    while (i < elem.length && ch) {
      for (Path c : filtredDirs.getDirs())
        ch = c.toString().toCharArray()[i] != elem[i] ? false : true;
      if (ch)
        result.append(elem[i++]);
    }

    String appendSeq = result.toString().substring(childPath.length());
    int appendlength = appendSeq.length();

    buffView.insertElem(appendSeq);
    buffView.shiftpos(appendlength);

    termView.print(buffView.getBufferFromPos());

    termView.shiftCol(appendlength - 1);
    termView.next();
  }

  public void completePathFull() {

    String pathElem = filtredDirs.get(0);

    int elemLength = pathElem.length(); // 7 dev-libs
    int childLength = childPath.length(); // 2 dev
    int appendlength = elemLength - childLength; // 7 - 2 = 5; // -libs

    buffView.insertElem(pathElem.substring(childLength)); // -libs // 5
    buffView.shiftpos(appendlength);

    termView.shiftCol(appendlength);

    buffView.insertElem(SPTR);
    buffView.shiftpos(1);

    termView.print(buffView.getBufferFromPos());
    termView.next();
  }
}