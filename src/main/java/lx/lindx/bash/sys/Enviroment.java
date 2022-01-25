package lx.lindx.bash.sys;

public class Enviroment {

  public static final String USER_NAME = getProperty("user.name");
  public static final String COMP_NAME = getProperty("comp.name");
  public static final String USER_HOME = getProperty("user.home");
  public static final String OSYS_NAME = getProperty("os.name");
  public static final String USER_LANG = getProperty("user.language");
  public static final String FILE_SEPARATOR = getProperty("file.separator");

  private static String getProperty(final String prop) {
    return System.getProperty(prop);
  }
}


