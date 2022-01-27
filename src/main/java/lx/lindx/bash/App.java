package lx.lindx.bash;

import java.io.IOException;

import lx.lindx.bash.com.ListDir;

/**
 * App
 */
public class App {

  public static void main(String[] args) throws IOException {

    ListDir ls = new ListDir();
    ls.byCols("/");
  }
}