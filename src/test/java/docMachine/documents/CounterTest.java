package docMachine.documents;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import docMachine.connect.ConnectDocMachine;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CounterTest extends TestCase {
    ConnectDocMachine conn;
    ContentRepository repo;
    Counter counter;

    public CounterTest(){
        super();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        conn = new ConnectDocMachine();
        repo = conn.openConnection();
        counter = new Counter();
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
 //       Content con = (Content) repo.getContents().getChild("/Books/CMS_ONLINE/5.2/AdministrationOperationManual/AdministrationOperationManual_From5.0");
 //       counter.countDocuments(con);
        assertNotSame (counter.getCount(),0);
    }
}
