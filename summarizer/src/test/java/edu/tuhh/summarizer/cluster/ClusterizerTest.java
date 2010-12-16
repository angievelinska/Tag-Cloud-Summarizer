package edu.tuhh.summarizer.cluster;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.clustering.Assignment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author avelinsk
 */

public class ClusterizerTest {
  Clusterizer clusterizer;
  Properties props;

  @Before
  public void setup() {
    clusterizer = new Clusterizer();
    props = new PropertiesLoader().loadProperties();
  }

  @Test
  public void testKmeans() {
    Assignment[] assignments = clusterizer.kMeansClustering();
    for (int i = 0; i < assignments.length; i++) {
      int[] assign = assignments[i].assignments();
      System.out.println("Assignments ");
      for (int j = 0; j < assign.length; j++) {

        System.out.print(assign[j]);
      }

    }
  }

  @Test
  public void testHierarchical() {
    System.out.println("Property: "+props.getProperty("CLUSTER_SIMILARITY_PROPERTY"));
    Assignment[] assignments = clusterizer.hierarchicalAgglomerativeClustering();
    for (int i = 0; i < assignments.length; i++) {
      int[] assign = assignments[i].assignments();
      System.out.println("Assignments " );
      for (int j = 0; j < assign.length; j++) {

        System.out.print(assign[j]);
      }

    }
  }


  @After
  public void tearDown() {
    clusterizer = null;
  }
}
