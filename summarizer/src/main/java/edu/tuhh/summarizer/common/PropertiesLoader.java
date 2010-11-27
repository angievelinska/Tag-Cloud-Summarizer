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
  private static final String PROP_FILE = "summarizer/src/main/resources/summarizer.properties";

  public static Properties loadProperties() {
    if (props != null && !props.isEmpty()) {
      log.info("Properties file was already loaded.");
      return props;

    } else {
      props = new Properties();
      try {
        FileInputStream fin = new FileInputStream(PROP_FILE);
        props.load(fin);
        fin.close();
        log.info("Properties file is now loaded.");
      } catch (IOException e) {
        log.error("Error loading properties file: ");
        e.printStackTrace();
      }
      return props;
    }
  }
}
