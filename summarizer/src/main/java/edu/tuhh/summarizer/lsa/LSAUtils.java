package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.DiagonalMatrix;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class LSAUtils {
  /**
   * matrix Ak after SVD
   */
  private static Matrix A;

  private static Matrix U;

  private static Matrix V_t;

  private static Matrix S;

  private static SemanticSpace sspace;

  /**
   * singularity threshold
   */
  private static final double SINGULARITY_THRESHOLD = 10E-12;


  public static SemanticSpace getSSpace() {
    try {
      sspace = SemanticSpaceIO.load(getProperties().getProperty("SSPACE"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sspace;
  }


  public static Matrix getU() {
    try {
      U = MatrixIO.readMatrix(new File(getProperties().getProperty("SSPACE_DIR"),
              getProperties().getProperty("MATRIX_U")),
              MatrixIO.Format.DENSE_TEXT);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return U;
  }


  public static Matrix getV_t() {
    try {
      File v_t = new File(getProperties().getProperty("SSPACE_DIR"),
              getProperties().getProperty("MATRIX_Vt"));
      V_t = MatrixIO.readMatrix(v_t,MatrixIO.Format.DENSE_TEXT);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return V_t;
  }


  public static Matrix getS() {
    try {
      S = MatrixIO.readMatrix(new File(getProperties().getProperty("SSPACE_DIR"),
              getProperties().getProperty("MATRIX_S")),
              MatrixIO.Format.DENSE_TEXT);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return S;
  }

  /**
   * SVD can be used for calculating A(inverse)
   * <p/>
   * A(inversed) = V * Sigma(inversed) * U(transposed)
   *
   * @return
   */
  public static Matrix getAInverse() {
    Matrix V_S_inv = Matrices.multiply(transpose(getV_t()), getSInverse());
    return Matrices.multiply(V_S_inv, transpose(getU()));
  }


  public static Matrix getMatrixInverse(Matrix m) {
    int rows = m.rows();
    int columns = m.columns();
    if (rows != columns) {
      return Matrices.create(rows, rows, true);
    }
    Matrix m_inv = m;
    for (int i = 0; i < rows; i++) {
      double oldVal = m_inv.get(i, i);
      double newVal = (oldVal == 0.0 ? 0.0 : 1.0 / oldVal);
      m_inv.set(i, i, newVal);
    }
    return m_inv;
  }

  public static Matrix getSInverse() {
    return getMatrixInverse(getS());
  }


  public double[] getSingularValues() {
    Matrix S = getS();
    int m = S.rows();
    double[][] singularMatrix = S.toDenseArray();
    double[] s = new double[m];
    for (int i = 0; i < m; i++) {
      s[i] = singularMatrix[i][i];
    }
    return s;
  }


/*  **
   * Generates an identity matrix.
   * Used in calculating the inverse of a matrix.
   *  
   * @param dimensions
   * @return
   **/

  public static Matrix getIdentityMatrix(int dimensions) {
    Matrix m = new DiagonalMatrix(dimensions);
    for (int i = 0; i < dimensions; i++) {
      m.set(i, i, 1.0);
    }
    return m;
  }

  /**
   * @param m
   * @return
   */
  public static Matrix transpose(Matrix m) {
    int rows = m.rows();
    int columns = m.columns();
    Matrix n = Matrices.create(columns, rows, true);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        n.set(i, j, m.get(j, i));
      }
    }
    return n;
  }

  public static Properties getProperties() {
    return PropertiesLoader.loadProperties();
  }

}
