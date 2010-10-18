package docMachine.cluster;

import java.util.*;

/**
 * User: avelinsk
 * Date: 18.10.2010
 * Time: 00:04:14
 */
class WCCAlgorithm {
  Set<String> words;

  /**
   * @param C is a clustering
   * @param l specifies how many terms make up a label
   * @param k specifies how ofter the same word may occur in the label of different clusters
   */
  public void weightedCentroidCovering(List<Cluster> C, int l, int k){
    Vector T = null;
    
    for(Cluster c : C){
      c.labelCluster("");
     }

    for(String word : words){
      for (int i = 1; i<k; i++){
        Cluster c = getIthCluster(C, word, i);
        
        //TODO: add to vector the triplet: word, cluster, term frequency
        T.add(new HashMap<String, <Cluster,Double>>());

      }
    }

    //TODO: sort T descending with respect to term frequencies

  }

  // TODO: finish implementation
  private Cluster getIthCluster(List<Cluster> C, String word, int i){
    Map<Cluster,Double> wordFreq = new HashMap<Cluster,Double>();

    for (Cluster c : C){
      wordFreq.put(c,c.getTermFrequency(word));
    }

    // TODO: sort wordFreq based on term frequency in descending order

    // TODO: return the cluster with i-th  highest frequency of word
    return C.get(0);
  }

  private void sortT(Vector T){
    
  }
}
