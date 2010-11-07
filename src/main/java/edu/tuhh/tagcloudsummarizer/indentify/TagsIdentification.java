package edu.tuhh.tagcloudsummarizer.indentify;

import edu.tuhh.tagcloudsummarizer.common.Document;
import edu.tuhh.tagcloudsummarizer.common.Tag;
import edu.ucla.sspace.vector.Vector;

import java.util.*;

/**
 * Algorithm for terms identification based on frequency of occurrence -
 * the terms that occur most frequently are nominated as document labels
 * for a set of documents returned from a search query.
 *
 * @author avelinsk
 */
class TagsIdentification {
  List<Tuple> T;

  /**
   * @param tags - the set of terms in a query result
   * @param l - how many terms make up the label for one document
   * @param k - how often the same word may occur in the label of different documents
   */
  public void identifyTags(Set<Document> docs, Set<Tag> tags, int l, int k){

    for(Tag tag : tags){

     /** for each term, get its k most frequent occurrences in documents 
      * and save them in list T
      */
      List<Tuple> tuples = kMostFrequent(docs, tag, k);

      for (Tuple tuple:tuples){
          T.add(tuple);
      }
    }

  }


  private List<Tuple> kMostFrequent(Set<Document> docs, Tag tag, int k){

    Map<Double, Document> topDocs = getTopK(tag, docs, k);
    List<Tuple> tuples = new ArrayList<Tuple>();

    for (Map.Entry<Double, Document> entry : topDocs.entrySet()){
      Double tf = entry.getKey();
      Document doc = entry.getValue();
      Tuple tuple = new Tuple(doc, tag, tf.doubleValue());
      tuples.add(tuple);
    }

    return tuples;
  }

  /**
   * Returns the k documents in which a term occurs most frequently.
   *
   * @param tag
   * @param docs
   * @param k
   * @return
   */
  private Map<Double, Document> getTopK(Tag tag, Set<Document> docs, int k) {

    Map<Double, Document> documents = new HashMap<Double, Document>();

    for(Document doc : docs){
      int idx = doc.getId();
      Vector frequencies = tag.getFrequency();
      Double frequency = (Double) frequencies.getValue(idx);
      documents.put(frequency, doc);
    }

    return topDocs;
  }


  public void sort(List<Tuple> tuples){
    Collections.sort(tuples, Collections.reverseOrder());
  }

  public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
    List<T> list = new ArrayList<T>(c);
    java.util.Collections.sort(list, Collections.reverseOrder());
    return list;
  }

  private void sort(){
    sort(T);
  }

}
