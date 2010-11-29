package edu.tuhh.tagcloud;

import edu.tuhh.summarizer.SummarizerFacade;
import org.apache.lucene.document.Document;
import org.mcavallo.opencloud.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avelinsk
 */
public class Summarizer {
  private SummarizerFacade facade;

  public Summarizer(){
    facade = new SummarizerFacade();
  }

  public List<String> getSearchResults(String query) {
    List<Document> luceneResults = facade.getLSASearchResults(query);
    List<String> links = new ArrayList<String>();
    for(Document doc : luceneResults){
      String link = doc.get("URL");
      links.add(link);
    }
    return links;
  }

  public List<Tag> getTags(String query, int maxResults) {
    return facade.getTags(query, maxResults);
  }
}
