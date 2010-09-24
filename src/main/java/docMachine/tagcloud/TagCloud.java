package docMachine.tagcloud;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * User: avelinsk
 * Date: 01.09.2010
 */
public class TagCloud {
  private Cloud cloud;

  protected TagCloud(){
    cloud = new Cloud();
  }


  protected TagCloud(double weight, int maxTags){
    cloud = new Cloud();
    cloud.setMaxWeight(weight);
    cloud.setMaxTagsToDisplay(maxTags);
  }
  

  public Cloud generateCloud(double maxWeight, int maxTags, Map tags, double threshold){
    new TagCloud(maxWeight,maxTags);
    populateCloud(tags);
    orderCloud(threshold);
    return cloud;
  }


  private void populateCloud(Map<Double,String> tags){
    String word = "";
    for (Object o : tags.entrySet()) {
      double weight = (Double) o;
      word = tags.get(weight);
      Tag tag = new Tag(word, weight);
      cloud.addTag(tag);
    }
  }


  protected void orderCloud(double threshold){
    cloud.tags(new Tag.ScoreComparatorDesc());
    cloud.setThreshold(threshold);
  }


  protected void serializeCloud(File file){
    FileOutputStream fos;
    ObjectOutputStream oos = null;
    try{
      fos = new FileOutputStream(file);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(cloud);
      oos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  

  protected Cloud deserializeCloud(File file) {
    Cloud cloud = null;
    FileInputStream fis;
    ObjectInputStream ois = null;
    try {
      fis = new FileInputStream(file);
      ois = new ObjectInputStream(fis);
      cloud = (Cloud) ois.readObject();
      ois.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return cloud;
  }

/*  protected void serializeXML(){
    XStream xstream = new XStream();
    String xml = xstream.toXML(cloud);
    Cloud c = (Cloud) xstream.fromXML(xml);
  }*/
}
