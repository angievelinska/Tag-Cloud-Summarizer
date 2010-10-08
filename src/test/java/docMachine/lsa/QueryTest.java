package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.util.MultiMap;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
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
    results = query.getCosineSimilarity(sspace, "content", 20);
    Set entries = results.entrySet();
    for (Iterator iter = entries.iterator(); iter.hasNext();){


    }

    for (Object o : results.entrySet()){
      Map.Entry<Double,String> entry = (Map.Entry<Double,String>)o;
      
    }
  }



}
