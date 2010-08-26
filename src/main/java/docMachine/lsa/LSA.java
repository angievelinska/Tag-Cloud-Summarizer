package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.text.Document;
import edu.ucla.sspace.text.IteratorFactory;
import edu.ucla.sspace.text.OneLinePerDocumentIterator;
import edu.ucla.sspace.util.CombinedIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: avelinsk
 * Date: 11.08.2010
 *
 * The class creates a Semantic Space using Latent Semantic Analysis (LSA) algorithm.
 */
public class LSA {
    private final static Log log = LogFactory.getLog(LSA.class);

    protected LatentSemanticAnalysis invokeLSA() throws IOException{
        //add all documents to the semantic space and call processSpace
        Properties props = System.getProperties();
        props.putAll(initProperties());
        LatentSemanticAnalysis lsa = new LatentSemanticAnalysis(props);
        //just for info
        this.logProps();
        
        return lsa;
    }

    protected File initOutputFile() throws IOException{
        File outputPath = new File("C:\\master_thesis\\TagCloudSummarizer\\sspace");
        File outputFile = new File(outputPath,"LSA.sspace");
        if (!outputFile.exists()){
            outputFile.createNewFile();
        }
        return outputFile;
    }

    public void runLSA() throws IOException{
        long start = System.currentTimeMillis();
        SemanticSpace sspace = this.invokeLSA();
        File output = this.initOutputFile();
        DataInputStream dis= new DataInputStream(new BufferedInputStream(new FileInputStream(output)));

        Iterator<Document> iterator = this.getDocumentIterator();
        

        SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);
        long end = System.currentTimeMillis();
        log.info("-------------INFO---------------");
        log.info("LSA used "+(end-start)+"ms to index the document collection.");
        log.info("Name of semantic algorithm:"+sspace.getSpaceName());
        log.info("Number of words in the sspace: "+sspace.getWords().size());
//        this.logProps();
        log.info("-------------END----------------");
    }

    protected void parseDocuments() throws IOException{
        int noThreads = Runtime.getRuntime().availableProcessors();
        IteratorFactory.setProperties(System.getProperties());
        SemanticSpace space = new LatentSemanticAnalysis();



    }

    protected void parseDocsMultiThreaded(final SemanticSpace space,
                                          final Iterator<Document> iter,
                                          int noThreads)
        throws IOException, InterruptedException
    {
        Collection<Thread> threads = new LinkedList<Thread>();
        final AtomicInteger count = new AtomicInteger(0);
        for (int i=0; i < noThreads; ++i){
            Thread t = new Thread(){
            public void run(){
                while (iter.hasNext()){
                    long start = System.currentTimeMillis();
                    Document document = iter.next();
                    int number = count.incrementAndGet();
                    int terms = 0;
                    try {
                        space.processDocument(document.reader());
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    long end = System.currentTimeMillis();
                    log.info("Document No"+number+" is parsed in "+(end-start)+"ms.");
                }
            }
        };
    }

    protected Iterator<Document> getDocumentIterator() throws IOException{
        Properties props = System.getProperties();
        Iterator<Document> iterator = null;
        String docFile =  props.getProperty("docFile");
        String[] fileNames = docFile.split("[\\r?\\n]+");
        log.info("docFile: "+fileNames.length);

        Collection<Iterator<Document>> docIter = new LinkedList<Iterator<Document>>();
        for (String s: fileNames){
            OneLinePerDocumentIterator lineIter = new OneLinePerDocumentIterator(docFile);
        }
        iterator = new CombinedIterator<Document>(iterator);

        return iterator;
    }

    /**
     * log the system properties set; just FYI
     */
    protected void logProps(){
        Properties sysprops = System.getProperties();
        log.info("Properties: ");
        for(Enumeration en = sysprops.propertyNames(); en.hasMoreElements(); ){
            String key = (String) en.nextElement();
            String value = sysprops.getProperty(key);
            log.info(key+" = "+value);
        }
    }

    protected Hashtable initProperties(){
        Hashtable props = new Hashtable();
        props.put("docFile","C:\\master_thesis\\TagCloudSummarizer\\input\\input.txt");
/*        props.put(LatentSemanticAnalysis.LSA_SVD_ALGORITHM_PROPERTY,"SVDLIBJ");
        props.put(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY,"300");
        props.put(LatentSemanticAnalysis.MATRIX_TRANSFORM_PROPERTY,"edu.ucla.sspace.matrix.LogEntropyTransform");*/
        props.put("svdAlgorithm","SVDLIBJ");
        props.put("outputFormat", "text");
        props.put("overwrite","true");
        props.put("tokenFilter","exclude=C:\\master_thesis\\TagCloudSummarizer\\stopwords\\english-stop-words-large.txt");
        props.put("stemmingAlgorithm","edu.ucla.sspace.text.EnglishStemmer");
        props.put("compoundWords","C:\\master_thesis\\TagCloudSummarizer\\compwords\\compound-words.txt");
        props.put("verbose","true");

        return props;
    }

}