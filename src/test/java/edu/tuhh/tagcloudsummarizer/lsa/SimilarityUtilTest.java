package edu.tuhh.tagcloudsummarizer.lsa;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author avelinsk
 */
public class SimilarityUtilTest {

  @Test
  public void testFNorm(){
    double[]a={0.1,0.2,9,0.0};
    //System.out.println("F norm is: "+SimilarityUtil.fNorm(a));
    assertEquals(SimilarityUtil.fNorm(a),9.002777349, 0.000001);

    double[]b={0, 9, 8.6543, 4, 0.98707, 2, 3, 0.1, 0.011223};
    //System.out.println("F norm is: "+SimilarityUtil.fNorm(b));
    assertEquals(SimilarityUtil.fNorm(b), 13.63383078, 0.000001);
  }

  @Test
  public void testDotProduct(){
    double[]a = {0, 0.3, 0.34506, 4};
    double[]b = {7, 0.8, 9};
    double[]c = {3.0213, 4, 0, 0.0};

    assertEquals(SimilarityUtil.getDotProduct(a,b), 0.0, 0.000001);
    assertEquals(SimilarityUtil.getDotProduct(a,c), 1.2, 0.01);
    //System.out.println(SimilarityUtil.getDotProduct(a,c));
  }

}
