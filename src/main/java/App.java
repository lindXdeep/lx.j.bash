import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * App
 */
public class App {

  private static String propFilename = "main.properties";
  private static File outPropFilePath;

  private static Properties properties;

  static {
    outPropFilePath = new File("src/main/resources", propFilename);
    properties = new Properties();
  }

  public static void main(String[] args) {
    System.out.println("App worked");

    try (OutputStream out = new FileOutputStream(outPropFilePath)) {

      properties.setProperty("one.prop", "one properties from file");
      properties.setProperty("two.prop", "one properties from file");
      properties.store(out, "");
      properties = null;

    } catch (IOException e) {
      e.printStackTrace();
    }

    properties = new Properties();

    try (InputStream in = App.class.getClassLoader().getResourceAsStream(propFilename)) {

      if (in == null) return;

      properties.load(in);

      System.out.println(properties.getProperty("one.prop"));
      System.out.println(properties.getProperty("two.prop"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}