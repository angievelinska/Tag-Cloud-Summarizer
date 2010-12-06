package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.matrix.DiagonalMatrix;
import edu.ucla.sspace.matrix.Matrices;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;

import java.io.*;
import java.util.Properties;

/**
 * Utility class which serves constructing and querying
 * the semantic space produced by LSA.
 *
 * @author avelinsk
 */
class LSAUtils {

  private static Matrix A;
  private static SemanticSpace termsSpace;
  private static SemanticSpace docsSpace;
  private static Matrix docsMatrix;

  public static SemanticSpace getTermsSpace() {
    try {
      termsSpace = SemanticSpaceIO.load(getProperties().getProperty("SSPACE_TERMS"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return termsSpace;
  }

  public static SemanticSpace getDocsSpace() {
    try {
      docsSpace = SemanticSpaceIO.load(getProperties().getProperty("SSPACE_DOCS"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return docsSpace;
  }

  public static Matrix getDocsMatrix(){
    String docMatr = getProperties().getProperty("DOCS_MATRIX");

    try{
      docsMatrix = MatrixIO.readMatrix(new File(docMatr),MatrixIO.Format.SVDLIBC_DENSE_TEXT);
    }catch(IOException e){
      e.printStackTrace();
    }

    return docsMatrix;
  }

  /**
   * Writes the semantic space to the file using the {@code TEXT} format.
   *
   * @param sspace the semantic space to be written
   * @param output the file into which the space will be written
   * @throws IOException if any I/O exception occurs when reading the semantic
   *                     space data from the file
   */
  protected static void saveDocumentSpace(Matrix sspace, File output) throws IOException {
    OutputStream os = new FileOutputStream(output);
    PrintWriter pw = new PrintWriter(os);

    writeHeader(os, SemanticSpaceIO.SSpaceFormat.TEXT);
    // write out how many vectors there are and the number of dimensions
    pw.println(sspace.rows() + " " + sspace.columns());

    for (int i = 0; i < sspace.rows(); i++) {
      StringBuffer sb = new StringBuffer(64);
      for (int j = 0; j < sspace.columns(); j++) {
        sb.append((sspace.get(i, j))).append(" ");
      }
      pw.println(i + "|" + sb.toString());
    }
    pw.close();
  }


  static void writeHeader(OutputStream os, SemanticSpaceIO.SSpaceFormat format)
          throws IOException {
    DataOutputStream dos = new DataOutputStream(os);
    dos.writeChar('s');
    dos.writeChar('0' + format.ordinal());
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
    return new PropertiesLoader().loadProperties();
  }

}
