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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lx.lindx.bash.com.ChangeDirectory;
import lx.lindx.bash.com.ListDirectory;
import lx.lindx.bash.sys.EnvironmentVariables;
import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

public class KeyProcessor {

  private StringBuffer buffer;
  private int bufPos;
  private int bufSize;

  private String sptr;
  private String workPath;

  private String parentPath = "";
  private String tmpPath = "";

  private Ps1 ps1;
  private TerminalView termView;

  /**
   * Base Commands:
   * 
   * cd - Change the shell working directory.
   * ls - List information about the FILEs (the current directory by default).
   * 
   */

  private ListDirectory ls;
  private ChangeDirectory cd;

  public KeyProcessor() {

    buffer = new StringBuffer();
    termView = new TerminalView();

    ps1 = new Ps1();
    ls = new ListDirectory();
    cd = new ChangeDirectory();

    termView.setEdge(ps1.length() + 1);
    termView.clearScreen();
    termView.print(ps1);

    sptr = EnvironmentVariables.FILE_SEPARATOR;
    workPath = ps1.getWorkingDirectory();

    Util.logKey(null, buffer.toString(), parentPath, bufPos, bufSize,
        termView.getLinelength(), termView.getRow(), termView.getCol(), termView.toEnd(), termView.getSysCol());
  }

  public void proccess(final String key) {

    termView.setEdge(ps1.length() + 1);
    bufSize = buffer.length();

    if (key.length() == 1) {

      if (bufPos == bufSize) {
        buffer.append(key);
        termView.print(key.charAt(0));
      } else {
        buffer.insert(bufPos, key);
        termView.print(buffer.substring(bufPos));
      }

      termView.setLineLength(bufSize = buffer.length());
      termView.next();
      bufPos++;

    } else {
      if (key.equals(KEY_TAB.name())) {

        Terminal.saneMode();

        tmpPath = cutPathFromString(buffer); // находим в буфере нужную строку
        Path respath = Paths.get(workPath, tmpPath); // текущий путь от cd workdir + tmppath
        Util.logKey(":" + respath.toString());

        if (Files.exists(respath)) {

          List<Path> lph = new LinkedList<>();

          Path[] lsdir = ls.getDirs(respath.toString(), false);

          Util.logKey("\n:exists size:" + lsdir.length + "\n" + respath.toString());

          System.out.println();
          for (Path p : lsdir) {
            System.out.println(p);
          }
        } else {

          LinkedList<Path> lspath = new LinkedList<>();

          String parent = cutParentDir(tmpPath);
          String child = tmpPath.substring(parent.length());

          child = child.replace(sptr, "");

          Util.logKey("\n------");
          Util.logKey("\nне существует\n");
          Util.logKey("parent:" + parent);
          Util.logKey("\nchild:" + child);
          Util.logKey("\n------");

          Path[] lps = ls.getDirs(workPath + sptr + parent, false);

          for (Path p : lps) {
            if (p.toString().startsWith(child)) {
              lspath.add(p);
            }

          }

          if (lspath.size() == 1) {
            termView.print(lspath.get(0), "commleet");
          } else if (lspath.size() > 1) {
            System.out.println();
            for (Path p : lspath) {
              System.out.println(p);
            }
          }
        }

        Terminal.rawMode();

      } else if (key.equals(KEY_ENTER.name())) {

        termView.setEdge(ps1.length() + 1);
        termView.print("\n\r", ps1);
        termView.newLine();
        dropBuffer();

      } else if (key.equals(KEY_BACKSPACE.name())) {

        if (bufPos > 0) {

          buffer.deleteCharAt(--bufPos);
          buffer.append(' ');

          termView.print("\b", bufPos == bufSize ? " " : buffer.substring(bufPos));

          if (termView.prev()) {
            termView.print(buffer.substring(bufPos));
          }

          termView.lastPos();
          termView.setLineLength(--bufSize);
          buffer.setLength(bufSize);
        }

      } else if (key.equals(KEY_F1.name())) {

        Util.logKey("--------------------\n");
        Util.logKey(buffer.toString() + "\n");
        Util.logKey("--------------------\n");

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

        if (bufPos < bufSize) {
          buffer.deleteCharAt(bufPos);
          buffer.append(' ');
          termView.print(buffer.substring(bufPos));
          termView.lastPos();
          buffer.setLength(--bufSize);
        }

      } else if (key.equals(KEY_UP.name())) {
        // System.out.print(KEY_UP);
      } else if (key.equals(KEY_DOWN.name())) {
      } else if (key.equals(KEY_RIGHT.name())) {
        if (bufPos < bufSize) {
          termView.next();
          bufPos++;
        }
      } else if (key.equals(KEY_LEFT.name())) {
        if (bufPos > 0) {
          termView.prev();
          bufPos--;
        }
      }
    }

    Util.logKey(null, buffer.toString(), tmpPath, bufPos, bufSize,
        termView.getLinelength(), termView.getRow(), termView.getCol(), termView.toEnd(), termView.getSysCol());
  }

  private void dropBuffer() {
    buffer = new StringBuffer();
    bufPos = 0;
    bufSize = 0;
  }

  private String cutPathFromString(final StringBuffer buffer) {

    if (buffer.length() < 1)
      return "";

    String str = buffer.substring(0, bufPos);
    int idx = str.lastIndexOf(32);

    if (idx != -1) {
      return str.substring(idx + 1, bufPos);
    }
    return str.substring(0, bufPos);
  }

  private String cutParentDir(final String str) {
    int idx = str.lastIndexOf(sptr);

    if (idx == -1) {
      return "";
    }

    return str.substring(0, idx);
  }
}
