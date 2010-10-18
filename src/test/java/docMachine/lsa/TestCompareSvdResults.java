package docMachine.lsa;

import edu.ucla.sspace.matrix.ArrayMatrix;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;
import edu.ucla.sspace.matrix.SVD;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * User: avelinsk
 * Date: 15.10.2010
 * Time: 16:01:32
 */
public class TestCompareSvdResults {

  public static void testCompareSVDResults() {
    int m = 12, n = 6;

    Random generator = new Random();
    Matrix mat = new ArrayMatrix(m, n);

    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        mat.set(i, j, Math.abs(generator.nextDouble()) % 100);
      }
    }

    List<SVD.Algorithm> algorithms = new LinkedList<SVD.Algorithm>();

    //algorithms.add(SVD.Algorithm.COLT);
    algorithms.add(SVD.Algorithm.JAMA);
    algorithms.add(SVD.Algorithm.SVDLIBJ);

    File dir  = new File("sspace");
    File f;

    for (SVD.Algorithm algorithm : algorithms) {
      System.out.println("Algorithm is: " + algorithm);

      //Matrix[] svd = SVD.svd(mat, algorithm, Math.min(m, n));
      Matrix[] svd = SVD.svd(mat,algorithm, 4);
      Matrix u = svd[0];
      Matrix s = svd[1];
      Matrix vt = svd[2];

      System.out.println("V^t is:");
      System.out.println(vt.toString());

      try{
        f = File.createTempFile(algorithm+"matrix_U",".txt", dir);
        MatrixIO.writeMatrix(u, f, MatrixIO.Format.DENSE_TEXT);
        f = File.createTempFile(algorithm+"matrix_S",".txt", dir);
        MatrixIO.writeMatrix(s, f, MatrixIO.Format.DENSE_TEXT);
        f = File.createTempFile(algorithm+"matrix_V",".txt", dir);
        MatrixIO.writeMatrix(vt, f, MatrixIO.Format.DENSE_TEXT);
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testSvdGeneration(){
    TestCompareSvdResults.testCompareSVDResults();
  }

}
