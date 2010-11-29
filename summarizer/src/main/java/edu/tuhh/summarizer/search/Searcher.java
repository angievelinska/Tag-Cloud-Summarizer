package edu.tuhh.summarizer.search;

import org.apache.lucene.document.Document;

import java.util.List;

/**
 * @author avelinsk
 */
public interface Searcher {

  public List<Document> doLuceneSearch(String query);

  public List<Document> getDocsByIds(List<Integer> docIds);

}
