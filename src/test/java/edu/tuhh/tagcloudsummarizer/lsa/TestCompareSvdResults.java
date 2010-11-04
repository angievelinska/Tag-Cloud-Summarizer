package edu.tuhh.tagcloudsummarizer.lsa;

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
 * @author avelinsk
 */
public class TestCompareSvdResults {

  public static void testCompareSVDResults() {
    int m = 7, n = 6;

    Random generator = new Random();
    Matrix mat = new ArrayMatrix(m, n);

    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        mat.set(i, j, Math.abs(generator.nextDouble()) % 100);
      }
    }

    List<SVD.Algorithm> algorithms = new LinkedList<SVD.Algorithm>();

    algorithms.add(SVD.Algorithm.COLT);
    algorithms.add(SVD.Algorithm.JAMA);
    algorithms.add(SVD.Algorithm.SVDLIBJ);

    File dir  = new File("svd");

    if(!dir.exists()){
      dir.mkdir();
    }

    for (SVD.Algorithm algorithm : algorithms) {
      System.out.println("Algorithm is: " + algorithm);

      //Matrix[] svd = SVD.svd(mat, algorithm, Math.min(m, n));
      Matrix[] svd = SVD.svd(mat,algorithm, 4);
      Matrix u = svd[0];
      Matrix s = svd[1];
      Matrix vt = svd[2];

      System.out.println("V^t is:");
      System.out.println(vt.toString());

      File f1 = new File(dir, algorithm+"_matrix_U.txt");
      File f2 = new File(dir, algorithm+"_matrix_S.txt");
      File f3 = new File(dir, algorithm+"_matrix_Vt.txt");
      try{
        f1.createNewFile();
        MatrixIO.writeMatrix(u, f1, MatrixIO.Format.DENSE_TEXT);
        f2.createNewFile();
        MatrixIO.writeMatrix(s, f2, MatrixIO.Format.DENSE_TEXT);
        f3.createNewFile();
        MatrixIO.writeMatrix(vt, f3, MatrixIO.Format.DENSE_TEXT);
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
