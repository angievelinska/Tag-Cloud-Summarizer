package edu.tuhh.summarizer.search;

import org.apache.lucene.document.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author: avelinsk
 */
public class SearcherTest {
  Search search;
  Properties props;
  Searcher searcher;

  @Before
  public void setUP() {
    searcher = new Search();
  }

  @Test
  public void testSearchDocuments() throws Exception {
    List<Document> result = searcher.doLuceneSearch("I you we he she it ours");
    assertFalse(result.size()!= 0);
  }

  @Test
  public void testSearch(){
    List<Document> result = searcher.doLuceneSearch("Test content management database");
    assertTrue(result.size() != 0);
  }

  @After
  public void tearDown() {
    search = null;
    props = null;
  }
}
