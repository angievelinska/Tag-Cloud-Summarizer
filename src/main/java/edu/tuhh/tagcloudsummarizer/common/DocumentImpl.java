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

  private List<Tag> label;

  public DocumentImpl(int id){
    super();
    setId(id);
  }

  private void setId(int docId){
    id = docId;
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
}

