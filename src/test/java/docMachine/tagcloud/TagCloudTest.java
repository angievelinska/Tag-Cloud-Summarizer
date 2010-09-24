package docMachine.tagcloud;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mcavallo.opencloud.Cloud;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: avelinsk Date: 03.09.2010
 */
public class TagCloudTest {
  TagCloud tc;
  
  @Before
  public void init(){
    tc = new TagCloud();
  }

  @Test
  public void testTagCloud(){
    Map<Double, String> tags = new HashMap<Double, String>();
    tags.put((double) 5, "CoreMedia");
    tags.put((double) 4, "content");
    tags.put(3.5, "server");
    tc.generateCloud(5.0, 20, tags, 1.0);
    
    File dir = new File("opencloud");
    if (!dir.exists()){
      Assert.assertTrue(dir.mkdir());
    }
    File file = new File(dir,"cloud.html");
    try {
      Assert.assertTrue(file.createNewFile());
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    tc.serializeCloud(file);

    Cloud c2 = tc.deserializeCloud(file);
    c2.getWordPattern();
  }

  @After
  public void terminate(){
    tc = null;
  }

}