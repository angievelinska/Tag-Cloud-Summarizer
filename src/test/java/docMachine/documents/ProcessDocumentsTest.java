package docMachine.documents;

import com.coremedia.cap.content.ContentRepository;
import docMachine.connect.ConnectDocMachine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ProcessDocumentsTest {
    ProcessDocuments pd;
    ConnectDocMachine cdm;
    ContentRepository repo;

    @Before
    public void setUp() throws Exception {
      pd = new ProcessDocuments();
      cdm = new ConnectDocMachine();
      repo = cdm.openConnection();
      pd.callTextSerializer(repo);
    }

    @After
    public void tearDown() throws Exception {
      cdm.closeConnection();
    }

    @Test
    public void testProcessDocuments(){

    }
   
}
