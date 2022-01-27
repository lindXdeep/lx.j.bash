package lx.lindx.bash.sys;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Console Parameters only Unix
 */
public class CmdUnix {

  private static byte[] buffer;

  private final static File TMP_FILE = new File(".tmp");
  private final static String sh = "/bin/sh";
  private final static String c = "-c";
  private final static String cmd = "tput cols > %s";

  private static int columns;

  static {
    buffer = new byte[64];
  }

  public static int getColumns() {
    execute(TMP_FILE.getName());
    setColumnsFromTempFile();
    return columns;
  }

  private static void setColumnsFromTempFile() {

    if (TMP_FILE.exists()) {
      try {
        columns = Integer.parseInt(readFile(TMP_FILE.getName()));
        deleteFile(TMP_FILE);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  private static String readFile(final String path) throws IOException {
    new DataInputStream(new FileInputStream(path)).read(buffer);
    return new String(buffer).trim();
  }

  private static boolean deleteFile(final File file) {
    return file.exists() ? file.delete() : false;
  }

  private static void execute(final String args) {
    try {
      exec(new String[] {
          sh, c, String.format(cmd, args)
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void exec(final String[] command) throws IOException {
    new ProcessBuilder(command).start();
  }
}