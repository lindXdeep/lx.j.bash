package lx.lindx.bash.com;

public interface Command {
  
  public int getMaxLengthElement();

  public int getMinLengthElement();

  public void byCols(String string);

  public int getNumRows();
}
