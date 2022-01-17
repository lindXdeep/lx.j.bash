package lx.lindx.bash;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

/**
 * App
 */
public class App {

  private static Console console;
  private static Reader reader;

  public static void main(String[] args) throws InterruptedException, IOException {

    String[] raw = { "/bin/sh", "-c", "stty raw < /dev/tty" };
    String[] sane = { "/bin/sh", "-c", "stty sane < /dev/tty" };

    Runtime.getRuntime().exec(raw).waitFor();

    console = System.console();
    reader = console.reader();

    char ch;

    while (true) {

      ch = (char) reader.read();

      if ('q' == ch) {
        Runtime.getRuntime().exec(sane).waitFor();
        System.exit(0);
      } else if ('w' == ch) {
        System.out.print("orked!");
      } else if ('\t' == ch) {
        System.out.println("\n\rsome else \r");
        System.out.println("some else \r");
        System.out.println("some else \r");
        System.out.print("\rlast result");
      }
    }
  }
}