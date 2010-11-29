package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.util.MultiMap;
import edu.ucla.sspace.vector.DoubleVector;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class QueryTest {
  SemanticSpace sspaceTerms;
  Query query;
  MultiMap<Double, String> results;
  long start, end;


  @Before
  public void initializeTest() {
    try {
      Properties props = PropertiesLoader.loadProperties();
      String SSPACE_TERMS = props.getProperty("SSPACE_TERMS");
      query = new Query();
      sspaceTerms = SemanticSpaceIO.load(new File(SSPACE_TERMS));
      start = System.currentTimeMillis();
    } catch (IOException e) {
      e.printStackTrace();
    }

    //query = new Query(sspace);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetMostSimilarWords() {
    results = query.getSimilarWords(sspaceTerms, "content", 20);
    Double key;
    String value;
    for (Object o : results.entrySet()) {
      Map.Entry<Double, String> entry = (Map.Entry<Double, String>) o;
      key = entry.getKey();
      value = entry.getValue();
      System.out.println("key: " + key + " value: " + value);
    }
  }

  @Test
  public void testGetDocumentVector() {

    DoubleVector testVector = query.getQueryAsVector("content management system");
    Assert.assertNotNull(testVector.length());

    System.out.println("vector length: " + testVector.length());
    for (int i = 0; i < testVector.length(); i++) {
      System.out.println(testVector.get(i));
    }
  }

  @Ignore
  //prints out all words in the sspace
  @Test
  public void testGetWords() {
    System.out.println("number of words: " + sspaceTerms.getWords().size());
    for (Object o : sspaceTerms.getWords()) {
      String entry = (String) o;
      System.out.println(entry);
    }
  }


  @After
  public void endTest() {
    sspaceTerms = null;
    query = null;
    results = null;
    end = System.currentTimeMillis();
    System.out.print("query took: " + (end - start) + " ms.");
  }


  private void prettyPrintMatrix(String legend, Matrix matrix,
                                 String[] documents, PrintWriter writer) {
    writer.printf("=== %s ===%n", legend);
    writer.printf("%6s", " ");
    for (int i = 0; i < documents.length; i++) {
      writer.printf("%8s", documents[i]);
    }
    writer.println();
    for (int i = 0; i < documents.length; i++) {
      writer.printf("%6s", documents[i]);
      for (int j = 0; j < documents.length; j++) {
        writer.printf("%8.4f", matrix.get(i, j));
      }
      writer.println();
    }
    writer.flush();
  }

}
