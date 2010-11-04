package edu.tuhh.tagcloudsummarizer.common;

import java.net.URI;
import java.util.Set;

/**
 * @author avelinsk
 */
public class TagImpl implements Tag {
  
  Tag tag;
  URI uri;
  String word;
  double frequency;
  Set<Document> documents;

  public TagImpl(){
    tag = new TagImpl();
  }

  public double getTermFrequency(){
    return frequency;
  }

  public String getWord(){
    return word;
  }
}
