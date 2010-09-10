package docMachine.connect;

import com.coremedia.cap.content.ContentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ProcessDocumentTest {
    ProcessDocument pd;
    ConnectDocMachine cdm;
    ContentRepository repo;

    @Before
    public void setUp() throws Exception {
      pd = new ProcessDocument();
      cdm = new ConnectDocMachine();
      repo = cdm.openConnection();
      pd.callTextSerializer(repo);
    }

    @After
    public void tearDown() throws Exception {
      cdm.closeConnection();
    }

    @Test
    public void testCallDocumentSerializer(){
        
    }
   
}
