package lx.lindx.bash.api;

import lx.lindx.bash.parser.CommandExpression;

/**
 * CommandAPI
 */
public interface Api {

  public String getName();

  public void setAddstdOutToEndFile(boolean stdOutAddToEndFile);

  public void setExpression(CommandExpression exp);

  public void setStdOutFile(CommandExpression exp);

  public void setOptions(CommandExpression exp);

  public int indexOfSpase(final String str);

  public CommandExpression make();

}