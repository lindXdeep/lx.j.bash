package lx.lindx.bash.com;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

import static lx.lindx.bash.util.Util.getColumns;
import static lx.lindx.bash.util.Util.getLogger;

import lx.lindx.bash.sys.Enviroment;

public class List {

  private final String nameCommand = "ls";
  private final String sptr = Enviroment.FILE_SEPARATOR;

  private int columns = getColumns();
  private int maxFileNameLength;

  public void show(final String path) {

    Path[] paths = getDirectories(path);

    int files = paths.length;
    int cols = columns / maxFileNameLength;
    int rows = (files / cols) + 1;

    for (int i = 0; i < rows; i++) {
      for (int j = i; j < paths.length; j = j + rows) {
        System.out.printf(("%-" + (maxFileNameLength)).concat("s "), paths[j] + "/");
      }
      System.out.println();
    }

    getLogger().info("Columns: " + columns + ",\t" +
        "FileLength: " + maxFileNameLength + ",\t" +
        "Cols: " + cols + "/ Rows: " + rows);
  }

  private Path[] getDirectories(final String path) {

    Path dirSrc = Paths.get(path);
    Set<Path> dirsFirst = new TreeSet<>();
    Set<Path> dirsLast = new TreeSet<>();

    if (Files.exists(dirSrc) && Files.isDirectory(dirSrc)) {
      try (DirectoryStream<Path> dir = Files.newDirectoryStream(dirSrc)) {
        for (Path p : dir) {

          String f = p.toString();

          maxFileNameLength = f.length() > maxFileNameLength ? f.length() : maxFileNameLength;

          String tmpStr = p.toString().replace(sptr, "");

          if (tmpStr.matches("^[a-zA-Z0-9]+$"))
            dirsFirst.add(Paths.get(tmpStr));
          else
            dirsLast.add(Paths.get(tmpStr));

        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      Path[] resultsDir = new Path[dirsFirst.size() + dirsLast.size()];
      dirsFirst.toArray(resultsDir);
      System.arraycopy(dirsLast.toArray(), 0, resultsDir, (resultsDir.length - dirsLast.size()), dirsLast.size());

      return resultsDir;
    }
    return null;
  }
}
