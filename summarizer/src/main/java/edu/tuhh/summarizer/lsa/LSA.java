package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
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
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creates an LSA semantic space.
 *
 * @author avelinsk
 */
public class LSA {
  private static Logger log = Logger.getLogger(LSA.class);

  public void runLSA() {
    Properties props = PropertiesLoader.loadProperties();
    IteratorFactory.setProperties(props);
    int noOfThreads = Runtime.getRuntime().availableProcessors();

    long start = System.currentTimeMillis();
    LatentSemanticAnalysis sspace = null;
    try {
      // initialize the semantic space
      sspace = new LatentSemanticAnalysis();
      Iterator<Document> iter = new OneLinePerDocumentIterator(props.getProperty("docFile"));
      File output = initOutputFile(props);

      //SVD reduction
      processDocumentsAndSpace(sspace, iter, noOfThreads, props);
      SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);
      log.info("Semantic space is saved after SVD reduction.");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    // save the 3 matrices resulting from LSA as files
    saveMatrices(sspace, props);

    long end = System.currentTimeMillis();
    log.info("LSA took " + (end - start) + "ms to index the document collection.");
  }


  protected File initOutputFile(Properties props) {
    String SSPACE_DIR = props.getProperty("SSPACE_DIR");
    String SSPACE_FILE = props.getProperty("SSPACE_FILE");
    File outputPath = new File(SSPACE_DIR);
    File outputFile = new File(outputPath, SSPACE_FILE);

    if (!outputPath.exists()) {
      outputPath.mkdir();
    }
    try {
      if (!outputFile.exists()) {
        outputFile.createNewFile();
      } else {
        outputFile = File.createTempFile(this.getClass().getName(), ".sspace", outputPath);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return outputFile;
  }


  protected void processDocumentsAndSpace(SemanticSpace space,
                                          Iterator<Document> iter,
                                          int noOfThreads,
                                          Properties props)
          throws IOException, InterruptedException {

    parseDocsMultiThreaded(space, iter, noOfThreads);
    space.processSpace(props);
  }


  protected void parseDocsMultiThreaded(final SemanticSpace space,
                                        final Iterator<Document> iter,
                                        int noThreads)
          throws IOException, InterruptedException {
    Collection<Thread> threads = new LinkedList<Thread>();
    final AtomicInteger count = new AtomicInteger(0);
    for (int i = 0; i < noThreads; ++i) {
      Thread t = new Thread() {
        public void run() {
          log.info("Process document: " + iter.next().toString());
          while (iter.hasNext()) {
            Document document = iter.next();
            try {
              space.processDocument(document.reader());
            } catch (Throwable t) {
              t.printStackTrace();
            }
          }
        }
      };
      threads.add(t);
    }

    for (Thread t : threads)
      t.start();

    for (Thread t : threads)
      t.join();
  }


  protected void saveMatrices(SemanticSpace sspace, Properties props) {
    log.info("Saving 3 matrices after LSA...");
    int numWords = sspace.getWords().size();
    DoubleVector[] vectors = new DoubleVector[numWords];
    String[] words = new String[numWords];
    int i = 0;
    log.info("Total words in sspace: " + words.length);
    for (String word : sspace.getWords()) {
      words[i] = word;
      vectors[i] = Vectors.asDouble(sspace.getVector(word));
      i++;
    }

    Matrix matrix = Matrices.asMatrix(Arrays.asList(vectors));
    Matrix[] matricesReduced = SVD.svd(matrix, SVD.Algorithm.SVDLIBJ, 90);
    saveMatrices(matricesReduced, props);
  }


  protected void saveMatrices(Matrix[] matrix, Properties props) {
    File dir = new File(props.getProperty("SSPACE_DIR"));
    File matrix_u = new File(dir, props.getProperty("MATRIX_U"));
    File matrix_s = new File(dir, props.getProperty("MATRIX_S"));
    File matrix_vt = new File(dir, props.getProperty("MATRIX_Vt"));
    try {
      matrix_u.createNewFile();
      matrix_s.createNewFile();
      matrix_vt.createNewFile();

      MatrixIO.writeMatrix(matrix[0], matrix_u, MatrixIO.Format.SVDLIBC_DENSE_TEXT);
      MatrixIO.writeMatrix(matrix[1], matrix_s, MatrixIO.Format.SVDLIBC_DENSE_TEXT);
      MatrixIO.writeMatrix(matrix[2], matrix_vt, MatrixIO.Format.SVDLIBC_DENSE_TEXT);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}