package lx.lindx.bash.parser;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CommandLine implements ListIterator<Character>, Iterable<Character> {

  private int size;
  private int idx;
  private char[] line;

  CommandLine(final String line) {
    setLine(line);
  }

  public void setLine(final String line) {
    this.line = line.toCharArray();
    this.size = line.length() - 1;
  }

  @Override
  public boolean hasNext() {
    return hasNexChar();
  }

  @Override
  public Character next() {
    return nextChar();
  }

  @Override
  public boolean hasPrevious() {
    return idx > 0;
  }

  @Override
  public Character previous() {

    if (hasPrevious())
      return line[--idx];

    throw new NoSuchElementException();
  }

  @Override
  public int nextIndex() {
    return hasNext() ? idx + 1 : size;
  }

  @Override
  public int previousIndex() {
    return hasPrevious() ? idx - 1 : 1;
  }

  @Override
  public void remove() {
    if (idx >= 0 && idx < size)
      System.arraycopy(line, idx + 1, line, idx, size-- - idx);
    else
      idx = size--;
  }

  @Override
  public void set(Character e) {
    insert(idx, e);
  }

  @Override
  public void add(Character e) {
    insert(nextIndex(), e);
  }

  @Override
  public Iterator<Character> iterator() {

    idx = 0;

    return new Iterator<Character>() {

      @Override
      public boolean hasNext() {
        return hasNexChar();
      }

      @Override
      public Character next() {
        return nextChar();
      }
    };
  }

  public Iterable<Character> reverse() {
    return new Iterable<Character>() {

      @Override
      public Iterator<Character> iterator() {
        return new Iterator<Character>() {

          @Override
          public boolean hasNext() {
            return hasPrevious();
          }

          @Override
          public Character next() {
            return previous();
          }
        };
      }
    };
  }

  public Iterable<Character> continuous() {
    return new Iterable<Character>() {

      @Override
      public Iterator<Character> iterator() {
        return new Iterator<Character>() {

          @Override
          public boolean hasNext() {
            return hasNexChar();
          }

          @Override
          public Character next() {
            return nextChar();
          }
        };
      }
    };
  }

  private boolean hasNexChar() {
    return idx <= size;
  }

  private Character nextChar() {

    if (hasNext())
      return line[idx++];

    throw new NoSuchElementException();
  }

  private void insert(final int posIns, Character e) {

    char[] tmp = new char[size + 2];
    tmp[posIns] = e;
    size = tmp.length - 1;

    System.arraycopy(line, 0, tmp, 0, posIns);
    System.arraycopy(line, posIns, tmp, posIns + 1, size - posIns);

    line = tmp;
  }
}
