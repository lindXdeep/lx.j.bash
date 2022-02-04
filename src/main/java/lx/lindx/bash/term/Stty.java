package lx.lindx.bash.term;

import java.io.IOException;

public class Stty {

  private final String STTY = "stty %s < /dev/tty";

  /**
   * "raw" mode (line editing bypassed and no enter key required)
   */
  private final String raw = "raw";

  /**
   * "cooked" mode (line editing with enter key required.)
   */
  private final String sane = "sane";

  /*
   * stty -echo, это отключит вывод на экран набираемых данных. Вызов после этого
   * программы cat продемонстрирует, что набираемые на клавиатуре данные больше не
   * выводятся на экран (то есть придётся набирать текст «вслепую»). Однако, после
   * нажатия клавиши Ввод (Enter), ядро передаст последнюю напечатанную строчку
   * программе cat, и она уже выведет её на экран.
   */
  private final String echo = "-echo";

  /*
   * stty -icanon - это отключит канонический режим. Если после этого попытаться,
   * например,
   * запустить программу cat, все сочетания клавиш, отвечающие за редактирование
   * текста, такие, как ^U или даже backspace, не будут работать. Кроме того, cat
   * будет получать (и, соответственно, выводить) данные не строчками, как раньше,
   * а отдельными символами.
   */
  private final String icanon = "-icanon min 1";

  Stty() {
  }

  public void raw() throws InterruptedException, IOException {
    Exec.execute(STTY, raw);
    Exec.execute(STTY, echo);
    Exec.execute(STTY, icanon);
  }

  public void sane() throws InterruptedException, IOException {
    Exec.execute(STTY, sane);
  }
}
