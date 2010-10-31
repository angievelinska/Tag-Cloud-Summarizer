package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.util.MultiMap;
import edu.ucla.sspace.vector.DoubleVector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
  long start,end;


  @Before
  public void initializeTest(){
    try {
      query = new Query();
      sspace = SemanticSpaceIO.load(new File("sspace/LSA.sspace"));
      start = System.currentTimeMillis();
    } catch (IOException e) {
      e.printStackTrace();
    }

    //query = new Query(sspace);
  }

  @Test
  public void testQuery(){
    results = query.getSimilarWords(sspace, "content", 20);
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

    DoubleVector testVector = query.getQueryAsVector("input/query.txt");
    Assert.assertNotNull(testVector.length());

    System.out.println("vector length: "+testVector.length());
    for (int i = 0; i < testVector.length(); i++){
      System.out.println(testVector.get(i));
    }
  }

  @Test
  public void testGetWords(){
    System.out.println("number of words: "+sspace.getWords().size());
    for (Object o : sspace.getWords()){
      String entry = (String) o;
      System.out.println(entry);
    }
  }

  @After
  public void endTest(){
    sspace = null;
    query = null; 
    results = null;
    end = System.currentTimeMillis();
    System.out.print("query took: "+(end-start));
    
  }

}
