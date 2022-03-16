package lx.lindx.bash.core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import lx.lindx.bash.api.ListDirectory;
import lx.lindx.bash.api.util.EscapeCharacter;
import lx.lindx.bash.sys.EnvironmentVariables;
import lx.lindx.bash.util.Util;
import lx.lindx.bash.view.Buffer;
import lx.lindx.bash.view.Console;

public class PathParser {

  private static final String SPTR = EnvironmentVariables.FILE_SEPARATOR;

  private FiltredDirs filtredDirs;

  private String tmpPath;
  private String fullpath;
  private String parentPath;
  private String childPath;

  private Ps1 ps1;
  private Buffer bufferView;

  private Console termView;
  private ListDirectory ls;

  private String appendSeq;

  private int appendlength;

  public PathParser(Buffer buffView) {
    this.bufferView = buffView;
  }

  public void setPs1(Ps1 ps1) {
    this.ps1 = ps1;
  }

  public void setListDir(ListDirectory ls) {
    this.ls = ls;
    this.filtredDirs = new FiltredDirs(ls);
  }

  public void setTerminalView(Console termView) {
    this.termView = termView;
  }

  public void readPathBeforePos() {
    
    tmpPath = bufferView.cutPathBeforePos();

    Util.log("tmp::" + tmpPath);

    fullpath = tmpPath.startsWith(SPTR) ? tmpPath : ps1.getWorkingDirectory().concat(SPTR).concat(tmpPath);

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

    filtredDirs.setPath(parentPath);
    filtredDirs.filterbBy(childPath);
  }

  public void appendSequence() {
    appendSeq = this.getAppendSequence();
    appendlength = appendSeq.length();
  }

  public boolean isPathEndsWithAppendSequence() {
    return tmpPath.endsWith(appendSeq) && filtredDirs.size() > 1;
  }

  public Set<Path> getFiltredDirs() {
    return filtredDirs.getDirs();
  }

  public void completePath() {

    bufferView.insertElem(appendSeq);
    bufferView.shiftpos(appendlength);

    termView.print(bufferView.getBufferFromPos());
    termView.shiftCol(appendlength - 1);
    termView.next();
  }

  public String getAppendSequence() {

    StringBuilder result = new StringBuilder();
    char[] elem = (filtredDirs.size() == 0 ? "" : filtredDirs.get(0)).toCharArray();
    String appendSeq = "";

    if (filtredDirs.size() > 1) {
      boolean ch = true;

      int i = 0;
      while (i < elem.length && ch) {
        for (Path c : filtredDirs.getDirs())
          ch = c.toString().toCharArray()[i] != elem[i] ? false : true;
        if (ch)
          result.append(elem[i++]);
      }

      appendSeq = EscapeCharacter.check(result.toString().substring(childPath.length()));

    } else if (filtredDirs.size() == 1) {

      appendSeq = EscapeCharacter.check(filtredDirs.get(0).concat(SPTR).substring(childPath.length()));
    }

    return appendSeq;
  }

  public boolean isFullPathExists() {
    return Files.exists(Paths.get(fullpath)) && Files.exists(Paths.get(parentPath));
  }

  public boolean isFullPathEndSeparator() {
    return fullpath.endsWith(SPTR);
  }

  public String getTmpPath() {
    return this.tmpPath;
  }

  public String getFullpath() {
    return this.fullpath;
  }

  public String getParentPath() {
    return this.parentPath;
  }

  public String getChildPath() {
    return this.childPath;
  }

  public Buffer getBufferView() {
    return this.bufferView;
  }

  public int getChildLength() {
    return childPath.length();
  }

  public boolean isBuffEmpty() {
    return bufferView.getSize() == 0;
  }
}
