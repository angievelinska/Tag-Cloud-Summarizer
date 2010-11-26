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
  //private static final Log log = LogFactory.getLog(Searcher.class);
  private static Logger log = Logger.getLogger(Searcher.class);
  private Directory directory;
  private String sw = "summarizer/data/stopwords/english-stop-words-large.txt";
  private File stopwords;

  protected void setup(String idxDir) {
    try{
      stopwords = new File(sw);
      directory = FSDirectory.open(new File(idxDir));
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  private IndexWriter getWriter(){
    IndexWriter idxWriter = null;

    try{
      StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_29, stopwords);
      idxWriter = new IndexWriter(directory, analyzer,IndexWriter.MaxFieldLength.UNLIMITED);
    } catch (IOException e){
      e.printStackTrace();
    }
    return idxWriter;
  }

  public void search(String idxDir, String query){
    setup(idxDir);
    IndexSearcher searcher;

    try{
      searcher = new IndexSearcher(directory);
      QueryParser parser = new QueryParser(Version.LUCENE_29, "contents",
                                    new StandardAnalyzer(Version.LUCENE_29, stopwords));
      Query q = parser.parse(query);
      TopDocs hits = searcher.search(q,10);

      log.info("Found "+hits.totalHits+" document(s): ");
      ScoreDoc[] docs = hits.scoreDocs;
      for (int i=0; i<docs.length; i++){
        int docId = docs[i].doc;
        Document doc = searcher.doc(docId);
        log.info("Document "+docId+" : "+doc.get("contents"));
      }

      for(ScoreDoc scoreDoc: hits.scoreDocs){
          Document doc = searcher.doc(scoreDoc.doc);
          log.info(doc.get("path"));
      }
      searcher.close();
      
    } catch(IOException e){
      e.printStackTrace();
    } catch(ParseException pe){
      pe.printStackTrace();
    }

  }

  public int getHitCount(String fieldName, String searchString){
    IndexSearcher idxSearch = null;
    int hitCount = 0;
    try{
      idxSearch = new IndexSearcher(directory);
      Term t = new Term(fieldName, searchString);
      Query q = new TermQuery(t);
      hitCount = Searcher.hitCount(idxSearch, q);
      idxSearch.close();
    } catch(IOException e){
      e.printStackTrace();
    }
    return hitCount;
  }

  public static int hitCount(IndexSearcher searcher, Query query){
    int hits = 0;
    try{
       hits = searcher.search(query,1).totalHits;
     } catch (IOException e){
       e.printStackTrace();
     }
    return hits;
  }

  protected void deleteDocuments(List<String> docIdsToDelete){
    try{
      IndexWriter writer = getWriter();
      for (String s : docIdsToDelete){
        writer.deleteDocuments(new Term("id",s));
      }
      writer.optimize();
      writer.commit();
      writer.close();
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  /**
   * Replaces the document at index idx with the parameter doc
   */
  protected void updateDocument(String idx, Document doc){
    IndexWriter writer = getWriter();
    //TODO: add the document to the index
    
    try {
      writer.updateDocument(new Term("id", idx), doc);
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  /**
   * Get documents "like" the given one using term frequency vectors
   */
  protected void getSimilar(Document doc){
    
  }

}
