package edu.tuhh.summarizer.search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author avelinsk
 */
public class Searcher {
  private static Logger log = Logger.getLogger(Searcher.class);
  private Directory directory;
  private static File stopwords;
  private static final int MAXRESULTS = 20;
  private final static String STOPWORDS = "summarizer/data/stopwords/english-stop-words-large.txt";

  protected void setup(String indexDir) {
    try {
      stopwords = new File(STOPWORDS);
      directory = FSDirectory.open(new File(indexDir));
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

  public void search(String indexDir, String query) {
    setup(indexDir);
    IndexSearcher searcher;

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
        log.info("Document " + docId + ": " + doc.get("path"));
      }

      searcher.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException pe) {
      pe.printStackTrace();
    }

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
