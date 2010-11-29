package edu.tuhh.tagcloud;

import edu.tuhh.summarizer.SummarizerFacade;
import org.apache.lucene.document.Document;
import org.mcavallo.opencloud.Tag;

import java.util.List;

/**
 * @author avelinsk
 */
public class Summarizer {
  private SummarizerFacade facade;

  protected List<Document> getSearchResults(String query) {
    return facade.getLSASearchResults(query);
  }

  protected List<Tag> getTags(String query, int maxResults) {
    return facade.getTags(query, maxResults);
  }
}
