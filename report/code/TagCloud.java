public class TagCloud {
  private Cloud cloud;
  private DictionaryFilter blacklist;
  private Properties props;
  private static Logger log = Logger.getLogger(TagCloud.class);

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
  
  /** ... */
}
