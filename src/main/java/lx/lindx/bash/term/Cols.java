package lx.lindx.bash.term;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import lx.lindx.bash.util.Util;

public class Cols {

  private byte[] buffer;
  private final String TMP_FILE = ".tmp";
  private final String COLS = "tput cols > %s";
  private Path tmpFile;

  Cols() {
    buffer = new byte[64];
    tmpFile = Paths.get(TMP_FILE);
  }

  public int getColumns() throws IOException, InterruptedException {
    deleteFile(TMP_FILE);
    Exec.execute(COLS, TMP_FILE);
    return Integer.parseInt(readFile(TMP_FILE));
  }

  private String readFile(final String path) throws FileNotFoundException, IOException {

    if (isExists(path)) {
      try (DataInputStream din = new DataInputStream(new FileInputStream(path))) {
        din.read(buffer);
        return new String(buffer).replace("\n", "").replace("\r", "").trim();
      }
    }
    throw new RuntimeException("Can't read .tmp file");
  }

  private void deleteFile(final String path) {
    if (isExists(path)) {
      try {
        Files.delete(tmpFile);
      } catch (IOException e) {
        Util.log().error(e.getMessage());
      }
    }
  }

  private boolean isExists(final String file) {
    return Files.exists(tmpFile, LinkOption.NOFOLLOW_LINKS) &&
        !Files.isDirectory(tmpFile, LinkOption.NOFOLLOW_LINKS);
  }
}
