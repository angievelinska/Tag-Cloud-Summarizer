package edu.tuhh.tagcloud.cloud;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import java.util.List;

/**
 * @author avelinsk
 */
public class TagCloud {

  public List<Tag> getTagCloud(String content, String command){
    String text;
    Cloud cloud = new Cloud();
    // Sets the number of tag to display in the tagCloud
    cloud.setMaxTagsToDisplay(30);

    // We want four different levels so set the maximum weight value to 4.0.
    cloud.setMaxWeight(4.0);

    // Sets the default url to assign to tag.
    // The format specifier %s will be substituted by the tag name
    cloud.setDefaultLink("https://documentation.coremedia.com/servlet/content/247402?language=en&include=false&version=5.2&book=%s");

    if (content != null) { text = content; }
        else { text = "";  }

    if (command != null) {
       if (command.equals("CLEAR")) { cloud.clear(); }
          else if (command.equals("SEARCH")) {
             cloud.addText(text);
          }
    }

    return cloud.tags();
  }
}
