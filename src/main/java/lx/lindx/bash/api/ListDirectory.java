package lx.lindx.bash.api;

import java.nio.file.Path;
import java.util.Set;

import lx.lindx.bash.api.util.DirectoryScanner;
import lx.lindx.bash.api.util.ItemFilter;
import lx.lindx.bash.api.util.ItemFilter.Type;
import lx.lindx.bash.sys.EnvironmentVariables;
import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

public class ListDirectory {

  private final String sptr = EnvironmentVariables.FILE_SEPARATOR;

  private DirectoryScanner directoryScanner;

  private int sysColumns;

  private int itemCount;

  private int itemRows;

  private int itemCols;

  public ListDirectory() {
    this.sysColumns = Terminal.getColumns();
    this.directoryScanner = new DirectoryScanner();
  }

  public Set<Path> getDirs(final String path, boolean absolutePath) {
    return directoryScanner.readFolder(path, ItemFilter.get(Type.FOLDERS), absolutePath);
  }

  public Set<Path> getFiles(final String path, boolean absolutePath) {
    return directoryScanner.readFolder(path, ItemFilter.get(Type.FILES), absolutePath);
  }

  public Set<Path> getAll(final String path, boolean absolutePath) {
    return directoryScanner.readFolder(path, ItemFilter.get(Type.ALL), absolutePath);
  }

  public void printDirsByCols(final String path) {
    print(getDirs(path, false), directoryScanner.getItemMinNameLength());
  }

  public void printDirsByCols(Set<Path> filtredDirs) {
    print(filtredDirs, directoryScanner.readItems(filtredDirs, false).getItemMinNameLength());
  }

  private void print(final Set<Path> dirs, int itemMinNameLength) {

    this.itemCount = dirs.size();
    this.itemCols = (sysColumns / directoryScanner.getItemMinNameLength()) - 1;

    int rows = itemCount / itemCols;

    this.itemRows = rows == 0 ? 1 : (rows == 1 && itemCount > itemCols) ? rows + 1 : rows;

    System.out.println();

    for (int i = 0; i < itemRows; i++) {
      for (int j = i; j < itemCount; j += itemRows) {
        System.out.printf(("%-" + (itemMinNameLength + 1)).concat("s "),
            dirs.toArray(new Path[itemCount])[j].toString() + sptr);
      }
      System.out.println();
    }

    Util.log("Columns: " + sysColumns + ", " +
        "NumCount: " + itemCount + ", " +
        "FileLength: " + itemMinNameLength + ", " +
        "Cols: " + itemCols + " / Rows: " + itemRows);
  }

  public int getNumRows() {
    return this.itemRows;
  }
}
