package lx.lindx.bash.api.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EscapeCharacter {

  private static char[] symbol = { ' ', '&', '|', '>', '<', '\\', '\'', '"' };
  private static List<Character> result = new ArrayList<>();
  private static char[] tmpPath;

  public static String check(final String subPath) {

    result.clear();

    for (char c : subPath.toCharArray()) {

      for (char s : symbol)
        if (c == s)
          result.add('\\');

      result.add(c);
    }

    return toResult(result);
  }

  public static String uncheck(final String path) {

    result.clear();
    tmpPath = path.toCharArray();

    for (int i = 0; i < tmpPath.length; i++) {

      for (char s : symbol)
        if (tmpPath[i] == '\\' && i < tmpPath.length - 1 && tmpPath[i++] == s)
          result.add(tmpPath[i]);

      result.add(tmpPath[i]);
    }
    return toResult(result);
  }

  private static String toResult(final List<Character> pathResult) {
    return pathResult
        .stream()
        .map(n -> String.valueOf(n))
        .collect(Collectors.joining());
  }
}