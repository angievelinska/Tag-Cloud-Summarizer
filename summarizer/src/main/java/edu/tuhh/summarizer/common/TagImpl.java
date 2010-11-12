package edu.tuhh.summarizer.common;

import edu.ucla.sspace.vector.Vector;

import java.net.URI;
import java.util.Set;

/**
 * @author avelinsk
 */
public class TagImpl extends Term implements Tag {
  
  Tag tag;
  URI uri;
  Set<Document> documents;

  public TagImpl(){
    tag = new TagImpl();
    
  }

  public URI getUri(){
    return uri;
  }

  @Override
  public Vector getFrequency(){
    return termFrequency;
  }

}
