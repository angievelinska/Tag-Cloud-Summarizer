package edu.tuhh.summarizer.common;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * @author avelinsk
 */
public class PropertiesLoaderTest {
  PropertiesLoader props;
  @Before
  public void setUP(){
    props = new PropertiesLoader();
  }
  @Test
  public void testLoadProperties() throws Exception {
    Properties props = PropertiesLoader.loadProperties();
    assertEquals("LSA.sspace", props.getProperty("SSPACE_FILE"));

  }

  @Test
  public void testProperties(){
    Properties props = PropertiesLoader.loadProperties();
    assertNotSame( "input.txt", props.getProperty("docFile"));
  }

  @Test
  public void printProps(){
    for (Map.Entry entry : PropertiesLoader.loadProperties().entrySet()){
      System.out.println("Property: "+entry.getKey()+"="+entry.getValue());
    }
  }
}