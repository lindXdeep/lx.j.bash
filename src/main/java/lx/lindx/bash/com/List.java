package lx.lindx.bash.com;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
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

    Set<Path> paths = getDirectories(path);

    for (Path path2 : paths) {
      System.out.println(path2);
    }

    getLogger().info("Columns: " + columns + ",\t" +
        "FileLength: " + maxFileNameLength + ",\t" +
        "Cols: " + cols + "/ Rows: " + rows);
  }

  private Set<Path> getDirectories(final String path) {

    Path dirSrc = Paths.get(path);
    Set<Path> dirs = new TreeSet<>(new ComparatorIgnoreSpecialChars());

    if (Files.exists(dirSrc) && Files.isDirectory(dirSrc)) {
      try (DirectoryStream<Path> dir = Files.newDirectoryStream(dirSrc)) {
        for (Path p : dir) {

          String f = p.toString();

          maxFileNameLength = f.length() > maxFileNameLength ? f.length() : maxFileNameLength;

      
          dirs.add(p);

        }
      } catch (IOException e) {
        e.printStackTrace();
      }

     
      Path[] resultsDir = new Path[dirs.size()];
      dirs.toArray(resultsDir);

      return dirs;
    }
    return null;
  }
}
