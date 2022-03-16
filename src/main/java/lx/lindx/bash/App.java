package lx.lindx.bash;

import java.nio.file.Paths;

import lx.lindx.bash.api.ListDirectory;

/**
 * App
 */
public class App {

  public static void main(String[] args) {


  

    new Thread(new Shell()).start();

  }
}