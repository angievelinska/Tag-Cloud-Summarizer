package docMachine.lsa;

import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test results are compared to results from BlueBit Online Matrix Calculator
 *
 *
 */
public class LSAUtilsTest {
  public double[][]testM1 =  {{4.648, 0.0, 0.0, 0.0},
                              {0.0, 1.196, 0.0, 0.0},
                              {0.0, 0.0, 0.966, 0.0},
                              {0.0, 0.0, 0.0, 0.731}};
  
  public double[][] inverseM1 =  {{0.215,0.0,0.0,0.0},
                                  {0.0,0.836,0.0,0.0},
                                  {0.0,0.0,1.035,0.0},
                                  {0.0,0.0,0.0,1.369}};

  @Test
  public void testGetMatrixInverse() {
    Matrix m = Matrices.create(4,4,true);
    for (int i=0; i<4; i++){
      m.set(i, i, testM1[i][i]);
    }

    Matrix m_inv = LSAUtils.getMatrixInverse(m);
    for (int j = 0; j<4; j++){
      for (int k = 0; k<4; k++){
        System.out.print(m.get(j, k));
        assertEquals(m_inv.get(j,k),inverseM1[j][k]);
      }
      System.out.println();
    }

  }

}
