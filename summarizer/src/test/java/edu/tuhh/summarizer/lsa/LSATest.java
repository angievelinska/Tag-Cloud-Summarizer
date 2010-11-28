package edu.tuhh.summarizer.lsa;

import edu.tuhh.summarizer.common.PropertiesLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * @author: avelinsk
 */
public class LSATest {
  LSA lsa;

  @Before
  public void setUP() throws Exception {
    lsa = new LSA();
  }

  @Test
  public void testLSA() {
    lsa.runLSA();

    Properties props = PropertiesLoader.loadProperties();
    File sspace = new File(props.getProperty("SSPACE"));
    Assert.assertTrue(sspace.exists());
    String matrix_u = (String)props.get("SSPACE_DIR")+ (String)props.get("MATRIX_U");
    Assert.assertTrue((new File(matrix_u)).exists());
    String matrix_s = (String)props.get("SSPACE_DIR")+ (String)props.get("MATRIX_S");
    Assert.assertTrue((new File(matrix_s)).exists());
    String matrix_vt = (String)props.get("SSPACE_DIR")+ (String)props.get("MATRIX_Vt");
    Assert.assertTrue((new File(matrix_vt)).exists());
  }

/*  @Test
  public void testSVD(){

//    System.out.println("vector length after reduction: "+lsa.sspace.getVectorLength());
    Vector v = (DoubleVector) lsa.getVector("content");
    int size = v.length();
    for (int i=0; i<size; i++){
      System.out.print(v.getValue(i));
    }
  }*/

  @After
  public void tearDown() throws Exception {
    lsa = null;
  }
}
