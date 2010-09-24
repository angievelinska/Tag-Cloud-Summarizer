package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import edu.ucla.sspace.matrix.SVD;
import edu.ucla.sspace.matrix.YaleSparseMatrix;
import edu.ucla.sspace.text.Document;
import edu.ucla.sspace.text.IteratorFactory;
import edu.ucla.sspace.text.OneLinePerDocumentIterator;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.Vectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: avelinsk
 * Date: 11.08.2010
 *
 * Creates a Semantic Space using Latent Semantic Analysis
 *
 * TODO: refactor
 */
public class LSA {
    private final static Log log = LogFactory.getLog(LSA.class);

    /**
     * TODO: parameterize the number of threads
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void runLSA() throws IOException, InterruptedException{
        long start = System.currentTimeMillis();

        Properties props = setupProperties();
        //just for info
        //this.logProps();

        int noOfThreads = Runtime.getRuntime().availableProcessors();

        LatentSemanticAnalysis sspace = new LatentSemanticAnalysis();

        Iterator<Document> iter = getDocumentIterator();

        File output = initOutputFile();

        processDocumentsAndSpace(sspace, iter, noOfThreads, props);

        SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);

        saveMatrix(sspace);

        long end = System.currentTimeMillis();
        log.info("LSA used "+(end-start)+"ms to index the document collection.");
        log.info("Number of words in the sspace: "+sspace.getWords().size());
    }

  public void runLSA(List<File> documents){

  }

    protected File initOutputFile() throws IOException{
        File outputPath = new File("sspace");
        File outputFile = new File(outputPath,"LSA.sspace");

        if (!outputPath.exists()){
          outputPath.mkdir();
        }
        if (!outputFile.exists()){
            outputFile.createNewFile();
        } else {
            outputFile = File.createTempFile(this.getClass().getName(),".sspace",outputPath);
        }

        return outputFile;
    }

    protected void processDocumentsAndSpace(SemanticSpace space,
                                            Iterator<Document> iter,
                                            int noOfThreads,
                                            Properties props)
        throws IOException, InterruptedException{

        parseDocsMultiThreaded(space, iter, noOfThreads);
        space.processSpace(props);
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
                log.info("process docs: "+iter.next().toString());
                while (iter.hasNext()){
                    long start = System.currentTimeMillis();
                    Document document = iter.next();
                    int number = count.incrementAndGet();
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
            threads.add(t);
        }

        for (Thread t: threads)
            t.start();

        for (Thread t: threads)
            t.join();
    }

    /**
     * Input documents are parsed in a single file,
     * each document on a separate row.
     */
    protected Iterator<Document> getDocumentIterator () throws IOException{
        Properties props = System.getProperties();
        String docFile =  props.getProperty("docFile");
        Iterator<Document> lineIter = null;
        lineIter = new OneLinePerDocumentIterator(docFile);
        
        return lineIter;
    }


  protected void saveMatrix(SemanticSpace sspace){
    int numVectors = sspace.getWords().size();      // ???
    int numWords = sspace.getWords().size();
    DoubleVector[] vectors = new DoubleVector[numVectors];
    String[] words = new String[numWords];
    int i = 0;
    for (String word : sspace.getWords()){
      words[i] = word;
      vectors[i] = Vectors.asDouble(sspace.getVector(word));
      i++;
    }

    Matrix matrix = Matrices.asMatrix(Arrays.asList(vectors));
    MatrixIO.Format fmt = MatrixIO.Format.DENSE_TEXT;
    File outputMatrix = new File("sspace/matrix.txt");

    try {
      outputMatrix.createNewFile();
      MatrixIO.writeMatrix(matrix, outputMatrix, fmt);
      //Matrix[] matrixReduced = SVD.svd(outputMatrix, SVD.Algorithm.SVDLIBJ, fmt, 100);
      Matrix[] matrixReduced = SVD.svd(matrix,SVD.Algorithm.SVDLIBJ,100);
      //saveMatrix(matrixReduced);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void saveMatrix(Matrix[] matrix) {
    File dir  = new File("sspace");
    File f = null;
    PrintWriter pw = null;
    try {
      f = File.createTempFile("matrixReduced",".txt", dir);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      pw = new PrintWriter(f);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    try {
      fos = new FileOutputStream(f);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(matrix);
      oos.flush();
    } catch (FileNotFoundException fe){
      fe.printStackTrace();
    } catch (IOException ex){
      ex.printStackTrace();
    } finally {
      try {
        oos.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    
  }

  protected Matrix[] convertSpace(SemanticSpace sspace){
    Matrix matrix = new YaleSparseMatrix(1,1);
    return new Matrix[1];
  }

    /**
     * log the system properties - just FYI
     */
    protected void logProps(){
        Properties sysprops = System.getProperties();

        for(Enumeration en = sysprops.propertyNames(); en.hasMoreElements(); ){
            String key = (String) en.nextElement();
            String value = sysprops.getProperty(key);
            log.info(key+" = "+value);
        }
    }

    protected Properties setupProperties(){
        Properties props = System.getProperties();
        props.put(IteratorFactory.COMPOUND_TOKENS_FILE_PROPERTY,"compwords/compound-words.txt");
        props.put(IteratorFactory.TOKEN_FILTER_PROPERTY,"exclude=stopwords/english-stop-words-large.txt");
        //props.put(IteratorFactory.STEMMER_PROPERTY, "edu.ucla.sspace.text.EnglishStemmer");
        props.put("docFile","input/input.txt");
        props.put("svdAlgorithm","SVDLIBJ");
        props.put(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY,"100");
      // default format is binary
        props.put("outputFormat", SemanticSpaceIO.SSpaceFormat.TEXT);
        props.put("overwrite","true");
        props.put("verbose","true");

        IteratorFactory.setProperties(props);

        return props;
    }
}