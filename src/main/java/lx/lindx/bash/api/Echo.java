package lx.lindx.bash.api;

import java.util.Arrays;

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

    // console.newLineAndReturnСarriage();

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

      for (BackslashEscapes b : BackslashEscapes.values()) {

        if (exp.getOptions().contains("e") && s[0] == '\\' && s[1] == '\\' && String.valueOf(s[2]).equals(b.name())) {
          sb.delete(sb.length() - 3, sb.length());
          sb.append(b.getEscape());
        }
      }
    }

    char[] sd = sb.toString().toCharArray();

    for (char c : sd) {
      console.print(c);

      if (c == '\n') {
        console.print("\r");
        console.shiftRow(1);
      }
    }

    // System.out.println("1:" + exp.getOptions());
    // System.out.println("2:" + exp.getCommand());

    // echo asdsa . asd > sadf >> asdads

    // -sdf sdfsdf > sdf > sdf > sdf -asd sdfdsf

    // sdf

    // setStdOutFile(this.exp);
    // setOptions(this.exp);

    // String opt = exp.getOptions();
    // String arg = exp.getCommand().substring(commandName.length()).trim();

    // int o = 0;
    // char ch = 0;

    // if (opt.isEmpty()) {
    // console.newLineAndReturnСarriage();
    // }

    // while (opt.length() >= 1 && o++ < opt.length() - 1 && (ch = opt.charAt(o++))
    // != -1) {

    // if (ch == 'n') {
    // console.shiftRow(-1);
    // console.next();
    // } else if (ch == 'e') {
    // // TODO: enable interpretation of backslash escapes
    // } else {
    // // nothing
    // }
    // }

    // if (exp.getStdOutFileName().length() != 0) {
    // stdOutToFile(arg, exp.getStdOutFileName());
    // } else {
    // console.print(arg);
    // }

    // exp.setState(true);

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

  // @Override
  // public void setOptions(CommandExpression exp) {

  // String s2 = "";
  // while ((s2 =
  // exp.getCommand().substring(commandName.length()).trim()).startsWith("-")) {

  // String sOpt = s2.substring(1, s2.indexOf(' ') != -1 ? s2.indexOf(' ') :
  // s2.length());

  // for (String o : options)
  // if (sOpt.contains(o))
  // exp.addOption(o);

  // exp.update(commandName.concat(" ").concat(s2.substring(sOpt.length() +
  // 1).trim()));
  // }
  // }

}
