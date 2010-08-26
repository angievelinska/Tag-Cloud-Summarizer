package docMachine.search;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ng
 * Date: 17.08.2010
 * Time: 22:15:52
 * To change this template use File | Settings | File Templates.
 */
public class IndexerTest {

    @Test
    public void testIndexDocuments() throws Exception {
        String[]dirs = {"C:\\master_thesis\\TagCloudSummarizer\\idx",
                "C:\\master_thesis\\TagCloudSummarizer\\input"};
        Indexer.indexDocuments(dirs);
    }
}
