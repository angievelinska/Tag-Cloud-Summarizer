package edu.tuhh.summarizer.lsa;

import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.vector.DoubleVector;
import org.apache.log4j.Logger;

/**
 * @author avelinsk
 */
public class SimilarityUtil {
  //private static final Log log = LogFactory.getLog(SimilarityUtil.class);
  private static Logger log = Logger.getLogger(SimilarityUtil.class);

  public static double getSimilarity(DoubleVector vect1, DoubleVector vect2){
    return Similarity.getSimilarity(Similarity.SimType.COSINE, vect1, vect2);
  }

  /**
   * Returns the cosine similarity between vectors a and b:
   * cosim(a,b) = dotProduct / frobeniusNorm
   *
   * cosim(a,b) = (a . b)/(||a||.||b||)
   */
  public static double getSimilarityBetweenVectors(double[] a, double[] b){
    double dotProduct, fNorm;
    dotProduct =  SimilarityUtil.getDotProduct(a,b);
    fNorm = getFrobeniusNorm(a,b);

    return dotProduct/fNorm;
  }

  /**
   * Returns the dot product of two vectors:
   *
   * a.b = a1*b1 + a2*b2 + ... + am*bm
   */
  public static double getDotProduct(double[] a, double[] b){
    int length = a.length;
    double sum = 0.0;
    if (length != b.length){
      log.warn("Vectors have different lenghts; Returning dot product 0.0");
      return sum;
    }
    
    for(int i=0; i<length; i++){
      sum += a[i]*b[i];
    }
    return sum;
  }


  /**
   * Returns the Frobenius norm (Eucledian distance) between two vectors:
   *
   * ||a||.||b|| = sqrt(a1^2 + a2^2 +...+ am^2).sqrt(b1^2 + b2^2 + .. + bm^2)
   */
  public static double getFrobeniusNorm(double[] a, double[] b){
    return fNorm(a)*fNorm(b);
  }

  /**
   * Returns the Frobenius norm of a vector:
   *
   * ||a|| = sqrt(a1^2 + a2^2 + .. + an^2)
   */
  public static double fNorm(double[] a){
    double sum = 0;
    for (int i=0; i<a.length; i++){
      sum += a[i] * a[i];
    }

    return Math.sqrt(sum);
  }


}