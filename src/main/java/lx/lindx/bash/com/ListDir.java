package lx.lindx.bash.com;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import lx.lindx.bash.sys.EnvironmentVariables;
import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

public class ListDir {

  private Path[] paths;

  private int sysColumns;

  private int minFileNameLength;

  private final String nameCommand = "ls";
  private final String sptr = EnvironmentVariables.FILE_SEPARATOR;

  private class ComparatorIgnoreSpecialChars implements Comparator<Path> {

    private String mask = "[^A-Za-zА-Яа-я0-9]";

    @Override
    public int compare(Path o1, Path o2) {
      String l = o1.toString().replaceAll(mask, "");
      String r = o2.toString().replaceAll(mask, "");
      return l.compareTo(r);
    }
  }

  public ListDir() {
    sysColumns = Terminal.getColumns();
  }

  public void byRows(final String path) {
    print(readDir(path), 1);
  }

  public void byCols(final String path) {
    print(readDir(path), (sysColumns / minFileNameLength));
  }

  private void print(final Path[] dirs, int numCols) {

    int numFiles = dirs.length;
    int numRows = (numFiles / numCols);

    for (int i = 0; i < numRows; i++) {
      for (int j = i; j < numFiles; j += numRows) {
        System.out.printf(("%-" + (minFileNameLength)).concat("s "), dirs[j].toString().substring(1) + sptr);
      }
      System.out.println();
    }

    Util.log("Columns: " + sysColumns + ",\t" +
        "FileLength: " + minFileNameLength + ",\t" +
        "Cols: " + numCols + " / Rows: " + numRows);
  }

  private Path[] readDir(final String path) {

    Path dirSrc = Paths.get(path);
    Set<Path> dirs = new TreeSet<>(new ComparatorIgnoreSpecialChars());

    if (Files.exists(dirSrc) && Files.isDirectory(dirSrc)) {
      try (DirectoryStream<Path> dir = Files.newDirectoryStream(dirSrc)) {
        for (Path p : dir) {
          int currFileLength = p.toString().length();
          minFileNameLength = currFileLength > minFileNameLength ? currFileLength : minFileNameLength;
          dirs.add(p);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return dirs.toArray(new Path[dirs.size()]);
  }
}