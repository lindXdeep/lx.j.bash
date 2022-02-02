package lx.lindx.bash;

import lx.lindx.bash.api.Shell;

/**
 * App
 */
public class App {

  public static void main(String[] args) {

    Shell shell = new Shell();

    new Thread(shell).start();
  }
}