package edu.tuhh.tagcloudsummarizer.identify;

import edu.tuhh.tagcloudsummarizer.common.Document;
import edu.tuhh.tagcloudsummarizer.common.Tag;

/**
 * Wrapper class that holds the association between a document, a term,
 * and the term occurrence in the document.
 *
 * @author avelinsk
 */

public class Tuple implements Comparable<Tuple> {

  private Document document;

  private Tag tag;

  private double termfreq;

  public Tuple(Document d, Tag t, double tf){
    document = d;
    tag = t;
    termfreq = tf;
  }

  public Document getDocument(){
    return document;
  }

  public Tag getTag(){
    return tag;
  }

  public double getTermFrequency(){
    return termfreq;
  }

  public int compareTo(Tuple t) {
    return Double.compare(getTermFrequency(),t.getTermFrequency());
  }
  
}
