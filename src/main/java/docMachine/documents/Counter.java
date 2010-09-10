package docMachine.documents;

import com.coremedia.cap.content.Content;

/**
 * User: avelinsk
 * Date: 02.08.2010
 *
 * TODO: refactor
 */
public class Counter extends AbstractDocuments {
  private final static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Counter.class);
  private int counter = 0;

    public void getTextsForSection(Content rootElement){
    if (rootElement.getType().getName().equals("Section")){

        for (Content article: rootElement.getLinks("Articles")){
            if (article.getType().getName().equals("MLArticle")){

               counter++;
           }
        }

        if (rootElement.getLinks("Sections").size()>0){
          for (Content section:rootElement.getLinks("Sections")){

              getTextsForSection(section);
          }
        }

    } else if (rootElement.getType().getName().equals("MLArticle")){

        counter++;
    }
  }

  public int getCount(){
    log.info("Number of sections/texts published as online documentation: "+counter);
    
    return counter;
  }

}
