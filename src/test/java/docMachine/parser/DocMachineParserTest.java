package docMachine.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author avelinsk
 */
public class DocMachineParserTest {
    DocMachineParser docParser;

  @Before
  public void setUp(){
    docParser = new DocMachineParser();
  }

  @After
  public void tearDown(){
    docParser = null;
  }

  @Test
  public void parseFilesTest(){
    File dir = new File("output");
    File outputFile = new File("input/input.txt");
    docParser.parseFiles(dir, outputFile);
  }

}
