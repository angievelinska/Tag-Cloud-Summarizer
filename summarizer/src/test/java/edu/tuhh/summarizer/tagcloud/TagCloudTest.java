package edu.tuhh.summarizer.tagcloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.Filter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author avelinsk
 */
public class TagCloudTest {
  TagCloud tc;

  @Before
  public void init() {
    tc = new TagCloud();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testHashMap() {
    Map<String, Double> tags = new HashMap<String, Double>();
    tags.put("CoreMedia", (double) 5);
    tags.put("content", (double) 4);
    tags.put("server", 3.5);

    double weight;
    for (Object o : tags.entrySet()) {
      Map.Entry<String, Double> entry = (Map.Entry<String, Double>) o;
      String word = entry.getKey();
      System.out.println(word);
      weight = entry.getValue();
      System.out.println(weight);
    }
  }


  @Test
  public void testTagCloud() {
    Map<String, Double> tags = new HashMap<String, Double>();
    tags.put("CoreMedia", (double) 5);
    tags.put("content", (double) 4);
    tags.put("server", 3.5);

    tc.generateCloud(5.0, 20, tags, 1.0);

    File dir = new File("summarizer/data/opencloud");
    if (!dir.exists()) {
      assertTrue(dir.mkdir());
    }
    File file = new File(dir, "cloud.html");
    if (!file.exists()) {
      try {
        assertTrue(file.createNewFile());
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    tc.serializeCloud(file);

    Cloud c2 = tc.deserializeCloud(file);
    System.out.println("Word pattern: "+c2.getWordPattern());
  }

  @Test
  public void getTagCloudTest() {
    Cloud c = tc.getExampleCloud(); 
    for (Tag t : c.tags()) {
      System.out.println(t.getName());
    }
  }

  @Test
  public void checkFilter() {
    Filter<Tag> filter = tc.getStopWords();
    // Discard words contained in the dictionary
    assertFalse(filter.accept(new Tag("a")));
    assertFalse(filter.accept(new Tag("an")));
    assertFalse(filter.accept(new Tag("the")));

    // Accept words not contained in the dictionary
    assertTrue(filter.accept(new Tag("sun")));
    assertTrue(filter.accept(new Tag("aa")));
  }

  @After
  public void terminate() {
    tc = null;
  }


}