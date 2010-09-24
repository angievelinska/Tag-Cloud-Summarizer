package docMachine.tagcloud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mcavallo.opencloud.Cloud;

import java.io.File;
import java.io.IOException;

/**
 * User: avelinsk Date: 03.09.2010
 */
public class TagCloudTest {
  TagCloud tc;
  Cloud c;
  @Before
  public void init(){
    tc = new TagCloud();
    c = tc.getCloud();
  }

  @Test
  public void testTagCloud(){
    tc.populateCloud(c);
    tc.orderCloud(c);
    File dir = new File("opencloud");
    if (!dir.exists()){
      dir.mkdir();
    }
    File file = new File(dir,"cloud.html");
    try {
      file.createNewFile();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    tc.serializeCloud(c,file);

    Cloud c2 = tc.deserializeCloud(file);
    c2.getWordPattern();
  }

  @After
  public void terminate(){
    tc = null;
    c = null;
  }

}