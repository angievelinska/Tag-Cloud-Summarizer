package docMachine.documents;

import com.coremedia.cap.content.Content;
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
    }

    @After
    public void tearDown() throws Exception {
      cdm.closeConnection();
    }

    @Test
    public void testProcessDocuments(){
      Content content = repo.getRoot().getChild("/Books/CMS_ONLINE/5.2/WhatsNewInCMS2008");
      pd.iterate(content);
      
    }
   
}
