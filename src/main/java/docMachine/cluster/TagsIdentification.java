package docMachine.cluster;

import docMachine.common.Document;
import docMachine.common.Tag;

import java.util.*;

/**
 * Algorithm for tags identification based on frequency of occurence -
 * the tags that occur most frequently are nominated for the tag cloud.
 *
 * @author avelinsk
 */
class TagsIdentification {
  Set<Tag> words;

  /**
   * @param D is a document set
   * @param l specifies how many terms make up a label
   * @param k specifies how ofter the same word may occur in the label of different documents
   */
  public void identifyTags(List<Document> D, int l, int k){
    Vector T = null;
    
    for(Document d : D){
      d.labelDocument("");
     }

    for(Tag word : words){
      for (int i = 1; i<k; i++){
        Document c = getIthDocument(D, word, i);
        
        //TODO: add to vector the triplet: word, document, term frequency
       // T.add(new HashMap<String, <Document,Double>>());

      }
    }
    //TODO: sort T descending with respect to term frequencies

  }

  // TODO: finish implementation
  private Document getIthDocument(List<Document> D, String word, int i){
    Map<Document,Double> wordFreq = new HashMap<Document,Double>();

    for (Document d : D){
      wordFreq.put(d, d.getTermFrequency(word));
    }

    // TODO: sort wordFreq based on term frequency in descending order

    // TODO: return the cluster with i-th  highest frequency of word
    return D.get(0);
  }

  private void sortT(Vector T){
    
  }

}
