package edu.tuhh.summarizer.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import edu.ucla.sspace.matrix.SVD;
import edu.ucla.sspace.text.Document;
import edu.ucla.sspace.text.IteratorFactory;
import edu.ucla.sspace.text.OneLinePerDocumentIterator;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.Vectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author avelinsk
 *
 * Creates a Semantic Space using Latent Semantic Analysis
 *
 * TODO: refactor
 */
public class LSA {
  private final static Log log = LogFactory.getLog(LSA.class);
 // public LatentSemanticAnalysis sspace = null;
  /**
   * TODO: parameterize the number of threads, more threads, faster app
   *
   */
  public void runLSA(){
      Properties props = setupProperties();
      int noOfThreads = Runtime.getRuntime().availableProcessors();
      LatentSemanticAnalysis sspace= null;
      // FYI
      //this.logProps();

      long start = System.currentTimeMillis();
      try{
        sspace = new LatentSemanticAnalysis();

        Iterator<Document> iter = getDocumentIterator();

        File output = initOutputFile();

        //SVD reduction
        processDocumentsAndSpace(sspace, iter, noOfThreads, props);
        log.info("prepare to save semantic space after svd");
        SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);

      } catch (IOException e){
        e.printStackTrace();
      } catch (InterruptedException ex){
        ex.printStackTrace();
      }

      // save the 3 matrices from LSA to files
      saveMatrices(sspace);

      long end = System.currentTimeMillis();
      log.info("LSA used "+(end-start)+"ms to index the document collection.");
      log.info("Number of words in the sspace: "+sspace.getWords().size());
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
                  //log.info("Document No"+number+" is parsed in "+(end-start)+"ms.");
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
      Iterator<Document> lineIter = new OneLinePerDocumentIterator(docFile);

      return lineIter;
  }


  protected void saveMatrices(SemanticSpace sspace){
    log.info("save 3 matrices");
    int numWords = sspace.getWords().size();
    DoubleVector[] vectors = new DoubleVector[numWords];
    String[] words = new String[numWords];
    int i = 0;
    log.info("size of words in sspace: "+words.length);
    for (String word : sspace.getWords()){
      words[i] = word;
      vectors[i] = Vectors.asDouble(sspace.getVector(word));
      i++;
    }

    try {
      PrintWriter pw = new PrintWriter(new File("sspace/words.dat"));
      for (int j=0; j<words.length;j++){
        pw.write(words[j]);
        pw.println();
      }
      log.info("words printed to file words.dat");
      //pw.write(words.toString());
      pw.close();
    } catch (IOException e){
      e.printStackTrace();
    }

    Matrix matrix = Matrices.asMatrix(Arrays.asList(vectors));
    //MatrixIO.Format fmt = MatrixIO.Format.SVDLIBC_SPARSE_TEXT;
    MatrixIO.Format fmt = MatrixIO.Format.SVDLIBC_DENSE_TEXT;
    File outputMatrix = new File("sspace/matrix.dat");

    try {
      outputMatrix.createNewFile();
      MatrixIO.writeMatrix(matrix, outputMatrix, fmt);
      Matrix[] matricesReduced = SVD.svd(matrix,SVD.Algorithm.SVDLIBJ,90);
      //Matrix[] matricesReduced = SVD.svd(outputMatrix,SVD.Algorithm.JAMA, MatrixIO.Format.SVDLIBC_DENSE_TEXT,120);
      saveMatrices(matricesReduced);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }


  protected void saveMatrices(Matrix[] matrix) {
    log.info("start saving three decomposed matrices");
    File dir  = new File("sspace");
    File f1 = new File(dir,"matrix_U.txt");
    File f2 = new File(dir,"matrix_S.txt");
    File f3 = new File(dir,"matrix_Vt.txt");
    try {
      f1.createNewFile();
      f2.createNewFile();
      f3.createNewFile();

      MatrixIO.writeMatrix(matrix[0], f1, MatrixIO.Format.SVDLIBC_DENSE_TEXT);
      MatrixIO.writeMatrix(matrix[1], f2, MatrixIO.Format.SVDLIBC_DENSE_TEXT);
      MatrixIO.writeMatrix(matrix[2], f3, MatrixIO.Format.SVDLIBC_DENSE_TEXT);

    } catch (IOException e) {
      e.printStackTrace();
    }
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
      props.put(LatentSemanticAnalysis.LSA_DIMENSIONS_PROPERTY,"90");
      //props.put("svdAlgorithm","SVDLIBJ");
      props.put(LatentSemanticAnalysis.LSA_SVD_ALGORITHM_PROPERTY, "SVDLIBJ");
      props.put(LatentSemanticAnalysis.RETAIN_DOCUMENT_SPACE_PROPERTY, "true");
    // default format is binary
      props.put("outputFormat", SemanticSpaceIO.SSpaceFormat.TEXT);
      props.put("overwrite","true");
      props.put("verbose","true");

      IteratorFactory.setProperties(props);

      return props;
  }
}