package lx.lindx.bash;

/**
 * App
 */
public class App {

  public static void main(String[] args) {

    new Thread(new Shell()).start();

  }
}