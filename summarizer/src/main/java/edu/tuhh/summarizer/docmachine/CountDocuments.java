package edu.tuhh.summarizer.docmachine;

import com.coremedia.cap.content.Content;
import org.apache.log4j.Logger;

/**
 * @author avelinsk
 *
 */
public class CountDocuments extends AbstractDocuments {
  private static Logger log = Logger.getLogger(CountDocuments.class);
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
