package lx.lindx.bash.term;

import java.io.IOException;

import lx.lindx.bash.util.Util;

public class Terminal {

  private static Cols col;
  private static Stty tty;

  static {
    col = new Cols();
    tty = new Stty();
  }

  public static void rawMode() {

    try {
      tty.raw();
    } catch (InterruptedException | IOException e) {
      Util.log().error(e.getMessage());
      kill(e);
    }
  }

  public static void saneMode() {
    try {
      tty.sane();
    } catch (InterruptedException | IOException e) {
      Util.log().error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public static int getColumns() {
    try {
      return col.getColumns();
    } catch (IOException | InterruptedException e) {
      kill(e);
    }
    return 80;
  }

  public static void clear() {
    try {
      Exec.execute("clear");
    } catch (IOException | InterruptedException e) {
      kill(e);
    }
  }

  private static void kill(final Exception e) {
    Util.log().error(e.getMessage());
    saneMode();
    Thread.currentThread().interrupt();
  }
}
