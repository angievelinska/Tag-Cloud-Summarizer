package edu.tuhh.summarizer.common;

import edu.ucla.sspace.vector.Vector;

import java.net.URI;

/**
 * @author avelinsk
 */
public interface Tag {

  public URI getUri();

  public String getWord();

  public Vector getFrequency();

}
