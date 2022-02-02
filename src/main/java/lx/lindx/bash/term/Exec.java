package lx.lindx.bash.term;

import java.io.IOException;

public class Exec {

  private static String sh = "/bin/sh";
  private static String c = "-c";

  static void execute(final String cmd, final String args) throws IOException, InterruptedException {

    exec(new String[] {
        sh, c, String.format(cmd, args)
    });
  }

  private static void exec(final String[] command) throws IOException, InterruptedException {

    Runtime.getRuntime().exec(command).waitFor();
  }

  public static void execute(final String command) throws IOException, InterruptedException {

    new ProcessBuilder(command).inheritIO().start().waitFor();
  }
}
