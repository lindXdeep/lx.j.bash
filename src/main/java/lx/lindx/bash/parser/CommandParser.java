package lx.lindx.bash.parser;

import lx.lindx.bash.view.Buffer;

public class CommandParser {

  private final String commandDelimiter[] = { " && ", " || " };

  private String commandLine;

  private boolean nextCommand;

  public void setCommandLine(final StringBuffer buffer) {
    this.commandLine = buffer.toString();
    nextCommand = buffer.length() > 0 ? true : false;
  }

  public void setCommandLine(final Buffer buffer) {
    this.commandLine = buffer.toString();
    nextCommand = buffer.getSize() > 0 ? true : false;
  }

  public boolean hasNextCommand() {
    return this.nextCommand;
  }

  public CommandExpression nextCommand() {

    CommandExpression command = new CommandExpression();

    for (String d : commandDelimiter) {

      String dt = d.substring(0, d.length() - 1);
      int idx = commandLine.indexOf(d);
      int lineLength = commandLine.length();

      if (idx != -1) {

        command = new CommandExpression(commandLine.substring(0, idx))
            .setLogicOperator(d);

        commandLine = commandLine.substring(idx + d.length());
        break;

      } else if (idx == -1 && lineLength > 0
          && (commandLine.endsWith(dt) || commandLine.endsWith(d))) {

        command = new CommandExpression(commandLine = commandLine.substring(0, (lineLength - dt.length())))
            .setLogicOperator(d);

        this.nextCommand = false;
        break;

      } else if (idx == -1 && lineLength > 0 && !commandLine.endsWith(dt)) {

        command = new CommandExpression(commandLine)
            .setLogicOperator("");

        this.nextCommand = false;
        break;
      }
    }
    return command;
  }
}
