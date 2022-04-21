package lx.lindx.bash.api;

import lx.lindx.bash.parser.CommandExpression;
import lx.lindx.bash.sys.EnvironmentVariables;

public class ChangeDirectory extends ACommand {

  public ChangeDirectory() {
    commandName = "cd";
  }

  public String toUserHome() {
    return EnvironmentVariables.USER_HOME;
  }

  @Override
  public String getName() {
    return this.commandName;
  }

  @Override
  public CommandExpression make() {
    return null;
  }

  @Override
  public void setExpression(CommandExpression exp) {
    // TODO Auto-generated method stub
    
  }
}
