package docMachine.tagcloud;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * User: avelinsk
 * Date: 01.09.2010
 */
public class TagCloud {
  public Cloud getCloud(){
    TagCloud tagCloud = new TagCloud();

    Cloud cloud = new Cloud();
    cloud.setMaxWeight(38.0);
    tagCloud.populateCloud(cloud);
    tagCloud.orderCloud(cloud);

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

  public void serializeCloud(Cloud cloud, String file) throws IOException {
    FileOutputStream fos = new FileOutputStream(file);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(cloud);
    oos.close();
  }

  public Cloud deSerializeCloud(String file) throws IOException, ClassNotFoundException{
    Cloud cloud;
    FileInputStream fis = new FileInputStream("file");
    ObjectInputStream ois = new ObjectInputStream(fis);
    cloud = (Cloud) ois.readObject();
    ois.close();
    return cloud;
  }

/*  public void serializeXML(){
    XStream xstream = new XStream();
    String xml = xstream.toXML(cloud);
    Cloud c = (Cloud) xstream.fromXML(xml);
  }*/
}
