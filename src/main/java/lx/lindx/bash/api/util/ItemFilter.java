package lx.lindx.bash.api.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemFilter {

  public enum Type {
    FOLDERS,
    FILES,
    ALL
  }

  public static DirectoryStream.Filter<Path> get(final Type typeFilter) {

    DirectoryStream.Filter<Path> type = null;

    switch (typeFilter) {

      case FOLDERS:
        type = new DirectoryStream.Filter<Path>() {
          @Override
          public boolean accept(Path entry) throws IOException {
            return Files.isDirectory(entry);
          }
        };

        break;

      case FILES:
        type = new DirectoryStream.Filter<Path>() {
          @Override
          public boolean accept(Path entry) throws IOException {
            return !Files.isDirectory(entry);
          }
        };

        break;

      case ALL:
        type = new DirectoryStream.Filter<Path>() {
          @Override
          public boolean accept(Path entry) throws IOException {
            return Files.exists(entry);
          }
        };

        break;

      default:
        break;
    }

    return type;
  }
}
