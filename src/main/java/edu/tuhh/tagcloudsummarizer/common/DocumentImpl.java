package edu.tuhh.tagcloudsummarizer.common;

import java.util.List;

/**
 * @author avelinsk
 */
public class DocumentImpl implements Document{
  /**
   *  The id is the column number
   *  from term-document matrix constructed in LSA
   */
  private int id;

  private Document document;

  private List<Tag> label;

  public DocumentImpl(){
   document = new DocumentImpl();
  }

  public int getId(){
    return id;
  }

   public void labelDocument(List<Tag> tags){
    label = tags;
  }

}

