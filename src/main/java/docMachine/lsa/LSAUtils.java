package docMachine.lsa;

import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.DiagonalMatrix;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import org.apache.commons.math.MathRuntimeException;
import org.apache.commons.math.linear.*;

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

  /**
   * uses LU decomposition to compute the inverse matrix
   *
   */
  public static Matrix getMatrixInverse(Matrix initial){
    RealMatrix matrix = (RealMatrix) initial;
    RealMatrix matrixInverse = new LUDecompositionImpl(matrix).getSolver().getInverse();
    return (Matrix) matrixInverse;
  }

    public static float[][] Inverse(float[][] a){
		// Formula used to Calculate Inverse:
		// inv(A) = 1/det(A) * adj(A)

		int tms = a.length;

		float m[][] = new float[tms][tms];
		float mm[][] = Adjoint(a);

                float dd = 0;
                try {
		float det = Determinant(a);

		if (det == 0) {
			throw new Exception ( "Determinant Equals 0, Not Invertible.");
                
                } else {
			dd = 1 / det;
		}
                }catch(Exception e){
                  e.printStackTrace();
                }
		for (int i = 0; i < tms; i++)
			for (int j = 0; j < tms; j++) {
				m[i][j] = dd * mm[i][j];
			}

		return m;
	}
  public static float[][] Adjoint(float[][] a){
          int tms = a.length;

          float m[][] = new float[tms][tms];

          int ii, jj, ia, ja;
          float det;

          for (int i = 0; i < tms; i++)
                  for (int j = 0; j < tms; j++) {
                          ia = ja = 0;

                          float ap[][] = new float[tms - 1][tms - 1];

                          for (ii = 0; ii < tms; ii++) {
                                  for (jj = 0; jj < tms; jj++) {

                                          if ((ii != i) && (jj != j)) {
                                                  ap[ia][ja] = a[ii][jj];
                                                  ja++;
                                          }

                                  }
                                  if ((ii != i) && (jj != j)) {
                                          ia++;
                                  }
                                  ja = 0;
                          }

                          det = Determinant(ap);
                          m[i][j] = (float) Math.pow(-1, i + j) * det;
                  }

          m = Transpose(m);

          return m;
  }

  public static float Determinant(float[][] matrix) {

		int tms = matrix.length;

		float det = 1;

		matrix = UpperTriangle(matrix);

		for (int i = 0; i < tms; i++) {
			det = det * matrix[i][i];
		} // multiply down diagonal

		det = det * iDF; // adjust w/ determinant factor

		return det;
	}

  public static float[][] Transpose(float[][] a) {

          float m[][] = new float[a[0].length][a.length];

          for (int i = 0; i < a.length; i++)
                  for (int j = 0; j < a[i].length; j++)
                          m[j][i] = a[i][j];
          return m;
  }

  public static float[][] UpperTriangle(float[][] m) {

          float f1 = 0;
          float temp = 0;
          int tms = m.length; // get This Matrix Size (could be smaller than
                                                  // global)
          int v = 1;

          iDF = 1;

          for (int col = 0; col < tms - 1; col++) {
                  for (int row = col + 1; row < tms; row++) {
                          v = 1;

                          outahere: while (m[col][col] == 0) // check if 0 in diagonal
                          { // if so switch until not
                                  if (col + v >= tms) // check if switched all rows
                                  {
                                          iDF = 0;
                                          break outahere;
                                  } else {
                                          for (int c = 0; c < tms; c++) {
                                                  temp = m[col][c];
                                                  m[col][c] = m[col + v][c]; // switch rows
                                                  m[col + v][c] = temp;
                                          }
                                          v++; // count row switchs
                                          iDF = iDF * -1; // each switch changes determinant
                                                                          // factor
                                  }
                          }

                          if (m[col][col] != 0) {

                                  try {
                                          f1 = (-1) * m[row][col] / m[col][col];
                                          for (int i = col; i < tms; i++) {
                                                  m[row][i] = f1 * m[col][i] + m[row][i];
                                          }
                                  } catch (Exception e) {
                                          System.out.println("Still Here!!!");
                                  }

                          }

                  }
          }

          return m;
  }

  /**
   * Generates an identity matrix.
   * It is used in calculating the inverse of a matrix.
   *  
   * @param dimensions
   * @return
   */
  public static Matrix getIdentityMatrix(int dimensions){
    Matrix m = new DiagonalMatrix(dimensions);
    for (int i=0; i<dimensions; i++){
      m.set(i, i, 1.0);
    }
    return m;
  }

}
