package lx.lindx.bash.core;

import lx.lindx.bash.api.Api;
import lx.lindx.bash.api.ChangeDirectory;
import lx.lindx.bash.api.Echo;
import lx.lindx.bash.api.ListDirectory;
import lx.lindx.bash.parser.CommandExpression;
import lx.lindx.bash.view.Console;

public class Executor {

  private Console console;
  private Api commandAPI[];
  private Ps1 ps1;

  public Executor(final Console console, Ps1 ps1) {
    this.console = console;
    this.ps1 = ps1;

    this.commandAPI = new Api[] {
        new Echo(console, ps1),
        new ChangeDirectory(),
        new ListDirectory()
    };
  }

  public CommandExpression make(CommandExpression expression) {

    for (Api comApi : commandAPI) {

      if (expression.isEquals(comApi)) {

        String exp = expression.getCommand();
        expression.update(exp.substring(exp.indexOf(' ')));

        comApi.setExpression(expression);

        return comApi.make();
      }
    }
    return expression;
  }
}
