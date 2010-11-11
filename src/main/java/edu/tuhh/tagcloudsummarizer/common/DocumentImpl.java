package edu.tuhh.tagcloudsummarizer.common;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

/**
 * @author avelinsk
 */
public class DocumentImpl implements Document{
  /**
   *  The id is the column number
   *  from term-document matrix constructed in LSA
   */
  private final int id;

  private final BufferedReader reader;

  private List<Tag> label;

  public DocumentImpl(int docId, String text){
    id = docId;
    reader = new BufferedReader(new StringReader(text));
  }

  public int getId(){
    return id;
  }

   public void addLabel(Tag tag){
    label.add(tag);
  }

  public int getLabelSize(){
    return label.size();
  }

  public BufferedReader reader(){
    return reader;
  }
}

