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
 * User: avelinsk
 * Date: 02.10.2010
 * Time: 17:27:26
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

  protected DoubleVector getQueryAsVector(String fquery){
    BufferedReader reader = null;
    
    try{
      File file_query = new File(fquery);
      if(!file_query.exists()){
        throw new FileNotFoundException("no query file found. exiting...");
      }
    } catch (FileNotFoundException e){
      e.printStackTrace();
    }

    try {
      reader = new BufferedReader(new FileReader(fquery));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    queryVector = docBuilder.buildVector(reader, queryVector);

    return queryVector;
  }

   public double computeCosineSimilarity(DoubleVector query, SemanticSpace sspace, int noOfResults) {
     LatentSemanticAnalysis lsa_space = (LatentSemanticAnalysis) sspace;
     double cosineSimilarity = 0.0;
     int i = sspace.getVectorLength();
     for (int j = 0; j < i; j++){
       DoubleVector doc = lsa_space.getDocumentVector(j);
       double similarity = Similarity.cosineSimilarity(doc, query);
       
     }

/*
        double original_cosineVal = cosineSimilarity(m.getRow(getIndexOfPair(pair1, matrix_row_map)), m.getRow(getIndexOfPair(pair2, matrix_row_map)));
        cosineVals += original_cosineVal;
        totalVals++;
        //System.err.println("orig cos: " + cosineVals);
        ArrayList<String> alternates1 = original_to_alternates.get(pair1);
        ArrayList<String> alternates2 = original_to_alternates.get(pair2);
        for (String a : alternates1) {
            for (String b : alternates2) {
                int a_index = getIndexOfPair(a, matrix_row_map);
                int b_index = getIndexOfPair(b, matrix_row_map);
                if(a_index != -1 && b_index != -1) {
                    double alternative_cosineVal = cosineSimilarity(m.getRow(a_index),m.getRow(b_index));
                    //System.err.println("adding cos: " + alternative_cosineVal);
                    if (alternative_cosineVal >= original_cosineVal) {
                        cosineVals += alternative_cosineVal;
                        totalVals++;
                    }
                }
            }
        }

        if (totalVals > 0) {
            return cosineVals/totalVals;
        } else {                                                   
            return 0.0;
        }*/


     return cosineSimilarity;
    }    


  protected DoubleVector getQueryMappedToSSpace(DoubleVector query){
    List<DoubleVector> q = new ArrayList<DoubleVector>();
    q.add(query);
    Matrix queryTransposed = Matrices.asMatrix(q);
    queryTransposed = Matrices.transpose(queryTransposed);
    Matrix queryByUMatrix = Matrices.multiply(queryTransposed,LSAUtils.getU());
    Matrix queryMapped = Matrices.multiply(queryByUMatrix, getSInversed());

    if (queryMapped.columns() > 1){

    }
    queryMapped.getColumnVector(0);
    queryMapped.getRowVector(0);

    return query;
  }
  
  private Matrix getSInversed(){
    Matrix SMatrix = LSAUtils.getS();
    // TODO: return the inverse matrix
    return Matrices.transpose(SMatrix);
  }

  protected MultiMap getSimilarWords(SemanticSpace sspace, String word, int maxResult){
    MultiMap results = wordCompare.getMostSimilar(word,sspace,maxResult, Similarity.SimType.COSINE);
    return results;
  }

  protected DoubleVector getDocument(String space, int idx ){
    LatentSemanticAnalysis sspace = null;

    try {
      sspace= (LatentSemanticAnalysis) SemanticSpaceIO.load(space);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sspace.getDocumentVector(idx);
  }

  protected DoubleVector getWord(String space, String word){
    SemanticSpace sspace = null;

    try{
      sspace = SemanticSpaceIO.load(space);
    } catch (IOException e){
      e.printStackTrace();
    }

    return (DoubleVector) sspace.getVector(word);

  }

}
