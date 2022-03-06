package lx.lindx.bash.view;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lx.lindx.bash.com.ListDirectory;

public class FiltredDirs {
  private List<Path> listFiltredDirs;
  private ListDirectory ls;
  private String path;

  public FiltredDirs(final ListDirectory ls) {
    this.listFiltredDirs = new ArrayList<>();
    this.ls = ls;
  }

  public void setPath(String parentpath) {
    this.path = parentpath;
  }

  public void filterbBy(final String mask) {

    listFiltredDirs.clear();

    for (Path p : ls.getDirs(path, false)) {
      if (p.toString().startsWith(mask)) {
        listFiltredDirs.add(p);
      }
    }
  }

  public int size() {
    return listFiltredDirs.size();
  }

  public String get(int index) {
    if (index <= listFiltredDirs.size() && index >= 0) {
      return listFiltredDirs.get(index).toString();
    }
    throw new RuntimeException();
  }

  public List<Path> getDirs() {
    return this.listFiltredDirs;
  }

  public void clear() {
    listFiltredDirs.clear();
  }

  public boolean isStartsWith(int first, int seccond) {
    return this.size() > 1 && this.get(first).startsWith(this.get(seccond));
  }
}
