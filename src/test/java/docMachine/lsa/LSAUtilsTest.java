package docMachine.lsa;

import org.junit.Before;
import org.junit.Test;

/**
 * User: avelinsk
 * Date: 25.10.2010
 * Time: 18:08:49
 */
public class LSAUtilsTest {
  public float[][]testMatrix =  {{2.3f, 0, 4.2f},
                                 {0, 1.3f, 2.2f},
                                 {3.8f, 0, 0.5f}};

  public float[][] testM2 =  {{3,5,1},
                              {2,4,5},
                              {1,2,2}};

  @Test
  public void testGetMatrixInverse() {
     float [][] result = LSAUtils.Inverse(testMatrix);
     for (int i = 0; i < 3; i++){
       for (int j = 0; j < 3; j++){
         System.out.print(result[i][j]+" ");
       }
       System.out.println();
     }

     System.out.println();
    
     float [][] result1 = LSAUtils.Inverse(testMatrix);
     for (int i = 0; i < 3; i++){
       for (int j = 0; j < 3; j++){
         System.out.print(result1[i][j]+" ");
       }
       System.out.println();
     }
  }

}
