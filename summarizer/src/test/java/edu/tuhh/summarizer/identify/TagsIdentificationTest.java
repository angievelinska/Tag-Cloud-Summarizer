package edu.tuhh.summarizer.identify;

import edu.tuhh.summarizer.common.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author avelinsk
 */
public class TagsIdentificationTest {
  Map<Double, Document> test;
  TagsIdentification tid;

  @Before
  public void setup(){
    tid = new TagsIdentification();

    test = new HashMap<Double, Document>();
/*    Document doc1 = new DocumentImpl(4);
    Document doc2 = new DocumentImpl(5);
    Document doc3 = new DocumentImpl(1);
    test.put(new Double(1.98), doc1);
    test.put(new Double(1.95), doc2);
    test.put(new Double(0.87), doc3);   */

  }

  @Test
  public void testSortMap(){
    Map<Double, Document> result = tid.sortMap(test);

    for (Map.Entry entry : result.entrySet()){
      System.out.println(entry.getKey() + " " + ((Document)entry.getValue()).getId());
    }
  }

  @After
  public void teardown(){
    tid = null;
    test = null;
  }
}
