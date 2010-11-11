package edu.tuhh.tagcloudsummarizer.common;

import java.io.BufferedReader;

/**
 * @author avelinsk
 */
public interface Document {

  public int getId();

  public void addLabel(Tag tag);

  public int getLabelSize();

  /**
   * returns the BufferedReader for this document's text
   */
  public BufferedReader reader();

}
