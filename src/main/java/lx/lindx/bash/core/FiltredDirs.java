package lx.lindx.bash.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lx.lindx.bash.api.ListDirectory;

public class FiltredDirs {

  private List<Path> listFiltredDirs;
  private String path;

  private ListDirectory ls;

  public FiltredDirs(ListDirectory ls) {
    this.listFiltredDirs = new ArrayList<>();
    this.ls = ls;
  }

  public int size() {
    return listFiltredDirs.size();
  }

  public String get(int index) {
    if (index >= 0 && index <= listFiltredDirs.size())
      return listFiltredDirs.get(index).toString();
    throw new RuntimeException();
  }

  public List<Path> getDirs() {
    return this.listFiltredDirs;
  }

  public void clear() {
    listFiltredDirs.clear();
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public void filterbBy(final String mask) {

    listFiltredDirs.clear();

    for (Path p : ls.getDirs(path, false)) {
      if (p.toString().startsWith(mask)) {
        listFiltredDirs.add(p);
      }
    }
  }
}
