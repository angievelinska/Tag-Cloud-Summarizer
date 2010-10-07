package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.matrix.Matrix;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * User: avelinsk
 * Date: 17.09.2010
 * Time: 13:10:56
 *
 * TODO: refactor - get classes from LSA.java
 */
public class SVDImpl{

  public void getMatrix(SemanticSpace sspace){
    //Matrix matrix = new
  }
  
  public void decompose(Matrix matrix, File file){

  }

  public static void normalize(){
    // normalize the initial term-document matrix
  }

  public double getSimilarity(Vector vector){
    double sim = 0.0;
    
    return sim;
  }

  public Map<String,Double> getWords(Matrix svd){
    Map<String,Double> words = new HashMap<String,Double>();

    return words;
  }


}
