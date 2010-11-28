package edu.tuhh.summarizer.search;

import org.apache.lucene.document.Document;

import java.util.List;

/**
 * @author avelinsk
 */
interface Searcher {

  public List<Document> search(String query);

}
