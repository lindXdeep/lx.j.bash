package lx.lindx.bash.parser;

import lx.lindx.bash.view.Buffer;

public class CommandParser {

  private final char commandDelimiter[] = { '&', '|' };

  private StringBuilder commandLine;

  private boolean nextCommand;

  public void setCommandLine(final StringBuilder buffer) {
    this.commandLine = buffer;
    nextCommand = buffer.length() > 0 ? true : false;
  }

  public void setCommandLine(final Buffer buffer) {
    this.commandLine = new StringBuilder(buffer.toString());
    nextCommand = buffer.getSize() > 0 ? true : false;
  }

  public boolean hasNextCommand() {
    return this.nextCommand;
  }

  public CommandExpression nextCommand() {

    CommandExpression command = new CommandExpression();

    
    

    return command;
  }
}
