package edu.tuhh.tagcloudsummarizer.identify;

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
public class TagsIdentification {
  List<Tuple> topOccurrences;

  /**
   * @param tags - the set of terms in a query result
   * @param l - how many terms make up the label for one document
   * @param k - how often the same word may occur in the label of different documents
   */
  public void identifyTags(Set<Document> docs, Set<Tag> tags, int l, int k){

    topOccurrences = new ArrayList<Tuple>();

    for(Tag tag : tags){

     /** for each term, get its k most frequent occurrences in documents 
      * and save them in list topOccurrences
      */
      List<Tuple> tuples = kMostFrequent(docs, tag, k);

      for (Tuple tuple:tuples){
          topOccurrences.add(tuple);
      }
    }

    sortOccurences();

    for(int labelcount = 1; labelcount < l; labelcount ++){
      // holds number of terms assigned as labels
      int assigned = 0;
      // holds number of tuples
      int j = 1;

      Iterator iter = topOccurrences.iterator();
      while((assigned < docs.size()) && (j <= topOccurrences.size()) && iter.hasNext()){
        Tuple tj = (Tuple) iter.next();

        int docLabels = tj.getDocumentId();

        // check the labels for each document in docs
        // if (labelsize < l) pop a tuple from topOccurrences and assign the Term as label to the document 
      }
    }

  }


  private List<Tuple> kMostFrequent(Set<Document> docs, Tag tag, int k){

    Map<Double, Document> topDocs = getTopK(tag, docs, k);
    List<Tuple> tuples = new ArrayList<Tuple>();

    for (Map.Entry<Double, Document> entry : topDocs.entrySet()){
      Double tf = entry.getKey();
      Document doc = entry.getValue();
      Tuple tuple = new Tuple(doc, tag, tf);
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

    // sort the documents in descending order based on term occurrences
    documents = sortMap(documents);

    Map<Double,Document> result = new HashMap<Double,Document>();
    int i=0;
    for (Map.Entry entry : documents.entrySet()){
      i++;
      if (i==k){
        return result;
      }
      result.put((Double)entry.getKey(), (Document)entry.getValue());
    }

    return result;
  }

  /**
   * Sort a Map in reverse order by its keys.
   *
   * @param docs
   * @return
   */
  public Map<Double, Document> sortMap(Map<Double, Document> docs){
    List documents = new LinkedList(docs.entrySet());
    Collections.sort(documents, new Comparator(){
      public int compare(Object o1, Object o2){
        return ((Double)((Map.Entry) o2).getKey()).compareTo ((Double)((Map.Entry)o1).getKey());
      }
    });

    Map result = new LinkedHashMap();
    for (Iterator it = documents.iterator(); it.hasNext(); ){
      Map.Entry entry = (Map.Entry) it.next();
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

  private void sortOccurences(){
    sort(topOccurrences);
  }

  public void sort(List<Tuple> tuples){
    Collections.sort(tuples, Collections.reverseOrder());
  }


}
