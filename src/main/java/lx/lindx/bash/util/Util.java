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
   * @param key  - Current key char
   * @param args
   * 
   *             <pre>
   * arg[0] = "buf-pos" - cursor position in line buffer.
   *             </pre>
   * 
   *             <pre>
   * arg[1] = "buf-size" - line buffer size.
   *             </pre>
   * 
   *             <pre>
   * arg[2] = "t-linelength" - currrent line length in terminal.
   *             </pre>
   * 
   *             <pre>
   * arg[3] = "t-row" - cursor position row.
   *             </pre>
   * 
   *             <pre>
   * arg[4] = "t-col" - cursor position col.
   *             </pre>
   * 
   *             <pre>
   * arg[5] = "t-end" - left to the end.
   *             </pre>
   * 
   *             <pre>
   * arg[6] = "syscol" - current columns size in terminal.
   *             </pre>
   * 
   */
  public static void logKey(final String key, int... args) {
    KEYLOG.logKey(String.format(
        "[%-13s] buf-pos:%-2d, buf-sz:%-2d, t-linelength:%-2d, t-row:%-2d, t-col:%-2d, t-end:%-2d, syscol:%d\n",
        key, args[0], args[1], args[2], args[3], args[4], args[5], args[6]));
  }

  public static void logKey(final String str) {
    KEYLOG.logKey(str);
  }
}
