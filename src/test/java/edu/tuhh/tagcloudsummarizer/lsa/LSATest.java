package edu.tuhh.tagcloudsummarizer.lsa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * User: ng
 * Date: 19.08.2010
 * Time: 23:05:27
 */
public class LSATest {
    LSA lsa;

    @Before
    public void setUp() throws Exception {
        lsa = new LSA();
    }

    @Test
    public void testLSA(){
      lsa.runLSA();

      Assert.assertTrue((new File("sspace/LSA.sspace")).exists());
      Assert.assertTrue((new File("sspace/matrix_S.txt")).exists());
      Assert.assertTrue((new File("sspace/matrix_U.txt")).exists());
      Assert.assertTrue((new File("sspace/matrix_V.txt")).exists());
    }

/*  @Test
  public void testSVD(){

//    System.out.println("vector length after reduction: "+lsa.sspace.getVectorLength());
    Vector v = (DoubleVector) lsa.sspace.getVector("content");
    int size = v.length();
    for (int i=0; i<size; i++){
      System.out.print(v.getValue(i));
    }
  }    */

    @After
    public void tearDown() throws Exception {
        lsa = null;
    }
}
