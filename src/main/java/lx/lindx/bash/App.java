package lx.lindx.bash;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

import lx.lindx.bash.sys.Tty;

public class App {

  private static Console console;
  private static Reader reader;

  public static void main(String[] args) throws InterruptedException, IOException {

    System.out.println("starrt");

    Shell sh = new Shell();
   
    Thread th = new Thread(sh);
    
    th.start();
    
  

   System.out.println("stop");

  


   
    // Tty.raw();

    // console = System.console();
    // reader = console.reader();

    // int ch;

    // while (true) {

    //   ch = reader.read();

    //   if (String.valueOf((char) ch).matches("[a-zA-Z0-9а-яА-я]")) {
    //     System.out.print((char) ch);
    //   }

    //   if ((int) 'q' == ch) {
    //     Tty.sane();
    //     System.exit(0);
    //   } else if ((int) 'w' == ch) {
    //     System.out.print("orked!");
    //   } else if (ch == 13) {
    //     System.out.print("\n\rEnter");
    //   } /*
    //      * else if ((int) ch == 127) {
    //      * System.out.print("Back Space");
    //      * }
    //      */ else if (9 == (int) ch) {
    //     System.out.println("\n\rsome else \r");
    //     System.out.println("some else \r");
    //     System.out.println("some else \r");
    //     System.out.print("\rlast result");
    //   } /*
    //      * else if (27 == (int) ch) {
    //      * System.out.println((char) ch);
    //      * }
    //      */ else if (127 == ch) {
    //     System.out.print('\b' + "\033[K");
    //   } else if ('f' == (char) ch) {
    //     for (char i = 'а'; i < 'я'; i++) {
    //       System.out.print(i + " ");
    //     }
    //     for (char i = 'А'; i < 'Я'; i++) {
    //       System.out.print(i + " ");
    //     }
    //     for (char i = '!'; i < '?'; i++) {
    //       System.out.print(i + " ");
    //     }
    //   }
    // }
  }
}