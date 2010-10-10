package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.util.MultiMap;
import edu.ucla.sspace.vector.DoubleVector;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * User: avelinsk
 * Date: 08.10.2010
 * Time: 12:57:38
 */
public class QueryTest {
  SemanticSpace sspace;
  Query query;
  MultiMap<Double,String> results;


  @Before
  public void initializeTest(){
    try {
      sspace = SemanticSpaceIO.load(new File("sspace/LSA.sspace"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    query = new Query(sspace);
  }

  @Test
  public void testQuery(){
    results = query.getCosineSimilarity(sspace, "management", 20);
    Double key;
    String value;

    for (Object o : results.entrySet()){
      Map.Entry<Double,String> entry = (Map.Entry<Double,String>) o;
      key = entry.getKey();
      value = entry.getValue();
      System.out.println("key: "+key+" value: "+value);
    }
  }

  @Test
  public void testGetDocumentVector(){
    File fquery = new File("input/query.txt");
    try {
      fquery.createNewFile();
      BufferedWriter wr = new BufferedWriter(new FileWriter(fquery));
      wr.write("test document vector");
      wr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    DoubleVector testVector = query.getDocumentVector("input/query.txt");
    System.out.println(testVector.toString());
    Assert.assertNotNull(testVector.length());

    System.out.println("vector length: "+testVector.length());
    for (int i = 0; i < testVector.length(); i++){
      System.out.println(testVector.get(i));
    }
  }

  @After
  public void endTest(){
    sspace = null;
    query = null; 
    results = null;
  }

}
