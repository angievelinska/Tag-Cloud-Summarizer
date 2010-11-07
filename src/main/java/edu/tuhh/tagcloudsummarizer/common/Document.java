package edu.tuhh.tagcloudsummarizer.common;

import java.util.List;

/**
 * @author avelinsk
 */
public interface Document {

  public int getId();

  public void labelDocument(List<Tag> tags);

}
