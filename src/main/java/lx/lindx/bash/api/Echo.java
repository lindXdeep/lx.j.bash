package lx.lindx.bash.api;

import lx.lindx.bash.core.Ps1;
import lx.lindx.bash.parser.CommandExpression;
import lx.lindx.bash.view.Console;

public class Echo extends ACommand {

  public Echo(Console console, Ps1 ps1) {
    this.commandName = "echo";
    this.options = new String[] { "n", "e" };

    this.console = console;
    this.ps1 = ps1;
  }

  @Override
  public CommandExpression make() {

    setOptions(exp);
    setStdOutFile(exp);

    console.setEdge(0);
    console.newLine();

    if (console.getRow() >= 25)
      System.out.println();

    if (exp.length() >= 2) {

      for (char c : exp) {

        console.print(c);

      }

    } else {
      console.print(exp);
    }
    exp.setState(true);
    return this.exp;
  }

  @Override
  public void setOptions(CommandExpression exp) {

    String str_opt = exp.getCommand().trim();

    if (str_opt.length() > 0 && str_opt.matches("^-[a-zA-Z]*\\s.+")) {
      int s = str_opt.indexOf(" ");

      if (checkContainOptions(str_opt.substring(1, s))) {
        exp.addOption(str_opt.substring(0, s));
        exp.update(str_opt.substring(s));
      }
    }
  }
}
