package docMachine.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: avelinsk
 * Date: 14.08.2010
 */
public class Indexer {
    private static final Log log = LogFactory.getLog(Indexer.class);
    private IndexWriter writer;
    
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
        doc.add(new Field("contents", new FileReader(f), Field.TermVector.YES));
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
