package edu.tuhh.summarizer.docmachine;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import edu.tuhh.summarizer.docmachine.connect.ConnectDocMachine;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CountDocumentsTest extends TestCase {
  ConnectDocMachine conn;
  ContentRepository repo;
  CountDocuments countDocuments;

  @Before
  public void setUP() throws Exception {
    conn = new ConnectDocMachine();
    repo = conn.openConnection();
    countDocuments = new CountDocuments();
  }

  @Ignore  //parses the whole online documentation, a very heavy test case
  @Test
  public void testCounter1() {
    Content con = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE");
    countDocuments.iterate(con);

    assertNotSame(countDocuments.getCount(), 0);
  }

  @Ignore
  @Test
  public void testCounter2() {
    Content content = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE/5.2/WhatsNewInCMS2008");
    countDocuments.iterate(content);

    assertNotSame(countDocuments.getCount(), 0);
  }


  @Test
  public void testCounter3() {
    Content content = repo.getRoot().getChild("/Books/CMS_ONLINE/SoSo");
    countDocuments.iterate(content);

    assertNotSame(countDocuments.getCount(), 0);
  }

  @After
  public void tearDown() throws Exception {
    conn.closeConnection();
    conn = null;
    repo = null;
    countDocuments = null;
  }
}
