package docMachine.tagcloud;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

/**
 * User: avelinsk Date: 01.09.2010
 */
public class TagCloud {
  public void createCloud(){
    Cloud cloud = new Cloud();
    cloud.setMaxWeight(38.0);
  }

  public void populateCloud(Cloud cloud){
    Tag tag = new Tag("CoreMedia", "https://www.coremedia.com/");
    cloud.addTag(tag);
  }
}
