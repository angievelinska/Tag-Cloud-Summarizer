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
  PropertiesLoader props = null;
  @Before
  public void setUP(){
    props = new PropertiesLoader();
  }
  @Test
  public void testLoadProperties() throws Exception {
    Properties properties = props.loadProperties();
    assertEquals("termSpace.sspace", properties.getProperty("SSPACE_FILE"));

  }

  @Test
  public void testProperties(){
    Properties properties = props.loadProperties();
    assertNotSame( "input.txt", properties.getProperty("docFile"));
  }

  @Test
  public void printProps(){
    Properties properties = props.loadProperties();
    for (Map.Entry entry : properties.entrySet()){
      System.out.println("Property: "+entry.getKey()+"="+entry.getValue());
    }
  }
}