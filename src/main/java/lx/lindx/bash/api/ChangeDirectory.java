package lx.lindx.bash.api;

import lx.lindx.bash.sys.EnvironmentVariables;

public class ChangeDirectory {

  public String toUserHome() {
    return EnvironmentVariables.USER_HOME;
  }

}