package edu.tuhh.summarizer.cluster;

import edu.tuhh.summarizer.common.PropertiesLoader;
import edu.ucla.sspace.clustering.Assignment;
import edu.ucla.sspace.clustering.AutomaticStopClustering;
import edu.ucla.sspace.clustering.HierarchicalAgglomerativeClustering;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.matrix.Matrix;
import edu.ucla.sspace.matrix.MatrixIO;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author avelinsk
 */
public class Clusterizer {
  Matrix matrix;
  Properties props;

  public Clusterizer() {
    props = new PropertiesLoader().loadProperties();
    try {
      matrix = MatrixIO.readMatrix(new File(props.getProperty("CLUSTER_MATRIX")), MatrixIO.Format.CLUTO_DENSE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Assignment[] hierarchicalAgglomerativeClustering() {
    HierarchicalAgglomerativeClustering hiearcluster = new HierarchicalAgglomerativeClustering();
    props.setProperty("CLUSTER_SIMILARITY_PROPERTY", "-1");
    props.setProperty("SIMILARITY_FUNCTION_PROPERTY", "COSINE");
    Assignment[] assignment = hiearcluster.cluster(matrix,20,props);
    return assignment;

  }

  public Assignment[] kMeansClustering() {
    AutomaticStopClustering autocluster = new AutomaticStopClustering();
    Assignment[] assignments = autocluster.cluster(matrix, props);
    return assignments;
  }

}
