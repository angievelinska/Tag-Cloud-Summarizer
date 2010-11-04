package docMachine.documents;

import com.coremedia.cap.content.Content;

/**
 * @author avelinsk
 *
 */
public class CountDocuments extends AbstractDocuments {
  private final static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(CountDocuments.class);
  private int counter = 0;

  @Override
  protected void processText(Content content){
    counter++;
  }
  
  public int getCount(){
    log.info("Number of sections/texts: "+counter);
    return counter;
  }

}
