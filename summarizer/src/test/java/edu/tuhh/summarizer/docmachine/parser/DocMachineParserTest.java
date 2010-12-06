package edu.tuhh.summarizer.docmachine.parser;

import edu.tuhh.summarizer.common.PropertiesLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author avelinsk
 */
public class DocMachineParserTest {
  DocMachineParser docParser;
  Properties props;

  @Before
  public void setUP() {
    docParser = new DocMachineParser();
    props = new PropertiesLoader().loadProperties();
  }

  @Test
  public void parseFilesTest() {
    File dir = new File(props.getProperty("DATA_DIR"));
    File outputFile = new File(props.getProperty("docFile"));
    if (outputFile.exists()) {
      assertTrue(outputFile.delete());
    }

    docParser.parseFiles(dir, outputFile);
    assertTrue(outputFile.exists());
  }

  @After
  public void tearDown() {
    docParser = null;
    props = null;
  }
}
