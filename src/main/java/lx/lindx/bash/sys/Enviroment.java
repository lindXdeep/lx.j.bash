package lx.lindx.bash.sys;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Enviroment {

  public static final String USER_NAME = getProperty("user.name");
  public static final String COMP_NAME = getHostName();
  public static final String USER_HOME = getProperty("user.home");
  public static final String OSYS_NAME = getProperty("os.name");
  public static final String USER_LANG = getProperty("user.language");
  public static final String FILE_SEPARATOR = getProperty("file.separator");

  private static String getProperty(final String prop) {
    return System.getProperty(prop);
  }

  private static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "UnknownHost";
  }
}
