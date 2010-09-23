package docMachine.documents;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import docMachine.connect.ConnectDocMachine;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CountDocumentsTest extends TestCase {
    ConnectDocMachine conn;
    ContentRepository repo;
    CountDocuments countDocuments;

  public CountDocumentsTest(){
        super();
    }

  @Before
    public void setUp() throws Exception {
        super.setUp();
        conn = new ConnectDocMachine();
        repo = conn.openConnection();
        countDocuments = new CountDocuments();
    }

    @After
    public void tearDown() throws Exception {
        conn.closeConnection();
        conn = null;
        repo = null;
        countDocuments = null;
    }

// too big, for now commented out
/*    @Test
    public void testCounter1(){
       Content con = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE");
       countDocuments.iterate(con);
      
       assertNotSame (countDocuments.getCount(), 0);
    }

  @Test
  public void testCounter2(){
    Content content = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE/5.2/WhatsNewInCMS2008");
    countDocuments.iterate(content);

    assertNotSame(countDocuments.getCount(), 0);
  }

  @Test
  public void testCounter3(){
    Content content = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE/SoSo");
    countDocuments.iterate(content);

    assertNotSame(countDocuments.getCount(), 0);
  }  */

  @Test
  public void testCounter4(){
      Content content = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE/SoSo");
      countDocuments.iterate(content);
    assertNotSame("Document No "+countDocuments.getCount(), countDocuments.getCount(), 0);
  }
  
}
