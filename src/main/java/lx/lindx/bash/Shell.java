package lx.lindx.bash;

import java.io.Console;
import java.io.IOException;

import lx.lindx.bash.core.KeyProcessor;
import lx.lindx.bash.keys.KeyEvent;
import lx.lindx.bash.keys.KeyListener;
import lx.lindx.bash.term.Terminal;
import lx.lindx.bash.util.Util;

public class Shell implements Runnable {

  private KeyListener keyListener;
  private Console console;
  private KeyEvent keyEvent;
  private KeyProcessor keyProcessor;

  public Shell() {

    this.console = System.console();
    this.keyEvent = new KeyEvent();
    this.keyListener = new KeyListener(console.reader(), keyEvent);
    this.keyProcessor = new KeyProcessor();
  }

  @Override
  public void run() {

    Terminal.rawMode();

    String key;

    try {

      while ((key = keyListener.nextKey()) != null) {

        keyProcessor.proccess(key);

      }

    } catch (IOException e) {
      Terminal.saneMode();
      Util.log().error(e.getMessage());
    }
  }
}
