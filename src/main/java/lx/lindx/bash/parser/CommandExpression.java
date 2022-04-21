package lx.lindx.bash.parser;

import java.util.Iterator;
import java.util.NoSuchElementException;

import lx.lindx.bash.api.Api;

public class CommandExpression implements Iterable<Character> {

  private String command;

  private String operation;

  private StringBuffer options;

  private String stdOut;

  private boolean state;

  public CommandExpression(final String command) {
    this.command = command.trim();
    this.options = new StringBuffer();
    this.stdOut = "";
  }

  public CommandExpression() {
    this.command = "";
    this.operation = "";
    this.options = new StringBuffer();
    this.stdOut = "";
  }

  public void setStdOutFileName(String stdOut) {
    this.stdOut = stdOut;
  }

  public CommandExpression setLogicOperator(final String operation) {
    this.operation = operation;
    return this;
  }

  public String getCommand() {
    return this.command;
  }

  public void addOption(String option) {
    this.options.append(option.trim());
  }

  public String getOptions() {
    return options.toString();
  }

  public String getOperation() {
    return this.operation;
  }

  @Override
  public String toString() {
    return this.command;
  }

  public String getStdOutFileName() {
    return stdOut;
  }

  public boolean getState() {
    return this.state;
  }

  public String update(String command) {
    return this.command = command.trim();
  }

  public void setState(boolean state) {
    this.state = state;
  }

  public boolean isEquals(final Api api) {
    return command.startsWith(api.getName().concat(" "));
  }

  @Override
  public Iterator<Character> iterator() {

    return new Iterator<Character>() {

      int idx = 0;

      @Override
      public boolean hasNext() {
        return idx < command.length();
      }

      @Override
      public Character next() {

        if (hasNext())
          return command.charAt(idx++);

        throw new NoSuchElementException();
      }
    };
  }

  public char[] toCharArray() {
    return getCommand().toCharArray();
  }

  public int length() {
    return command.length();
  }
}
