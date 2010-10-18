package docMachine.cluster;

import edu.ucla.sspace.vector.Vector;

import java.util.Set;

/**
 * User: avelinsk
 * Date: 18.10.2010
 * Time: 00:29:40
 */
class Cluster {
  private Set<String> words;
  private Cluster  cluster;
  private String label;

  public void Cluster(){
   cluster = new Cluster();
  }

  protected void labelCluster(String label){
    label = label;
  }

  /**
   * returns the term frequency of word occurence in the cluster
   * @param word
   * @return
   */
  protected double getTermFrequency(String word){
    double termFreq = (double) getWordOccurence(word) / getClusterSize();
    return termFreq;
  }

  private int getClusterSize(){
    return words.size();
  }

  private int getWordOccurence(String word){
    int occurence = 0;
    for (String w : words){
      if (w.equalsIgnoreCase(word)){
        occurence++;
      }
    }

    return occurence;
  }
}

