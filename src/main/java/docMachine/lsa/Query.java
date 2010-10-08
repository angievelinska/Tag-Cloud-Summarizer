package docMachine.lsa;

import edu.ucla.sspace.common.DocumentVectorBuilder;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.common.WordComparator;
import edu.ucla.sspace.util.MultiMap;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * User: avelinsk
 * Date: 02.10.2010
 * Time: 17:27:26
 */
public class Query {
  private static final Log log = LogFactory.getLog(Query.class);

  private String query;

  private DoubleVector queryVector;

  private DocumentVectorBuilder docBuilder;

  private WordComparator wordCompare;


  public Query(SemanticSpace sspace){
    docBuilder = new DocumentVectorBuilder(sspace);
  /**
   * TODO: parameterize with higher number of threads
   *
   */
    wordCompare = new WordComparator();

    queryVector = new DenseVector(sspace.getVectorLength());
  }

  protected void setQuery(String query){
    query = query;
  }

  protected DoubleVector getDocumentVector(String query){
    BufferedReader reader = null;

    File file_query = new File(query);
    if(!file_query.exists()){
      log.info("no query file found. exiting...");

      try {
        throw new FileNotFoundException("no query file found. exiting...");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    try {
      reader = new BufferedReader(new FileReader(file_query));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    queryVector = docBuilder.buildVector(reader, queryVector);
    return queryVector;
  }

  
  protected MultiMap getCosineSimilarity(SemanticSpace sspace, String word, int maxResult){
    MultiMap results = wordCompare.getMostSimilar(word,sspace,maxResult, Similarity.SimType.COSINE);
    return results;
  }

}
