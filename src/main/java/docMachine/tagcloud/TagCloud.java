package docMachine.tagcloud;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

/**
 * User: avelinsk
 * Date: 01.09.2010
 */
public class TagCloud {
  public Cloud createCloud(){
    Cloud cloud = new Cloud();
    cloud.setMaxWeight(38.0);
    return cloud;
  }

  public void populateCloud(Cloud cloud){
    Tag tag = new Tag("CoreMedia", "https://www.coremedia.com/", 3.5);
    cloud.addTag(tag);
  }

  public void orderCloud(Cloud cloud){
    cloud.tags(new Tag.ScoreComparatorDesc());
    cloud.setThreshold(2.0);
  }

  public static void main(String[] args){
    TagCloud tc = new TagCloud();
    Cloud c = tc.createCloud();
    tc.populateCloud(c);
    tc.orderCloud(c);
  }
}
