package edu.tuhh.summarizer.search;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ng
 * Date: 17.08.2010
 * Time: 22:15:52
 */
public class IndexerTest {

    @Test
    public void testIndexDocuments() throws Exception {
        String[]dirs = {"index","output"};
        Indexer.indexDocuments(dirs);
    }
}
