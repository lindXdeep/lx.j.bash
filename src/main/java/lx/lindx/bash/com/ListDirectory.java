package lx.lindx.bash.com;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lx.lindx.bash.sys.EnvironmentVariables;
import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

public class ListDirectory {

  private Path[] paths;

  private int sysColumns;

  private int minFileNameLength;
  private int numFiles;
  private int numRows;

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

  public ListDirectory() {
    sysColumns = Terminal.getColumns();
  }

  // public void byRows(final String path) {
  // print(readDir(path), 1);
  // }

  public void byCols(final String path) {
    print(getDirs(path, false), (sysColumns / minFileNameLength));
  }

  public void byCols(List<Path> dirs) {

    Path[] elems = new Path[dirs.size()];

    int i = 0;

    System.out.println();

    for (Path path : dirs) {
      elems[i++] = path;
      int currFileLength = path.toString().length();
      minFileNameLength = currFileLength > minFileNameLength ? currFileLength : minFileNameLength;
    }

    print(elems, (sysColumns / minFileNameLength));
  }

  private void print(final Path[] dirs, int numCols) {

    numFiles = dirs.length;
    numRows = (numFiles / numCols) == 0 ? 1 : (numFiles / numCols);

    for (int i = 0; i < numRows; i++) {
      for (int j = i; j < numFiles; j += numRows) {
        System.out.printf(("%-" + (minFileNameLength - 2)).concat("s "), dirs[j].toString() + sptr);
      }
      System.out.println();
    }

    Util.log("Columns: " + sysColumns + ", " +
        "FileLength: " + minFileNameLength + ", " +
        "Cols: " + numCols + " / Rows: " + numRows);
  }

  public Path[] getDirs(final String path, boolean absolutePath) {
    DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
      @Override
      public boolean accept(Path entry) throws IOException {
        return Files.isDirectory(entry);
      }
    };

    return readFolder(path, filter, absolutePath);
  }

  public Path[] getFiles(final String path, boolean absolutePath) {
    DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
      @Override
      public boolean accept(Path entry) throws IOException {
        return !Files.isDirectory(entry);
      }
    };

    return readFolder(path, filter, absolutePath);
  }

  public Path[] getAll(final String path, boolean absolutePath) {
    DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
      @Override
      public boolean accept(Path entry) throws IOException {
        return Files.exists(entry);
      }
    };

    return readFolder(path, filter, absolutePath);
  }

  private Path[] readFolder(final String path, DirectoryStream.Filter<Path> filter, boolean absolutePath) {

    Path dirSrc = Paths.get(path);

    Set<Path> paths = new TreeSet<>(new ComparatorIgnoreSpecialChars());

    if (Files.exists(dirSrc) && Files.isDirectory(dirSrc)) {

      try (DirectoryStream<Path> dir = Files.newDirectoryStream(dirSrc, filter)) {

        for (Path p : dir) {
          int currFileLength = p.toString().length();
          minFileNameLength = currFileLength > minFileNameLength ? currFileLength : minFileNameLength;
          paths.add(absolutePath ? p : p.getFileName());
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return paths.toArray(new Path[paths.size()]);
  }

  // @Override
  // public int getMaxLengthElement() {
  // return this.minFileNameLength;
  // }

  // @Override
  // public int getMinLengthElement() {
  // return this.minFileNameLength;
  // }

  public int getNumRows() {
    return this.numRows;
  }
}