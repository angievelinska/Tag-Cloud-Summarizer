package edu.tuhh.summarizer.docmachine;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import edu.tuhh.summarizer.docmachine.connect.ConnectDocMachine;
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
  public void testCounter(){
      Content content = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE/5.2/WhatsNewInCMS2008");
      pd.iterate(content);
  }

/*   @Test
  public void testCounter2(){
      Content content = (Content) repo.getRoot().getChild("/Books/CMS_ONLINE/SoSo");
      pd.iterate(content);
  }

  @Test
  public void testProcessDocuments3(){
    Content content = repo.getRoot().getChild("/Books/CMS_ONLINE/5.2");
    pd.iterate(content);
  }*/
   
}
