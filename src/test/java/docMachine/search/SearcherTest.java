package docMachine.search;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ng
 * Date: 18.08.2010
 * Time: 11:15:31
 * To change this template use File | Settings | File Templates.
 */
public class SearcherTest {
    @Test
    public void testSearchDocuments() throws Exception {
        String[] args = {"idx",
                "content management server"};
        Searcher.searchDocuments(args);
    }
}
