package docMachine.tagcloud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import java.io.*;
import java.util.*;

/**
 * @author avelinsk
 */
public class TagCloud {
  private Cloud cloud;
  private static final Log log = LogFactory.getLog(TagCloud.class);

  public TagCloud(){
    cloud = new Cloud();
  }


  protected TagCloud(double weight, int maxTags){
    super();
    cloud = new Cloud();
    cloud.setMaxWeight(weight);
    cloud.setMaxTagsToDisplay(maxTags);
  }
  

  public Cloud generateCloud(double maxWeight, int maxTags, Map<String,Double> tags, double threshold){
    new TagCloud(maxWeight,maxTags);
    populateCloud(tags);
    orderCloud(threshold);
    return cloud;
  }

  public Cloud generateCloud(double maxWeight, int maxTags, List<String> words, double threshold){
    TagCloud tc = new TagCloud(maxWeight,maxTags);
    populateCloud(words);
    orderCloud(threshold);
    return cloud;
  }


  private void populateCloud(List<String> words){
     //List<Tag> tags = new ArrayList<Tag>();
     for (String t : words){
       Tag tag = new Tag(t);
       cloud.addTag(tag);
     }
   // cloud.addTags(tags);
  }

  @SuppressWarnings("unchecked")
  private void populateCloud(Map<String, Double> tags){
    double weight;
    for (Object o : tags.entrySet()) {
      Map.Entry<String,Double> entry = (Map.Entry<String,Double>) o;
      String word = entry.getKey();
      weight = entry.getValue();
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
  }

  public Cloud getTagCloud(){
    Map<String, Double> tags = new HashMap<String, Double>();
    tags.put("CoreMedia", (double) 30);
    tags.put("content", (double) 20);
    tags.put("server", 26.5);
    return generateCloud(40, 20, tags, 10.0);
  }
*/

  public Cloud getTagCloud(){
    File input = new File("input/input1.txt");
    log.info("file exists");
    StringBuilder sb = new StringBuilder();
    Scanner scanner;

    try{
      scanner = new Scanner(input);
      while(scanner.hasNext()){
        sb.append(scanner.nextLine());

      }
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }

    log.info(sb.toString());
    
    String[] words = sb.toString().split("\\s+");
    List<String> tags = Arrays.asList(words);

    return generateCloud(40,20,tags,10.0);
  }

}
