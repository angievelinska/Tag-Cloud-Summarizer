package edu.tuhh.tagcloud.cloud;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import java.util.List;

/**
 * @author avelinsk
 */
public class TagCloud {
  public Cloud cloud;
  public List<Tag> tags;

  public TagCloud(String text){
    cloud = new Cloud();
    cloud.setMaxTagsToDisplay(30);
    cloud.setMaxWeight(4.0);
    cloud.setDefaultLink("https://documentation.coremedia.com/");
    cloud.addText(text);
  }

  public void setCloud(Cloud cloud){
    this.cloud = cloud;
  }

  public Cloud getCloud(){
    return cloud;
  }

  public List<Tag> getTags(){
    return tags;
  }
}
