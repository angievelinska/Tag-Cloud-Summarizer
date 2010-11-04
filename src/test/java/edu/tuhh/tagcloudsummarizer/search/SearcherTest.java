package edu.tuhh.tagcloudsummarizer.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: ng
 * Date: 18.08.2010
 * Time: 11:15:31
 */
public class SearcherTest {
    Searcher searcher;

    @Before
    public void setup(){
      searcher = new Searcher();
    }

    @Test
    public void testSearchDocuments() throws Exception {
        String[] args = {"idx",
                "content management server"};
        searcher.searchDocuments(args);
    }

    @After
    public void tearDown(){
      searcher = null;
    }
}
