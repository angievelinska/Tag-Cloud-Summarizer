package edu.tuhh.tagcloudsummarizer.common;

import java.util.List;
import java.util.Set;

/**
 * @author avelinsk
 */
public class DocumentImpl implements Document{
  private int id;
  private Set<Tag> words;
  private Document document;
  private List<Tag> labels;

  public DocumentImpl(){
   Document document = new DocumentImpl();
  }

   public void labelDocument(List<Tag> labels){
    labels = labels;
  }

  /**
   * returns the term frequency of word in the document
   * tf =
   *
   * log/entropy =
   *
   * @param word
   * @return
   */
  public double getTermFrequency(Tag word){
    double termFreq = (double)getWordOccurence(word.getTermFrequency())/getDocumentSize();
    return termFreq;
  }

  private int getDocumentSize(){
    return words.size();
  }

  private int getWordOccurence(Tag word){
    int occurence = 0;
    for (Tag w : words){
      if (w.getWord().equalsIgnoreCase(word.getWord())){
        occurence++;
      }
    }

    return occurence;
  }
}

