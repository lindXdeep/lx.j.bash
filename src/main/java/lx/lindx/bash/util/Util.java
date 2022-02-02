package lx.lindx.bash.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {

  private final static Logger LOGGER;
  private final static KeyLogger KEYLOG;

  static {
    LOGGER = LogManager.getLogger(Util.class.getSuperclass().getName());
    KEYLOG = new KeyLogger();
  }

  public static void log(final String message) {
    LOGGER.info(message);
  }

  public static Logger log() {
    return LOGGER;
  }

  /**
   * 
   * @param key
   * @param args
   * 
   *             arg[0] = "buf-pos" - cursor position in line buffer.
   *             arg[1] = "buf-size" - line buffer size.
   *             arg[2] = "t-linelength" - currrent line length in terminal.
   *             arg[3] = "t-row" - cursor position row.
   *             arg[4] = "t-col" - cursor position col.
   *             arg[5] = "syscol" - current columns size in terminal.
   */
  public static void logKey(final String key, int... args) {
    KEYLOG.logKey(String.format("[%-13s] buf-pos:%-2d, buf-sz:%-2d, t-linelength:%-2d, t-row:%-2d, t-col:%-2d, t-end:%-2d, syscol:%d\n",
        key, args[0], args[1], args[2], args[3], args[4], args[5], args[6]));
  }

  public static void logKey(final String str) {
    KEYLOG.logKey(str);
  }
}
