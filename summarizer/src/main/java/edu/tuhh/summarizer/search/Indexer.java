package edu.tuhh.summarizer.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * @author avelinsk
 */
public class Indexer {
  private static final Log log = LogFactory.getLog(Indexer.class);
  private static IndexWriter writer;
  private String INDEX_DIR = "index";
  private String DATA_DIR = "output";

  public Indexer(boolean index){
    if(index){
      initializeIndex(INDEX_DIR, DATA_DIR);
    }
  }

  private void initializeIndex(String index_dir, String data_dir) {
    File index = new File(index_dir);
    File data = new File(data_dir);
    long start = new Date().getTime();

    try {
        int numIndexed = index(index, data);
        long end = new Date().getTime();
        log.info("Indexing " + numIndexed + " files took " + (end-start) + " ms.");
    } catch (IOException e) {
        log.error("Unable to index "+index+": "+e.getMessage());
    }
  }


  private static int index(File indexDir, File dataDir) throws IOException {
    if (!dataDir.exists() || !dataDir.isDirectory()) {
        throw new IOException(dataDir
                + " does not exist or is not a directory. Aborting...");
    }
    Directory dir = FSDirectory.open(indexDir);
    writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29), true, IndexWriter.MaxFieldLength.UNLIMITED);
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




  /***************** old code below **************************************************************/
  public static void indexDocuments(String[] args) throws Exception{
        if (args.length!=2){
            throw new IllegalArgumentException("Usage: java "+ Indexer.class.getName() +" <index dir> <data dir>");
        }

        String indexDir = args[0];
        String dataDir = args[1];
        long startTime = System.currentTimeMillis();

        Indexer indexer = new Indexer(indexDir);
        int noIndexed;

        try {
            noIndexed = indexer.index(dataDir, new TextFilesFilter());
        } finally {
            indexer.close();
        }
        long endTime = System.currentTimeMillis();

        log.info("Indexing "+noIndexed+" took "+(endTime-startTime)+" milliseconds");
    }

    public Indexer(String indexDir) throws IOException{
        Directory dir = FSDirectory.open(new File(indexDir));
        writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public void close() throws IOException{
        writer.close();
    }

    public int index(String dataPath, FileFilter filter) throws Exception{
        File[] files = new File(dataPath).listFiles();
        for (File f : files){
            if (!f.isDirectory()
                    && !f.isHidden()
                    && f.exists()
                    && f.canRead()
                    && (filter == null || filter.accept(f))){
                indexFile(f);
            }
        }

        return writer.numDocs();
    }

    protected Document indexDocument(File f) throws Exception{
        Document doc = new Document ();
        doc.add(new Field("contents", new FileReader(f), Field.TermVector.WITH_POSITIONS));
        doc.add(new Field("filename", f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        return doc;
    }

    private static class TextFilesFilter implements FileFilter{
        public boolean accept(File path){
            return path.getName().toLowerCase().endsWith(".html");
        }
    }

    private void indexFile (File f) throws Exception{
        log.info("Indexing "+f.getCanonicalPath());
        Document doc = indexDocument(f);
        writer.addDocument(doc);
    }

}
