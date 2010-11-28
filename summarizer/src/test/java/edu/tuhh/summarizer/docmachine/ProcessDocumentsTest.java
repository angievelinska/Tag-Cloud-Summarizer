package edu.tuhh.summarizer.docmachine;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import edu.tuhh.summarizer.docmachine.connect.ConnectDocMachine;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ProcessDocumentsTest {
  ProcessDocuments pd;
  ConnectDocMachine cdm;
  ContentRepository repo;

  @Before
  public void setUP() throws Exception {
    pd = new ProcessDocuments();
    cdm = new ConnectDocMachine();
    repo = cdm.openConnection();
  }

  @Test
  public void testCounter() {
    Content content = repo.getRoot().getChild("/Books/CMS_ONLINE/5.2/WhatsNewInCMS2008");
    pd.iterate(content);
  }

  @Ignore
  @Test
  public void testCounter2() {
    Content content = repo.getRoot().getChild("/Books/CMS_ONLINE/SoSo");
    pd.iterate(content);
  }

  @Ignore
  // a heavy test case
  @Test
  public void testProcessDocuments3() {
    Content content = repo.getRoot().getChild("/Books/CMS_ONLINE/5.2");
    pd.iterate(content);
  }

  @After
  public void tearDown() throws Exception {
    pd = null;
    repo = null;
    cdm.closeConnection();
  }
}
