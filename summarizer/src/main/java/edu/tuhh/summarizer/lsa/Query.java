package edu.tuhh.summarizer.lsa;

import edu.ucla.sspace.common.DocumentVectorBuilder;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.common.WordComparator;
import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import edu.ucla.sspace.util.MultiMap;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * @author avelinsk
 */
public class Query {
  private SemanticSpace sspace;
  private DoubleVector queryVector;
  private DocumentVectorBuilder docBuilder;
  private WordComparator wordCompare;

  public Query() {
    sspace = LSAUtils.getSSpace();
    docBuilder = new DocumentVectorBuilder(sspace);
    wordCompare = new WordComparator();
    queryVector = new DenseVector(sspace.getVectorLength());
  }

  /**
   * @param query
   * @return
   */
  protected DoubleVector getQueryAsVector(String query) {
    queryVector = docBuilder.buildVector(new BufferedReader(new StringReader(query)), queryVector);
    return queryVector;
  }

  /**
   * queries for documents query Sigma * V_t sspace
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
    for (int j = 0; j < i; j++) {
      DoubleVector doc = lsa_space.getDocumentVector(j);
      double similarity = Similarity.cosineSimilarity(doc, query);
    }
    return cosineSimilarity;
  }


  protected MultiMap getSimilarWords(SemanticSpace sspace, String word, int maxResult) {
    MultiMap results = wordCompare.getMostSimilar(word, sspace, maxResult, Similarity.SimType.COSINE);
    return results;
  }

  protected DoubleVector getDocument(SemanticSpace sspace, int idx) {
    return ((LatentSemanticAnalysis) sspace).getDocumentVector(idx);
  }

  protected DoubleVector getWord(SemanticSpace space, String word) {
    return (DoubleVector) ((LatentSemanticAnalysis) sspace).getVector(word);
  }

}
