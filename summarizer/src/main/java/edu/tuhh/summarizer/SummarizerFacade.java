package edu.tuhh.summarizer;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.tuhh.summarizer.lsa.Query;
import edu.tuhh.summarizer.search.Search;
import edu.tuhh.summarizer.search.Searcher;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import edu.ucla.sspace.util.MultiMap;
import org.apache.lucene.document.Document;
import org.mcavallo.opencloud.Tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class SummarizerFacade {
  protected Query query;
  protected Matrix docsMatrix;
  protected SemanticSpace sspaceTerms;
  protected Searcher searcher;
  protected MultiMap<Double, String> results;
  private Properties props;


  public SummarizerFacade() {
    try {
      props = PropertiesLoader.loadProperties();
      String SSPACE_TERMS = props.getProperty("SSPACE_TERMS");
      String DOCS_MATRIX = props.getProperty("DOCS_MATRIX");
      query = new Query();
      sspaceTerms = SemanticSpaceIO.load(new File(SSPACE_TERMS));
      searcher = new Search();
      docsMatrix = MatrixIO.readMatrix(new File(DOCS_MATRIX), MatrixIO.Format.SVDLIBC_DENSE_TEXT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Document> getLSASearchResults(String q) {
    List<Query.SearchResult> documents = query.searchDocSpace(q);
    List<Integer> ids = new ArrayList<Integer>();
    for(Query.SearchResult result : documents){
      ids.add(result.index);
    }
    return searcher.getDocsByIds(ids);
  }

  public List<Tag> getTags(String q, int maxResults) {
    List<Query.SearchResult> results =  query.searchTermSpace(q, maxResults);
    String link = props.getProperty("DEFAULT_LINK");
    List<Tag> tags = new ArrayList<Tag>();
    for (Query.SearchResult result : results){
      Tag tag = new Tag(result.word, link, result.score);
      tag.setWeight(result.score);
      tags.add(tag);
    }
    return tags;
  }

}


