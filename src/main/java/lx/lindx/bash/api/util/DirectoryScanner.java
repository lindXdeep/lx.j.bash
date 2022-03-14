package lx.lindx.bash.api.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream.Filter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

public class DirectoryScanner {

  private Set<Path> resultElements;
  private Path sourcePath;

  private int itemNumCount;
  private int itemMinNameLength;

  public int getItemMinNameLength() {
    return this.itemMinNameLength + 1; // -> / + ' '
  }

  public int getItemNumCount() {
    return itemNumCount;
  }

  public Set<Path> readFolder(final String path, Filter<Path> filter, boolean absolutePath) {

    init(path);

    if (Files.exists(sourcePath) && Files.isDirectory(sourcePath)) {

      try (DirectoryStream<Path> elementStream = Files.newDirectoryStream(sourcePath, filter)) {

        addElements(elementStream.iterator(), absolutePath);

      } catch (IOException e) {
        Util.log(e.getMessage());
        Terminal.saneMode();
      }
    }
    return resultElements;
  }

  public DirectoryScanner readItems(Set<Path> filtredDirs, boolean absolutePath) {

    init(null);

    if (!filtredDirs.isEmpty()) {
      addElements(filtredDirs.iterator(), absolutePath);
    }

    return this;
  }

  private void init(final String path) {
    this.resultElements = new TreeSet<>(new ComparatorIgnoreSpecialChars());
    this.sourcePath = Paths.get(path != null ? path : "");
    this.itemMinNameLength = 0;
  }

  private void addElements(Iterator<Path> iterator, boolean absolutePath) {

    while (iterator.hasNext()) {

      Path p = iterator.next();

      setFileNameLength(absolutePath ? p.toString().length() : p.getFileName().toString().length());
      resultElements.add(absolutePath ? p : p.getFileName());
    }

    this.itemNumCount = resultElements.size();
  }

  private void setFileNameLength(int currFileLength) {
    itemMinNameLength = (currFileLength > itemMinNameLength ? currFileLength : itemMinNameLength);
  }
}
