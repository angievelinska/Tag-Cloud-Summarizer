package edu.tuhh.summarizer.search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * @author avelinsk
 */
public class Indexer {
  private static Logger log = Logger.getLogger(Indexer.class);
  private final static String INDEX_DIR = "summarizer/data/index";
  private final static String DATA_DIR = "summarizer/data/output";
  private final static String STOPWORDS = "summarizer/data/stopwords/english-stop-words-large.txt";
  private final static int mergeFactor = 10;
  private final static int minMergeDocs = 10;
  private final static int maxMergeDocs = Integer.MAX_VALUE;


  public Indexer(boolean index) {
    if (index) {
      initializeIndex(INDEX_DIR, DATA_DIR);
    }
  }


  protected void initializeIndex(String index_dir, String data_dir) {
    File index = new File(index_dir);
    File data = new File(data_dir);
    long start = new Date().getTime();

    try {
      int numIndexed = index(index, data);
      long end = new Date().getTime();
      log.info("Indexing " + numIndexed + " files took " + (end - start) + " ms.");
    } catch (IOException e) {
      log.error("Unable to index " + index + " :\n" + e.getMessage());
    }
  }


  private static int index(File indexDir, File dataDir) throws IOException {
    if (!dataDir.exists() || !dataDir.isDirectory()) {
      throw new IOException(dataDir
              + " does not exist or is not a directory. Aborting...");
    }
    Directory dir = FSDirectory.open(indexDir);
    File stopwords = new File(STOPWORDS);
    IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29, stopwords),
            true, IndexWriter.MaxFieldLength.UNLIMITED);
    writer.setUseCompoundFile(true);

    indexDirectory(writer, dataDir);

    int numIndexed = writer.numDocs();
    writer.optimize();
    writer.close();
    return numIndexed;
  }


  private static void indexDirectory(IndexWriter writer, File dir)
          throws IOException {
    File[] files = dir.listFiles();

    for (int i = 0; i < files.length; i++) {
      File f = files[i];
      if (f.isDirectory()) {
        indexDirectory(writer, f);
      } else if (f.getName().endsWith(".txt")) {
        indexFile(writer, f);
      }
    }
  }


  private static void indexFile(IndexWriter writer, File f)
          throws IOException {
    if (f.isHidden() || !f.exists() || !f.canRead()) {
      System.err.println("Could not write " + f.getName());
      return;
    }
    System.err.println("Indexing " + f.getCanonicalPath());

    Document doc = new Document();
    doc.add(new Field("path", f.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field("modified", DateTools.timeToString(f.lastModified(), DateTools.Resolution.MINUTE), Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field("contents", new FileReader(f), Field.TermVector.YES));
    writer.addDocument(doc);
  }


  private static int hitCount(IndexSearcher searcher, Query query) {
    int hits = 0;
    try {
      hits = searcher.search(query, 1).totalHits;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return hits;
  }

}
