package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.vector.DoubleVector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: avelinsk
 * Date: 12.10.2010
 * Time: 21:19:27
 */
public class SimilarityUtil {
  protected Set<DoubleVector> getSimilarVectors(SemanticSpace sspace, DoubleVector vector, int results){
     return new HashSet<DoubleVector>();
  }

  protected double getSimilarityForVector(SemanticSpace sspace, DoubleVector vector){
    Set<String> words = sspace.getWords();
    double sum=0.0;
    Iterator<String> i = words.iterator();
    while(i.hasNext()){
        String word = i.next();
        sum += Similarity.getSimilarity(Similarity.SimType.COSINE,
                                        sspace.getVector(word),
                                        vector);
    }
    return sum/words.size();
  }

}
