package lx.lindx.bash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * App
 */
public class App {

  private static String propPathFile = "main.properties";
  private static Properties properties;
  private static OutputStream out;
  private static InputStream in;

  private static final Logger LOGGER;

  static {
    properties = new Properties();
    LOGGER = LogManager.getLogger(App.class.getName());

    try {
      out = new FileOutputStream(new File("src/main/resources", propPathFile));
      in = App.class.getClassLoader().getResourceAsStream(propPathFile);

      if (out == null || in == null) {
        System.out.println("properties file not found");
        System.exit(1);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {

    properties.setProperty("java.vm.name", System.getProperty("java.vm.name"));
    properties.setProperty("java.home", System.getProperty("java.home"));
    properties.setProperty("os.version", System.getProperty("os.version"));
    properties.setProperty("os.name", System.getProperty("os.name"));
    properties.setProperty("package", App.class.getCanonicalName());
    properties.store(out, "");

    System.out.println(properties.getProperty("java.vm.name"));
    System.out.println(properties.getProperty("java.home"));
    System.out.println(properties.getProperty("os.version"));
    System.out.println(properties.getProperty("os.name"));
    System.out.println(properties.getProperty("package"));

    LOGGER.debug("test log debug message", App.class);
    LOGGER.info("test log info message", App.class);
    LOGGER.error("test log error message", App.class);
  }
}