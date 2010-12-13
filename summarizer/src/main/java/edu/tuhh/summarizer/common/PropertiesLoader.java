package edu.tuhh.summarizer.common;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class PropertiesLoader {
  private static Properties props;
  private final static Logger log = Logger.getLogger(PropertiesLoader.class);
  private static final String PROP_FILE = "C:/tmp/summarizer.properties";

  public Properties loadProperties() {
    if (props != null && !props.isEmpty()) {
      log.info("Properties file was loaded before.");
      return props;

    } else {
      props = new Properties();
      FileInputStream fin = null;
      try {

        fin = new FileInputStream(PROP_FILE);
        props.load(fin);

      } catch (IOException e) {
        log.error("Error loading properties file: ");
        e.printStackTrace();
      } finally {
        try {
          if (fin != null) {
            fin.close();
          }
        } catch (IOException e) {
          log.error("Error closing FileInputStream: ");
          e.printStackTrace();
        }
      }
      return props;
    }
  }
}
