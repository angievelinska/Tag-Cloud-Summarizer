package docMachine.lsa;

import edu.ucla.sspace.common.*;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.util.MultiMap;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avelinsk
 */
public class Query {
  private static final Log log = LogFactory.getLog(Query.class);

  private String query;

  private SemanticSpace sspace;

  private DoubleVector queryVector;

  private DocumentVectorBuilder docBuilder;

  private WordComparator wordCompare;

  public Query(){
    sspace = LSAUtils.getSSpace();

    docBuilder = new DocumentVectorBuilder(sspace);
  /**
   * TODO: implement to run with more threads
   *
   */
    wordCompare = new WordComparator();

    queryVector = new DenseVector(sspace.getVectorLength());
  }

  protected void setQuery(String query){
    query = query;
  }

  /**
   * TODO: ? query vector is already represented as a pseudo - document vector ?
   * @param q_file
   * @return
   */
  protected DoubleVector getQueryAsVector(String q_file){
    File query = null;
    BufferedReader reader = null;
    try{
      query = new File(q_file);
      if(!query.exists()){
        throw new FileNotFoundException("No query file found. exiting...");
      }
    } catch (FileNotFoundException e){
      e.printStackTrace();
    }

    try {
      reader = new BufferedReader(new FileReader(query));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    queryVector = docBuilder.buildVector(reader, queryVector);

    return queryVector;
  }

  /**
   * TODO: ? should queries for documents query Sigma * V_t sspace
   *
   * @param query
   * @param sspace
   * @param noOfResults
   * @return
   */
   public double computeCosineSimilarity(DoubleVector query, SemanticSpace sspace, int noOfResults) {
     LatentSemanticAnalysis lsa_space = (LatentSemanticAnalysis) sspace;
     double cosineSimilarity = 0.0;
     int i = sspace.getVectorLength();
     for (int j = 0; j < i; j++){
       DoubleVector doc = lsa_space.getDocumentVector(j);
       double similarity = Similarity.cosineSimilarity(doc, query);
       
     }

     return cosineSimilarity;
    }    


  protected DoubleVector getQueryMappedToSSpace(DoubleVector query){
    List<DoubleVector> q = new ArrayList<DoubleVector>();
    q.add(query);
    Matrix m_query = Matrices.asMatrix(q);
    Matrix q_U = Matrices.multiply(m_query,LSAUtils.getU());
    Matrix q_vector = Matrices.multiply(q_U, LSAUtils.getSInverse());

    if (q_vector.columns() > 1){
      return query;
    }
    q_vector.getColumnVector(0);
    q_vector.getRowVector(0);

    return query;
  }
  
  protected MultiMap getSimilarWords(SemanticSpace sspace, String word, int maxResult){
    MultiMap results = wordCompare.getMostSimilar(word,sspace,maxResult, Similarity.SimType.COSINE);
    return results;
  }

  protected DoubleVector getDocument(SemanticSpace sspace, int idx ){
    return ((LatentSemanticAnalysis)sspace).getDocumentVector(idx);
  }

  protected DoubleVector getWord(SemanticSpace space, String word){
    return (DoubleVector) ((LatentSemanticAnalysis)sspace).getVector(word);
  }

}
