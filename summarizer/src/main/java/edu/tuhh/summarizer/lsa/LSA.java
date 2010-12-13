package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import edu.ucla.sspace.text.Document;
import edu.ucla.sspace.text.OneLinePerDocumentIterator;
import edu.ucla.sspace.vector.DoubleVector;
import edu.ucla.sspace.vector.Vectors;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Creates an LSA semantic space.
 *
 * @author avelinsk
 */
public class LSA {
  private static Logger log = Logger.getLogger(LSA.class);

  public void runLSA() {
      Properties props = new PropertiesLoader().loadProperties();
//      IteratorFactory.setProperties(lsaprops);
      int noOfThreads = Runtime.getRuntime().availableProcessors();

      long start = System.currentTimeMillis();
      LatentSemanticAnalysis sspace = null;
      try {
        // initialize the semantic space
        sspace = new LatentSemanticAnalysis();
        Iterator<Document> iter = new OneLinePerDocumentIterator(props.getProperty("docFile"));

        //SVD reduction
        processDocumentsAndSpace(sspace, iter, noOfThreads, props);

        File output = initOutputFile(props, "termSpace.sspace");
        SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);
        log.info("Semantic space is saved after SVD reduction.");
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }

      saveDocumentSpace(sspace, props);

      long end = System.currentTimeMillis();
      log.info("LSA took " + (end - start) + "ms to index the document collection.");
    }


  protected File initOutputFile(Properties props, String fileName) {
    String SSPACE_DIR = props.getProperty("SSPACE_DIR");
    File outputPath = new File(SSPACE_DIR);
    File outputFile = new File(outputPath, fileName);

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

  protected void saveDocumentSpace(LatentSemanticAnalysis sspace, Properties props) {
    int numDocs = sspace.getDocumentsNumber();
    DoubleVector[] vectors = new DoubleVector[numDocs];
    log.info("Total documents in sspace: " + numDocs);
    for (int i = 0; i < numDocs; i++) {
      vectors[i] = Vectors.asDouble(sspace.getDocumentVector(i));
    }
    //File sspaceFile = initOutputFile(props, "docSpace.sspace");
    File matrixFile = initOutputFile(props, "docSpace.txt");
    File matrixCluster = initOutputFile(props, "cluster.txt");
    Matrix docSpace = Matrices.asMatrix(Arrays.asList(vectors));
    try {
      MatrixIO.writeMatrix(docSpace, matrixFile, MatrixIO.Format.SVDLIBC_DENSE_TEXT);
      MatrixIO.writeMatrix(docSpace, matrixCluster, MatrixIO.Format.CLUTO_DENSE);
      //LSAUtils.saveDocumentSpace(docSpace, sspaceFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}