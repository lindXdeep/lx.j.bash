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

    console.setEdge(0);
    console.newLine();

    if (console.getRow() >= 25)
      System.out.println();

    StringBuilder sb = new StringBuilder();
    char[] s = new char[3];

    for (int i = 0; i < exp.length(); i++) {

      if (i <= 2) {
        sb.append(s[i] = exp.toCharArray()[i]);
      } else {
        s[0] = s[1];
        s[1] = s[2];
        sb.append(s[2] = exp.toCharArray()[i]);
      }

      for (char[] b : backslashEscapes) {
        if (exp.getOptions().contains("e") && s[0] == '\\' && s[1] == '\\' && s[2] == b[0]) {
          sb.delete(sb.length() - 3, sb.length());
          sb.append(b[1]);
        } else if (s[0] != '\\' && s[1] == '\\' && s[2] == b[0]) { // default
          sb.deleteCharAt(sb.length() - 2);
        }
      }

      if (s[0] != '\\' && s[1] != '\\' && (s[2] == '\'' || s[2] == '\"')) { // default
        sb.deleteCharAt(sb.length() - 1);
      }
    }

    if (!exp.getOptions().contains("n")) {
      exp.update(sb.append('\n').toString());
    }

    for (char c : sb.toString().toCharArray()) {

      console.print(c);

      if (c == '\n') {
        console.print("\r");
        console.shiftRow(1);
      }
    }

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
