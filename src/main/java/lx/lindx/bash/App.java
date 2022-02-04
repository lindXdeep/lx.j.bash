package lx.lindx.bash;

import lx.lindx.bash.core.Shell;

/**
 * App
 */
public class App {

  public static void main(String[] args) {

    new Thread(new Shell()).start();

  }
}