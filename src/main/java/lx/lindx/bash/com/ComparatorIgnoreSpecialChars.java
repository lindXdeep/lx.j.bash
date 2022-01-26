package lx.lindx.bash.com;

import java.nio.file.Path;
import java.util.Comparator;

public class ComparatorIgnoreSpecialChars implements Comparator<Path> {

  private String mask = "[^A-Za-zА-Яа-я0-9]";

  @Override
  public int compare(Path o1, Path o2) {

    String l = o1.toString().replaceAll(mask, "");
    String r = o2.toString().replaceAll(mask, "");

    return l.compareTo(r);
  }
}
