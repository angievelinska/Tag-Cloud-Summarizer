package edu.tuhh.tagcloudsummarizer.common;

/**
 * @author avelinsk
 */
public interface Document {

  public int getId();

  public void addLabel(Tag tag);

  public int getLabelSize();

}
