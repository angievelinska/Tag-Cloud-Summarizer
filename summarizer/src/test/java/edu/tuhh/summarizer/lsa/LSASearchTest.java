package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import edu.ucla.sspace.util.MultiMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class LSASearchTest {
  Matrix docsMatrix;
  SemanticSpace sspaceTerms;
  Query query;
  MultiMap<Double, String> results;
  long start, end;


  @Before
  public void initializeTest() {
    try {
      Properties props = new PropertiesLoader().loadProperties();
      String SSPACE_TERMS = props.getProperty("SSPACE_TERMS");
      String DOCS_MATRIX = props.getProperty("DOCS_MATRIX");
      query = new Query();
      sspaceTerms = SemanticSpaceIO.load(new File(SSPACE_TERMS));
      docsMatrix = MatrixIO.readMatrix(new File(DOCS_MATRIX),MatrixIO.Format.SVDLIBC_DENSE_TEXT);
      start = System.currentTimeMillis();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDocsSearch() {
    String q = "content management system";
    List<Query.SearchResult> results =
            query.searchDocSpace(q);
    printResults(q, results);
  }

  @Test
  public void testTermsSearch(){
   String q = "content management system";
    List<Query.SearchResult> results =
            query.searchTermSpace(q,20);
    printResults(q, results);
  }

  @After
  public void endTest() {
    docsMatrix = null;
    sspaceTerms = null;
    query = null;
    results = null;
    end = System.currentTimeMillis();
    System.out.print("query took: " + (end - start) + " ms.");
  }

  private void printResults(String query,
                                  List<Query.SearchResult> results) {
    System.out.printf("Results for query: [%s]%n", query);
    for (Query.SearchResult result : results) {
      System.out.printf("%s %s (score = %8.4f)%n", result.index, result.word, result.score);
    }
  }

}

