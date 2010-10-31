package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.DiagonalMatrix;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;

import java.io.File;
import java.io.IOException;

/**
 * User: avelinsk
 * Date: 25.10.2010
 * Time: 14:43:57
 */
public class LSAUtils {

  private static SemanticSpace sspace;

  private static Matrix U;

  private static Matrix V_t;

  private static Matrix S;

  private static int iDF = 0;
  /**
   * singularity threshold
   * */
  private static final double SINGULARITY_THRESHOLD = 10E-12;


  public static SemanticSpace getSSpace() {
    try{
      sspace = SemanticSpaceIO.load("sspace/LSA.sspace");
    } catch(IOException e){
      e.printStackTrace();
    }
    return sspace;
  }


  public static Matrix getU() {
    try{
      U = MatrixIO.readMatrix(new File("sspace","matrix_U.txt"), MatrixIO.Format.DENSE_TEXT);
    } catch(IOException e){
      e.printStackTrace();
    }
    return U;
  }


  public static Matrix getV_t() {
    try{
      V_t = MatrixIO.readMatrix(new File("sspace","matrix_Vt.txt"), MatrixIO.Format.DENSE_TEXT);
    } catch(IOException e){
      e.printStackTrace();
    }
    return V_t;
  }


  public static Matrix getS() {
    try{
      S = MatrixIO.readMatrix(new File("sspace","matrix_S.txt"), MatrixIO.Format.DENSE_TEXT);
    } catch(IOException e){
      e.printStackTrace();
    }
    return S;
  }


  public static Matrix getAInverse(){
    Matrix V_S_inv = Matrices.multiply(Matrices.transpose(getV_t()), getSInverse());
    Matrix A_inv = Matrices.multiply(V_S_inv, Matrices.transpose(getU()));
    return A_inv;
  }


  public static Matrix getMatrixInverse(Matrix m) {
    int rows = m.rows();
    int columns = m.columns();
    if (rows != columns){
      return Matrices.create(rows, rows, true);
    }
    Matrix m_inv = m;
    for (int i = 0; i<rows; i++){
      double oldVal = m_inv.get(i,i);
      double newVal = (oldVal==0.0 ? 0.0 : 1.0/oldVal);
      m_inv.set(i, i, newVal);
    }
    return m_inv;
  }

  public static Matrix getSInverse(){
    return getMatrixInverse(getS());
  }

  
   public double[] getSingularValues() {
     Matrix S = getS();
     int m = S.rows();
     double[][] singularMatrix = S.toDenseArray();
     double[] s = new double[m];
     for(int i = 0; i<m; i++){
       s[i] = singularMatrix[i][i];
     }
     return s;
   }




/*  **
   * Generates an identity matrix.
   * It is used in calculating the inverse of a matrix.
   *  
   * @param dimensions
   * @return
   **/
  public static Matrix getIdentityMatrix(int dimensions){
    Matrix m = new DiagonalMatrix(dimensions);
    for (int i=0; i<dimensions; i++){
      m.set(i, i, 1.0);
    }
    return m;
  }
}