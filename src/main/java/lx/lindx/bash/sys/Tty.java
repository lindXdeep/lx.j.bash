package lx.lindx.bash.sys;

import java.io.IOException;

public class Tty {

  private static String sh = "/bin/sh";
  private static String c = "-c";
  private static String cmd = "stty %s < /dev/tty";

  // "raw" mode (line editing bypassed and no enter key required)
  private static String raw = "raw";

  // "cooked" mode (line editing with enter key required.)
  private static String sane = "sane";

  /*
   * stty -echo, это отключит вывод на экран набираемых данных. Вызов после этого
   * программы cat продемонстрирует, что набираемые на клавиатуре данные больше не
   * выводятся на экран (то есть придётся набирать текст «вслепую»). Однако, после
   * нажатия клавиши Ввод (Enter), ядро передаст последнюю напечатанную строчку
   * программе cat, и она уже выведет её на экран.
   */
  private static String echo = "-echo";

  /*
   * stty -icanon - это отключит канонический режим. Если после этого попытаться,
   * например,
   * запустить программу cat, все сочетания клавиш, отвечающие за редактирование
   * текста, такие, как ^U или даже backspace, не будут работать. Кроме того, cat
   * будет получать (и, соответственно, выводить) данные не строчками, как раньше,
   * а отдельными символами.
   */
  private static String icanon = "-icanon min 1";

  public static void raw() {
    try {

      stty(raw);
      stty(echo);
      stty(icanon);

      

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void sane() {
    try {

      stty(sane);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void stty(final String args) throws IOException, InterruptedException {

    exec(new String[] {
        sh, c, String.format(cmd, args)
    });
  }

  private static void exec(final String[] command) throws IOException, InterruptedException {

    Runtime.getRuntime().exec(command).waitFor();
  }
}
