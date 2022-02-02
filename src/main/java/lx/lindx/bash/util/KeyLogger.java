package lx.lindx.bash.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class KeyLogger {

  private static final String LOG_FILE = ".keylog";
  private FileOutputStream keylogOut;

  public KeyLogger() {
    if (new File(LOG_FILE).exists()) {
      try {
        keylogOut = new FileOutputStream(LOG_FILE, false);
        keylogOut.write(new byte[] {}); // truncate file
        keylogOut.flush();
        keylogOut.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try {
      keylogOut = new FileOutputStream(LOG_FILE, true);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void logKey(int key) {
    logKey(Integer.toString(key));
  }

  public void logKey(final String key) {
    try {
      keylogOut.write(key.getBytes());
      keylogOut.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
