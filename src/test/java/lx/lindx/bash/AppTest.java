package lx.lindx.bash;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * AppTest
 */
public class AppTest {

  @BeforeAll
  public static void start() {
    System.out.println("BeforeAll");
  }

  @Test
  public void test() {
    System.out.println("Test");
  }

  @AfterAll
  public static void end() {
    System.out.println("AfterAll");
  }

}