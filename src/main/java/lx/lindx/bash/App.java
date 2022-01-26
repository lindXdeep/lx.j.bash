package lx.lindx.bash;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import lx.lindx.bash.com.List;
import lx.lindx.bash.sys.Enviroment;
import lx.lindx.bash.util.Util;

/**
 * App
 */
public class App {

  public static void main(String[] args) throws IOException {

 

    List ls = new List();
    ls.show("/");
  }
}