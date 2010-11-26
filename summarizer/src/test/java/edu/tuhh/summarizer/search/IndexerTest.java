package edu.tuhh.summarizer.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: avelinsk
 */
public class IndexerTest {
  Indexer indexer;

  @Before
  public void setup() {
    indexer = new Indexer(true);
  }

  @Test
  public void testIndexDocuments() throws Exception {
    indexer.initializeIndex("summarizer/data/index", "summarizer/data/output");
  }

  @After
  public void teardown(){
    indexer = null;
  }
}
