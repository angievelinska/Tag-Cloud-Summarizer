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
    CountDocuments counter;

    public CountDocumentsTest(){
        super();
    }

  @Before
    public void setUp() throws Exception {
        super.setUp();
        conn = new ConnectDocMachine();
        repo = conn.openConnection();
        counter = new CountDocuments();
    }

    @After
    public void tearDown() throws Exception {
        conn.closeConnection();
        conn = null;
        repo = null;
        counter = null;
    }

  
    @Test
    public void testCounter(){
       Content con = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE");
       counter.iterate(con);
      
       assertNotSame (counter.getCount(), 0);
    }
}
