package edu.tuhh.summarizer.tagcloud;

import edu.tuhh.summarizer.common.PropertiesLoader;
import org.apache.log4j.Logger;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;
import org.mcavallo.opencloud.filters.DictionaryFilter;
import org.mcavallo.opencloud.filters.Filter;

import java.io.*;
import java.util.*;

/**
 * @author avelinsk
 */
public class TagCloud {
  private Cloud cloud;
  private DictionaryFilter blacklist;
  private Properties props;
  private static Logger log = Logger.getLogger(TagCloud.class);

  public TagCloud() {
    cloud = new Cloud();
  }


  protected TagCloud(double weight, int maxTags) {
    props = new PropertiesLoader().loadProperties();
    String DEFAULT_LINK = props.getProperty("DEFAULT_LINK");
    String STOPWORDS = props.getProperty("STOPWORDS");
    cloud = new Cloud();
    cloud.setMaxWeight(weight);
    cloud.setMaxTagsToDisplay(maxTags);
    cloud.addInputFilter(getStopWords(STOPWORDS));
    cloud.addOutputFilter(getStopWords(STOPWORDS));
    cloud.setDefaultLink(DEFAULT_LINK);
  }

  protected Filter<Tag> getStopWords(String STOPWORDS) {
    Filter<Tag> stopwords = null;
    try {
      FileReader reader = new FileReader(new File(STOPWORDS));
      stopwords = new DictionaryFilter(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stopwords;
  }

  public Cloud generateCloud(double maxWeight, int maxTags, Map<String, Double> tags, double threshold) {
    TagCloud tc = new TagCloud(maxWeight, maxTags);
    tc.populateCloud(tags);
    tc.orderCloud(threshold);
    return tc.cloud;
  }

  public Cloud generateCloud(double maxWeight, int maxTags, List<String> words, double threshold) {
    TagCloud tc = new TagCloud(maxWeight, maxTags);
    tc.populateCloud(words);
    tc.orderCloud(threshold);
    return tc.cloud;
  }


  private void populateCloud(List<String> words) {
    //List<Tag> tags = new ArrayList<Tag>();
    for (String t : words) {
      Tag tag = new Tag(t);
      cloud.addTag(tag);
    }
    // cloud.addTags(tags);
  }

  @SuppressWarnings("unchecked")
  private void populateCloud(Map<String, Double> tags) {
    double weight;
    for (Object o : tags.entrySet()) {
      Map.Entry<String, Double> entry = (Map.Entry<String, Double>) o;
      String word = entry.getKey();
      weight = entry.getValue();
      Tag tag = new Tag(word, weight);
      cloud.addTag(tag);
    }
  }


  protected void orderCloud(double threshold) {
    cloud.tags(new Tag.ScoreComparatorDesc());
    cloud.setThreshold(threshold);
  }


  protected void serializeCloud(File file) {
    FileOutputStream fos;
    ObjectOutputStream oos = null;
    try {
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

  public Cloud getExampleCloud() {
    File input = new File("C:/tmp/input.dat");
    StringBuilder sb = new StringBuilder();
    Scanner scanner;

    try {
      scanner = new Scanner(input);
      while (scanner.hasNext()) {
        sb.append(scanner.nextLine());

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    log.info(sb.toString());

    String[] words = sb.toString().split("\\s+");
    List<String> tags = Arrays.asList(words);

    return generateCloud(40, 20, tags, 10.0);
  }

}
