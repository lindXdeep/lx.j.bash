package lx.lindx.bash;

import java.io.IOException;

import lx.lindx.bash.sys.EscSeq;
import lx.lindx.bash.sys.KeyEvent;

public class App2 {

  private static String username = "lindx@lindx$";

  private static int strEdge = username.length();
  private static int lineLength = strEdge;

  private static String key;
  private static KeyEvent keys = new KeyEvent();
  private static StringBuilder buffer;

  static {
    buffer = new StringBuilder(256);
  }

  public static void main(String[] args) throws InterruptedException, IOException {

    print("lindx@lindx$");

    while ((key = keys.listenKey()) != null) {

      if (key.length() == 1) {

        print(key);
        buffer.append(key);
        lineLength++;

      } else {
        if (key.equals(EscSeq.KEY_TAB.name())) {
          System.out.print(EscSeq.KEY_TAB);
        } else if (key.equals(EscSeq.KEY_ENTER.name())) {
          System.out.print(EscSeq.KEY_ENTER);
        } else if (key.equals(EscSeq.KEY_BACKSPACE.name())) {

          if (lineLength > strEdge) {
            buffer.deleteCharAt(buffer.length() - 1);
            print("\b\033[K");
            lineLength--;
          }

        } else if (key.equals(EscSeq.KEY_F1.name())) {
          System.out.print(EscSeq.KEY_F1);
        } else if (key.equals(EscSeq.KEY_F2.name())) {
          System.out.print(EscSeq.KEY_F2);
        } else if (key.equals(EscSeq.KEY_F3.name())) {
          System.out.print(EscSeq.KEY_F3);
        } else if (key.equals(EscSeq.KEY_F4.name())) {
          System.out.print(EscSeq.KEY_F4);
        } else if (key.equals(EscSeq.KEY_F5.name())) {
          System.out.print(EscSeq.KEY_F5);
        } else if (key.equals(EscSeq.KEY_F6.name())) {
          System.out.print(EscSeq.KEY_F6);
        } else if (key.equals(EscSeq.KEY_F7.name())) {
          System.out.print(EscSeq.KEY_F7);
        } else if (key.equals(EscSeq.KEY_F8.name())) {
          System.out.print(EscSeq.KEY_F8);
        } else if (key.equals(EscSeq.KEY_F9.name())) {
          System.out.print(EscSeq.KEY_F9);
        } else if (key.equals(EscSeq.KEY_INSERT.name())) {
          System.out.print(EscSeq.KEY_INSERT);
        } else if (key.equals(EscSeq.KEY_DELETE.name())) {
          System.out.print(EscSeq.KEY_DELETE);
        } else if (key.equals(EscSeq.KEY_UP.name())) {
          System.out.print(EscSeq.KEY_UP);
        } else if (key.equals(EscSeq.KEY_DOWN.name())) {
          System.out.println("--------------");
          System.out.println(buffer);
          System.out.println("--------------");
        } else if (key.equals(EscSeq.KEY_RIGHT.name())) {
          System.out.print(EscSeq.KEY_RIGHT);
        } else if (key.equals(EscSeq.KEY_LEFT.name())) {
          print("\b");
        }
      }
    }
  }

  private static void print(final String str) {
    System.out.print(str);
  }
}