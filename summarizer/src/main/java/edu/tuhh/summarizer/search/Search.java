package edu.tuhh.summarizer.search;

import edu.tuhh.summarizer.common.PropertiesLoader;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class Search implements Searcher {
  private Directory directory;
  private static File stopwords;
  private static final int MAXRESULTS = 20;
  private static Logger log = Logger.getLogger(Searcher.class);

  public Search(){
    try {
      Properties props = PropertiesLoader.loadProperties();
      String INDEX_DIR = props.getProperty("INDEX_DIR");
      String STOPWORDS = props.getProperty("STOPWORDS");
      stopwords = new File(STOPWORDS);
      directory = FSDirectory.open(new File(INDEX_DIR));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private IndexWriter getWriter() {
    IndexWriter writer = null;

    try {
      StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_29, stopwords);
      writer = new IndexWriter(directory, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return writer;
  }

  public List<Document> doLuceneSearch(String query) {
    //setup();
    IndexSearcher searcher;
    List<Document> result = new ArrayList<Document>();

    try {
      searcher = new IndexSearcher(directory);
      QueryParser parser = new QueryParser(Version.LUCENE_29, "contents",
              new StandardAnalyzer(Version.LUCENE_29, stopwords));
      Query q = parser.parse(query);
      TopDocs hits = searcher.search(q, MAXRESULTS);

      log.info("Found " + hits.totalHits + " document(s): ");
      ScoreDoc[] docs = hits.scoreDocs;
      for (ScoreDoc scoreDoc : docs) {
        int docId = scoreDoc.doc;
        Document doc = searcher.doc(docId);
        result.add(doc);
        log.info("Document " + docId + ": " + doc.get("path"));
      }

      searcher.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException pe) {
      pe.printStackTrace();
    }

    return result;
  }

  public List<Document> getDocsByIds(List<Integer> docIds){
    IndexSearcher searcher;
    List<Document> result = new ArrayList<Document>();
    try{
      searcher = new IndexSearcher(directory);
      for(Integer index : docIds){
        result.add(searcher.doc(index));
      }
      searcher.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }


  protected void deleteDocuments(List<String> docIdsToDelete) {
    try {
      IndexWriter writer = getWriter();
      for (String s : docIdsToDelete) {
        writer.deleteDocuments(new Term("id", s));
      }
      writer.optimize();
      writer.commit();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}