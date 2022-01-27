package lx.lindx.bash.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lx.lindx.bash.sys.CmdUnix;

public class Util {

  private final static Logger LOGGER;

  static {
    LOGGER = LogManager.getLogger(Util.class.getSuperclass().getName());
  }

  public static Logger getLogger() {
    return LOGGER;
  }

  public static int getColumns() {
    return CmdUnix.getColumns();
  }
}
