package lx.lindx.bash.core;

import java.util.Formatter;

import lx.lindx.bash.sys.EnvironmentVariables;
import lx.lindx.bash.sys.TerminalSequences;
import lx.lindx.bash.util.Util;

public class Ps1 {

  private int length;

  private String userName;
  private String compName;
  private String workingDirectory;

  private String fColor;
  private String fReset;

  private Formatter psx = new Formatter();

  public Ps1() {

    fColor = TerminalSequences.COLOR_BLUE;
    fReset = TerminalSequences.RESET_FORMAT;

    userName = EnvironmentVariables.USER_NAME;
    compName = EnvironmentVariables.COMP_NAME;

    workingDirectory = EnvironmentVariables.USER_HOME;

    setWorkingDirectory(workingDirectory);

    Util.logKey(String.valueOf(length));
  }

  public int length() {
    return length;
  }

  public Ps1 setWorkingDirectory(final String workingDirectory) {
    this.workingDirectory = workingDirectory;

    psx.format("%s@%s:%s%s%s$ ",
        userName,
        compName,
        fColor,
        workingDirectory,
        fReset);

    length = psx.toString().length() - (fColor.length() + fReset.length());

    return this;
  }

  public String getWorkingDirectory() {
    return workingDirectory;
  }

  @Override
  public String toString() {
    return psx.toString();
  }
}
