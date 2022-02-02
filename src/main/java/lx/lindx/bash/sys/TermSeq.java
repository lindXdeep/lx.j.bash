package lx.lindx.bash.sys;

/**
 * Terminal control sequences.
 */
public class TermSeq {

  /**
   * Clear the screen
   */
  public static final String CLS = "\033[2J";

  /**
   * Move the cursor to the upper-left corner of the screen.
   */
  public static final String CURR_UP_LEFT = "\033[H";

  /**
   * Move the cursor to row r, column c.
   */
  public static final String MOV_CURR_R_C = "\033[%d;%dH";

  /**
   * Delete everything from the cursor to the end of the line.
   */
  public static final String CURR_DEL_ALL = "\033[K";

  /**
   * Reset special formatting (such as colour).
   */
  public static final String RESET_FORMAT = "\033[0m";
  /**
   * Black text.
   */
  public static final String COLOR_BLACK = "\033[30m";
  /**
   * Red text.
   */
  public static final String COLOR_RED = "\033[31m";
  /**
   * Green text.
   */
  public static final String COLOR_GREEN = "\033[32m";
  /**
   * Yellow text.
   */
  public static final String COLOR_YELLOW = "\033[33m";
  /**
   * Blue text.
   */
  public static final String COLOR_BLUE = "\033[34m";
  /**
   * Magenta text.
   */
  public static final String COLOR_MAGENTA = "\033[35m";
  /**
   * Cyan text.
   */
  public static final String COLOR_CYAN = "\033[36m";
  /**
   * White text.
   */
  public static final String COLOR_WHITE = "\033[37m";

}
