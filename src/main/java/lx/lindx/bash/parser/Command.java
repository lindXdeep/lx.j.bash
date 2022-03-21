package lx.lindx.bash.parser;

public class Command {

  private String command;

  private String operation;

  private boolean state;

  public Command(final String command) {
    this.command = command.trim();
  }

  public Command() {
    this.command = "";
    this.operation = "";
  }

  public Command setOperation(final String operation) {
    this.operation = operation.trim();
    return this;
  }

  public String getCommand() {
    return this.command;
  }

  public String getOperation() {
    return this.operation;
  }

  public boolean getState() {
    return this.state;
  }

  @Override
  public String toString() {
    return this.command;
  }
}
