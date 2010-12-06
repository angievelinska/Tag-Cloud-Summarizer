package edu.tuhh.summarizer.search;

import edu.tuhh.summarizer.common.PropertiesLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author: avelinsk
 */
public class IndexerTest {
  Indexer indexer;
  Properties props;

  @Before
  public void setUP() {
    props = new PropertiesLoader().loadProperties();
  }

  @Test
  public void testIndexDocuments() throws Exception {
    indexer = new Indexer();
  }

  @After
  public void tearDown(){
    indexer = null;
    props = null;
  }
}
