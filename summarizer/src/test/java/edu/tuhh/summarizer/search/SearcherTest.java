package edu.tuhh.summarizer.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: avelinsk
 */
public class SearcherTest {
    Searcher searcher;

    @Before
    public void setup(){
      searcher = new Searcher();
    }

    @Test
    public void testSearchDocuments() throws Exception {
        searcher.search("summarizer/data/index","I you we shall win the contest");
    }

    @After
    public void tearDown(){
      searcher = null;
    }
}
