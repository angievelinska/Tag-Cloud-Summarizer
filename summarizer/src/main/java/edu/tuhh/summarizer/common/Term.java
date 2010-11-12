package edu.tuhh.summarizer.common;

import edu.ucla.sspace.vector.Vector;

/**
 * Representation of a term from the term-document matrix
 * constructed in LSA.
 * 
 * @author avelinsk
 */
abstract class Term {

  protected String word;

  protected Vector termFrequency;

  public String getWord(){
    return word;
  }

  protected Vector getFrequency(){
    return termFrequency;
  }

}

