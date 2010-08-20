package docMachine.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ng
 * Date: 17.08.2010
 * Time: 23:33:17
 * To change this template use File | Settings | File Templates.
 */
public class Searcher {
    private static final Log log = LogFactory.getLog(Searcher.class);
    public static void searchDocuments(String[] args) throws IOException, ParseException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage "+Searcher.class.getName()+" <index dir> <query>") ;
        }
        String indexDir = args[0];
        String query = args[1];

        search(indexDir,query);
    }

    public static void search(String indexDir, String query) throws IOException, ParseException {
        Directory idxDir = FSDirectory.open(new File(indexDir));
        IndexSearcher isearcher = new IndexSearcher(idxDir);
        QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new StandardAnalyzer(Version.LUCENE_30));
        Query q = parser.parse(query);
        long start = System.currentTimeMillis();
        TopDocs hits = isearcher.search(q,5);
        long end = System.currentTimeMillis();

        log.info("Found "+hits.totalHits+" document(s) in "+(end-start)+" milliseconds that match query "+q+": ");
        for(ScoreDoc scoreDoc: hits.scoreDocs){
            Document doc = isearcher.doc(scoreDoc.doc);
            log.info(doc.get("fullpath"));
        }

        isearcher.close();
    }
}
