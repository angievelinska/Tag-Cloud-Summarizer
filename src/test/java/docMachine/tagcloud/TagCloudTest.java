package docMachine.tagcloud;

import org.junit.Test;
import org.mcavallo.opencloud.Cloud;

import java.io.IOException;

/**
 * User: avelinsk Date: 03.09.2010
 */
public class TagCloudTest {
  TagCloud tc;
  Cloud c;
  @Test
  public void init(){
    tc = new TagCloud();
    c = tc.createCloud();
  }

  @Test
  public void testTagCloud(){
    tc.populateCloud(c);
    tc.orderCloud(c);
    try {
      tc.serializeCloud(c, "opencloud\\cloud.txt");
    } catch (IOException e){
      e.printStackTrace();
    }

    try{
      Cloud c2 = tc.deSerializeCloud("opencloud\\cloud.txt");
      c2.getWordPattern();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    } catch (IOException ex){
      ex.printStackTrace();
    }
  }

  @Test
  public void terminate(){
    tc = null;
    c = null;
  }

}
