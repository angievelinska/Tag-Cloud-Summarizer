package edu.tuhh.tagcloudsummarizer.lsa;

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

public class LSAapp {


private String INDEX_DIR;
private String DATA_DIR;


public LSAapp(String corpus_directory,
                                String index_directory,
                                boolean do_index) {
    System.err.println("starting LSA...\n");

    INDEX_DIR = index_directory;
    DATA_DIR = corpus_directory;
    if (do_index) {
        initializeIndex(INDEX_DIR, DATA_DIR);
    }
}


public static void initializeIndex(String indexDir, String dataDir) {
    File indexDir_f = new File(indexDir);
    File dataDir_f = new File(dataDir);

    long start = new Date().getTime();
    try {
        int numIndexed = index(indexDir_f, dataDir_f);
        long end = new Date().getTime();

        System.err.println("Indexing " + numIndexed + " files took " + (end -start) + " milliseconds");
    } catch (IOException e) {
        System.err.println("Unable to index "+indexDir_f+": "+e.getMessage());
    }
}

//creates the index files
private static int index(File indexDir, File dataDir)
    throws IOException {
    if (!dataDir.exists() || !dataDir.isDirectory()) {
        throw new IOException(dataDir
                + " does not exist or is not a directory");
    }
    Directory dir = FSDirectory.open(indexDir);
    IndexWriter writer = new IndexWriter(dir,
                new StandardAnalyzer(Version.LUCENE_29), true, IndexWriter.MaxFieldLength.UNLIMITED);
    writer.setUseCompoundFile(false);

    indexDirectory(writer, dataDir);

    int numIndexed = writer.numDocs();
    writer.optimize();
    writer.close();
    return numIndexed;
}

/*
 * recursive method that calls itself when it finds a directory, or indexes if
 * it is at a file ending in ".txt"
 */

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

  /*
  * method to actually index a file using Lucene, adds a document
  * onto the index writer
  */

  private static void indexFile(IndexWriter writer, File f)
      throws IOException {

    if (f.isHidden() || !f.exists() || !f.canRead()) {
        System.err.println("Could not write "+f.getName());
        return;
    }

    System.err.println("Indexing " + f.getCanonicalPath());

    Document doc = new Document();

    doc.add(new Field("path", f.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field("modified", DateTools.timeToString(f.lastModified(), DateTools.Resolution.MINUTE),Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field("contents", new FileReader(f), Field.TermVector.YES));

    writer.addDocument(doc);
  }

  private static int hitCount(IndexSearcher searcher, Query query){
    int hits = 0;
    try{
       hits = searcher.search(query,1).totalHits;
     } catch (IOException e){
       e.printStackTrace();
     }
    return hits;
  }


}


