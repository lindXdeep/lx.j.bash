package lx.lindx.bash.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import lx.lindx.bash.core.Ps1;
import lx.lindx.bash.parser.CommandExpression;
import lx.lindx.bash.util.EscapeCharacter;
import lx.lindx.bash.view.Console;

abstract class ACommand implements Api {

  protected String commandName;
  protected String[] options;

  protected enum BackslashEscapes {

    
    b('\b'),
    f('\f'),
    t('\t'),
    n('\n'),
    r('\r');

    private final Character ch;

    BackslashEscapes(final Character ch) {
      this.ch = ch;
    }

    public Character getEscape() {
      return this.ch;
    }
  }

  protected boolean stdOutAddToEndFile;

  protected CommandExpression exp;

  protected Console console;

  protected Ps1 ps1;

  @Override
  public String getName() {
    return this.commandName;
  }

  @Override
  public void setAddstdOutToEndFile(boolean stdOutAddToEndFile) {
    this.stdOutAddToEndFile = stdOutAddToEndFile;
  }

  @Override
  public String toString() {
    return this.commandName;
  }

  @Override
  public void setExpression(CommandExpression exp) {
    this.exp = exp;
  }

  public void setStdOutFile(CommandExpression exp) {

    String str_exp = exp.getCommand();

    if (str_exp.split(" > ").length > 1 || str_exp.split(" >> ").length > 1) {

    } else {
      // если нет вывода в файли тогда на экран
    }

    // echo asdasd >> asd -d sad > asaS ; > ASD > sdasd -d;

  }

  public boolean stdOutToFile(final String argument, final String fileName) {

    if (argument.length() == 0 || fileName.length() == 0)
      return false;

    System.out.println("data: " + argument);
    System.out.println("filename: " + fileName);

    // System.out.println(argument);

    // System.out.println("::" + argument + " | " + fileName);

    // System.out.println(stdOutAddToEndFile);

    // Path outFile = Paths.get(EscapeCharacter.uncheck(fileName));

    // System.out.println(outFile);

    // if (Files.exists(outFile)) {

    // }

    // Path p = Paths.get(getPs1().getWorkingDirectory(), fileName);

    // System.out.println(":: >>" + outFile);

    // try {
    // Files.createFile(p);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }

    return false;
  }

  @Override
  public int indexOfSpase(final String str) {
    int i = 0;
    while (i++ < str.length() - 1)
      if (str.charAt(i) == ' ' && str.charAt(i - 1) != '\\')
        break;
    return i;
  }

  @Override
  public void setOptions(CommandExpression exp) {

    String str = exp.getCommand();

    int i = -1;
    while ((i = str.indexOf(" -")) != -1) {
      str = str.substring(i += " -".length());
      int s = str.indexOf(' ') == -1 ? str.length() : str.indexOf(' ');
      exp.addOption(str.substring(0, s));
      str = str.substring(s);
    }
  }

  public boolean checkContainOptions(final String str_opt) {

    String opt = Arrays.toString(options);

    for (char s : str_opt.toCharArray()) {
      if (!opt.contains(String.valueOf(s)))
        return false;
    }
    return true;
  }
}
