package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * @author: avelinsk
 */
public class LSATest {
  LSA lsa;

  @Before
  public void setUP() throws Exception {
    lsa = new LSA();
  }

  @Test
  public void testLSA() {
    lsa.runLSA();

    Properties props = PropertiesLoader.loadProperties();
    File sspace = new File(props.getProperty("SSPACE_TERMS"));
    Assert.assertTrue("TermSpace is saved.",sspace.exists());
  }

  @After
  public void tearDown() throws Exception {
    lsa = null;
  }
}
