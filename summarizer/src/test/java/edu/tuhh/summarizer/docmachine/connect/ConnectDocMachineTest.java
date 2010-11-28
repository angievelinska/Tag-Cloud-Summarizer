package edu.tuhh.summarizer.docmachine.connect;

import com.coremedia.cap.content.ContentRepository;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ConnectDocMachineTest extends TestCase {
  ConnectDocMachine conn;

  @Before
  public void setUP() throws Exception {
    conn = new ConnectDocMachine();
    conn.openConnection();
  }

  @Test
  public void testOpenConnection() {
    assertTrue(conn.openConnection() instanceof ContentRepository);
  }

  @After
  public void tearDown() throws Exception {
    conn.closeConnection();
  }

}

